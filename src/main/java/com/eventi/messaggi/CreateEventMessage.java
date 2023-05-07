package com.eventi.messaggi;

public class CreateEventMessage implements EventMessage{
    
    private final Integer clientId;
    private final String eventName;
    private final Integer eventSeats;


    public CreateEventMessage(Integer clientId, String eventName, Integer eventSeats) {
        this.clientId = clientId;
        this.eventName = eventName;
        this.eventSeats = eventSeats;
    }

    public Integer getClientId() {
        return clientId;
    }

    public String getEventName() {
        return eventName;
    }

    public Integer getEventSeats() {
        return eventSeats;
    }
    
}
