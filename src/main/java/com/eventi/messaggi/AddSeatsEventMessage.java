package com.eventi.messaggi;

public class AddSeatsEventMessage implements EventMessage{
    
    private final String eventName;
    private final Integer eventSeats;
    
    public AddSeatsEventMessage(String eventName, Integer eventSeats) {
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
