package com.eventi.messaggi;

public class BookSeatsEventMessage implements EventMessage{
    
    private final Integer clientId;
    private final String eventName;
    private final Integer eventSeats;
    

    public BookSeatsEventMessage(Integer clientId, String eventName, Integer eventSeats) {
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