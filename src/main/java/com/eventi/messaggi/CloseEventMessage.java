package com.eventi.messaggi;

public class CloseEventMessage implements EventMessage{

    private final String eventName;
    
    public CloseEventMessage(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

}
