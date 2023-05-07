package com.eventi.messaggi;

public class ListEventMessage implements EventMessage{
    
    private final Integer clientId;


    public ListEventMessage(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getClientId() {
        return clientId;
    }

}
