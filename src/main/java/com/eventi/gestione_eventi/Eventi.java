package com.eventi.gestione_eventi;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.eventi.calvino.Subscriber;
import com.eventi.messaggi.AddSeatsEventMessage;
import com.eventi.messaggi.BookSeatsEventMessage;
import com.eventi.messaggi.BroadcastEventsListMesage;
import com.eventi.messaggi.CloseEventMessage;
import com.eventi.messaggi.CreateEventMessage;
import com.eventi.messaggi.ErrorResponse;
import com.eventi.messaggi.EventMessage;
import com.eventi.messaggi.ListEventMessage;
import com.eventi.messaggi.OkResponse;


public class Eventi extends Subscriber implements Runnable {    

    private Map<String,Evento> eventList;


    public Eventi() {
        this.eventList = new TreeMap<>();
    }

    public void crea(String name, Integer seats) {
        if (this.containsEvent(name))
            throw new IllegalArgumentException("L'evento che si intende creare è già esistente");
        
        eventList.put(name,new Evento(name, seats));
    }

    public void aggiungi(String name, Integer seats) {
       
        if(!this.containsEvent(name))
            throw new IllegalArgumentException("Evento non esistente");
        eventList.get(name).addSeats(seats);

    }

    public void prenota(String name, Integer seats) {
        
       
        if(!this.containsEvent(name))
            throw new IllegalArgumentException("Evento non esistente");
        eventList.get(name).removeSeats(seats);

    }

    public void listaEventi() {
        for (Evento evento : eventList.values()) {
            System.out.println( evento.toString());
           
        }
    }

    public void chiudi(String name) { 
        if(!this.containsEvent(name))
            throw new IllegalArgumentException("L'evento che si intende chiudere non è esistente");
        eventList.remove(name);       
    }

    private boolean containsEvent(String name) {
        return this.eventList.containsKey(name);
    }

    private void initializeTopics(){
        try {
            SubscribeCons("topicEventMessages");   //Topic da cui riceveremo i messaggi dai server thread
            SubscribeProd("topicEventsBroadcast"); //Topic sulla quale facciamo il broadcast della lista eventi
            SubscribeCons("topicEventsBroadcast"); //Sottoscrivo sia come producer che consumer per consentire solo peeker su questo topic
            produce("topicEventsBroadcast", new BroadcastEventsListMesage(new HashMap<>()));
            SubscribeProd("topicFatalError");      //Topic su cui comunico al main che il thread non e' piu in grado di continuare l'esecuzione
        } catch (Exception e) {
            System.out.println("unable tu initialize topics: " + e.getMessage());
            System.exit(0);
        }
    }

    private void updateEventTopic(){
        try {
            produce("topicEventsBroadcast", new BroadcastEventsListMesage(eventList));
            consume("topicEventsBroadcast");
        } catch (Exception e) {
            // Comunico al main che sono detonato
        }
    }

    @Override
    public void run() {
        System.out.println("EVENTI: Starting event hadler thread");
        initializeTopics();
        System.out.println("EVENTI: Event start polling on topic");
        while(true){
            EventMessage recv_message;
            boolean waiting = false;
            try {
                recv_message = consume("topicEventMessages");
            } catch (Exception e) {
                // TODO: comunico al mian che sono detonato
                continue;
            }
            if(recv_message==null){
                if(waiting){
                    try {
                        Thread.sleep(20); // aspettiamo per non fondere la CPU
                    } catch (Exception e) {
                        continue; // se non ti va la wait ti meriti che ti si fonda la CPU
                    }
                } else {
                    waiting = true;
                }
            }
            waiting = false;
            boolean handleMessageResult = true;
            Integer clientId;
            if (recv_message instanceof AddSeatsEventMessage) {
                try {
                    aggiungi(((AddSeatsEventMessage)recv_message).getEventName(), ((AddSeatsEventMessage)recv_message).getEventSeats());
                    clientId = ((AddSeatsEventMessage)recv_message).getClientId();
                } catch (Exception e) {
                    handleMessageResult = false;
                    clientId = ((AddSeatsEventMessage)recv_message).getClientId();
                }
            } else if (recv_message instanceof BookSeatsEventMessage){
                try {
                    prenota(((BookSeatsEventMessage)recv_message).getEventName(), ((BookSeatsEventMessage)recv_message).getEventSeats()); 
                    clientId = ((BookSeatsEventMessage)recv_message).getClientId();
                } catch (Exception e) {
                    handleMessageResult = false;
                    clientId = ((BookSeatsEventMessage)recv_message).getClientId();
                }
            } else if (recv_message instanceof CloseEventMessage){
                try {
                    chiudi(((CloseEventMessage)recv_message).getEventName());
                    clientId = ((CloseEventMessage)recv_message).getClientId();
                } catch (Exception e) {
                    handleMessageResult = false;
                    clientId = ((CloseEventMessage)recv_message).getClientId();
                }
            } else if (recv_message instanceof CreateEventMessage){
                try {
                    crea(((CreateEventMessage)recv_message).getEventName(), ((CreateEventMessage)recv_message).getEventSeats());
                    clientId = ((CreateEventMessage)recv_message).getClientId();
                } catch (Exception e) {
                    handleMessageResult = false;
                    clientId = ((CreateEventMessage)recv_message).getClientId();
                }
            } else {
                continue;
            }
            updateEventTopic();
            try {
                SubscribeProd("topic-"+clientId.toString());
                if(handleMessageResult){
                    produce("topic-"+clientId.toString(), new OkResponse());
                } else {
                    produce("topic-"+clientId.toString(), new ErrorResponse());
                }
                synchronized(getMyProducer().get("topic-"+clientId.toString())){
                    getMyProducer().get("topic-"+clientId.toString()).notifyAll();
                }
            } catch (Exception e) {
                continue; 
            }

        }
    }

}
