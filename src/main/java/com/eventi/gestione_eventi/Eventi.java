package com.eventi.gestione_eventi;

import java.util.TreeMap;

import com.eventi.calvino.Subscriber;
import com.eventi.messaggi.BroadcastEventsListMesage;

import java.util.Map;


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
            SubscribeProd("topicFatalError");      //Topic su cui comunico al main che il thread non e' piu in grado di continuare l'esecuzione
        } catch (Exception e) {
            System.out.println("unable tu initialize topics: " + e.getMessage());
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
        initializeTopics();
    }

}
