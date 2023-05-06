package com.eventi.messaggi;

public class BookSeatsEventMessage implements EventMessage{
    
    private final String eventName;
    private final Integer eventSeats;
    
    public BookSeatsEventMessage(String eventName, Integer eventSeats) {
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