package com.eventi.messaggi;

public class CreateEventMessage implements EventMessage{
    
    private final String eventName;
    private final Integer eventSeats;
    
    public CreateEventMessage(String eventName, Integer eventSeats) {
        this.eventName = eventName;
        this.eventSeats = eventSeats;
    }

    public String getEventName() {
        return eventName;
    }

    public Integer getEventSeats() {
        return eventSeats;
    }

}
