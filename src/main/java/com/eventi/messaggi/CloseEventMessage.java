package com.eventi.messaggi;

public class CloseEventMessage implements EventMessage{

    private final Integer clientId;
    private final String eventName;


    public CloseEventMessage(Integer clientId, String eventName) {
        this.clientId = clientId;
        this.eventName = eventName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public String getEventName() {
        return eventName;
    }
    
}
