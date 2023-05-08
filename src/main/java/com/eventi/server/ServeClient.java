package com.eventi.server;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONException;
import org.json.JSONObject;

import com.eventi.calvino.Subscriber;
import com.eventi.calvino.calvino_exceptions.SubscriberNotConsumerException;
import com.eventi.calvino.calvino_exceptions.SubscriberNotProducerException;
import com.eventi.messaggi.AddSeatsEventMessage;
import com.eventi.messaggi.BookSeatsEventMessage;
import com.eventi.messaggi.BroadcastEventsListMesage;
import com.eventi.messaggi.CloseEventMessage;
import com.eventi.messaggi.CreateEventMessage;
import com.eventi.messaggi.EventMessage;
import com.eventi.messaggi.ListEventMessage;
import com.eventi.messaggi.OkResponse;


public class ServeClient extends Subscriber implements Runnable {

    public static final AtomicInteger id = new AtomicInteger(0);
    private final Integer my_id;
    private Socket clientSocket; 


    @Override
    public void run() {
        try {
            serve();
        } catch (Exception e) {
            System.out.println("SERVECLIENT: " + e.getMessage());
        }
        System.out.println("SERVECLIENT: Disconnecting from client.");
        try {
            clientSocket.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void serve(){
        System.out.println("SERVECLIENT: Starting to serve client " + clientSocket.getInetAddress() + " on port " + clientSocket.getPort() + ".");
        while (clientSocket.isConnected() /* Finche il client non si disconnette */) {
            var message = readJSON();
            EventMessage topicMessage;
            try {
                topicMessage = handleJSON(message);
            } catch (Exception e) {
                continue;
            }
            if(topicMessage instanceof ListEventMessage){
                sendEventList();
                continue;
            }
            for(;;){
                try {
                    SubscribeProd("topicEventMessages");
                    break;
                } catch (Exception e) {
                    // Buisy Waiting
                }
            }
            EventMessage response;
            try { 
                produce("topicEventMessage", topicMessage);
                UnSubscribeProd("topicEventMessages");
                synchronized(getMyConsumer().get("topic-"+my_id.toString())){
                    getMyConsumer().get("topic-"+my_id.toString()).wait();
                }
                response = consume("topic-"+my_id.toString());
            } catch (SubscriberNotProducerException e) {
                // TODO: handle exception
                continue;
            } catch (SubscriberNotConsumerException e) {
                continue;
            } catch (Exception e){
                continue;
            }
            if(response instanceof OkResponse){
                sendEventOk();
            } else {
                sendEventError();
            }
        }
    }

    private JSONObject readJSON() throws InvalidMessageException{
        CharBuffer recv_buffer = CharBuffer.allocate(1024);
        try (InputStreamReader in = new InputStreamReader(
            clientSocket.getInputStream(), StandardCharsets.UTF_8)){
                in.read(recv_buffer);
        } catch (Exception e) {
            throw new InvalidMessageException(e.getMessage());
        }
        return new JSONObject(recv_buffer);
    }

    public EventMessage handleJSON (JSONObject json) {
        String role;
        JSONObject command;
        String eventName;
        Integer eventSeats;
        try {
            role = json.getString("role");
            command = json.getJSONObject("command");
        } catch (JSONException e) {
            throw new InvalidMessageException(e.getMessage());
        }
        switch (role) {
            case "ADMIN":
                switch (command.getString("type")) {
                    case "CREATE":
                            try {
                                eventName = command.getString("name");
                                eventSeats = command.getInt("seats");
                            } catch (Exception e) {
                                throw new InvalidMessageException("command");
                            }
                            return new CreateEventMessage(my_id, eventName, eventSeats);
                
                    case "ADD":
                            try {
                                eventName = command.getString("name");
                                eventSeats = command.getInt("seats");
                            } catch (Exception e) {
                                throw new InvalidMessageException("command");
                            }
                            return new AddSeatsEventMessage(my_id, eventName, eventSeats);

                    case "CLOSE":
                            try {
                                eventName = command.getString("name");
                            } catch (Exception e) {
                                throw new InvalidMessageException("command");
                            }
                            return new CloseEventMessage(my_id, eventName);

                    default:
                        throw new InvalidMessageException("command");
                } 
        
            case "USER":
            
                switch (command.getString("type")) {
                    case "BOOK":
                            try {
                                eventName = command.getString("name");
                                eventSeats = command.getInt("seats");
                            } catch (Exception e) {
                                throw new InvalidMessageException("command");
                            }
                            return new BookSeatsEventMessage(my_id, eventName, eventSeats);

                    case "LIST":
                            return new ListEventMessage(my_id);

                    default:
                        throw new InvalidMessageException("command");
                }
                
            default:
                throw new InvalidMessageException("role");
        }
    }

    private void sendEventList(){
        BroadcastEventsListMesage eventListMessage;
        try {
            eventListMessage = (BroadcastEventsListMesage)peek("topicEventsBroadcast");
        } catch (Exception e) {
            // TODO: handle exception
            return;
        }
        String send_message = generateEventListJSON(eventListMessage);      
        try(OutputStreamWriter out = new OutputStreamWriter(
            clientSocket.getOutputStream(), StandardCharsets.UTF_8
        )){
            out.write(send_message);
        } catch (Exception e){
            //TODO - cazzi
        }
    }

    public String generateEventListJSON(BroadcastEventsListMesage eventListMessage){
        JSONObject resultJSON = new JSONObject();
        for(var event : eventListMessage.getEventList().values()){
            JSONObject aux = new JSONObject();
            aux.put("name", event.getName());
            aux.put("seats", event.getSeats());
            aux.put("max_seats", event.getMaxSeats());
            resultJSON.put(event.getName(), aux);
        }
        return resultJSON.toString();
    }

    private void sendEventOk(){
        String send_message = sendEventResponse(true);
        try(OutputStreamWriter out = new OutputStreamWriter(
            clientSocket.getOutputStream(), StandardCharsets.UTF_8
        )){
            out.write(send_message);
        } catch (Exception e){
            //TODO - cazzi
        }
    }

    private void sendEventError(){
        String send_message = sendEventResponse(false);
        try(OutputStreamWriter out = new OutputStreamWriter(
            clientSocket.getOutputStream(), StandardCharsets.UTF_8
        )){
            out.write(send_message);
        } catch (Exception e){
            //TODO - cazzi
        }
    }

    private String sendEventResponse(boolean eventType){
        JSONObject resultJSON = new JSONObject();
        if(eventType){
            resultJSON.put("result", "OK");
        } else {
            resultJSON.put("result", "ERROR");
        }
        return resultJSON.toString();
    }

    protected ServeClient(Socket clientSocket){
        System.out.println("SERVECLIENT: New socket created to handle requests.");
        this.clientSocket = clientSocket;
        this.my_id = id.incrementAndGet();
        try {
            SubscribeCons("topic-"+my_id.toString());
            SubscribePeek("topicEventsBroadcast");
        } catch (Exception e) {
        }
    }
}
