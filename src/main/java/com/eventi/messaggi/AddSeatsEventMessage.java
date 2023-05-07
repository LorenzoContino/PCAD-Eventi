package com.eventi.messaggi;

public class AddSeatsEventMessage implements EventMessage{
    
    private final Integer clientId;
    private final String eventName;
    private final Integer eventSeats;
    
    public AddSeatsEventMessage(Integer clientId, String eventName, Integer eventSeats) {
        this.clientId = clientId;
        this.eventName = eventName;
        this.eventSeats = eventSeats;
    }

    public String getEventName() {
        return eventName;
    }

    public Integer getEventSeats() {
        return eventSeats;
    }

    public Integer getClientId() {
        return clientId;
    }

}
