package com.eventi.messaggi;

import java.util.Map;

import com.eventi.gestione_eventi.Evento;

public class BroadcastEventsListMesage implements EventMessage{

    private Map<String,Evento> eventList;

    public BroadcastEventsListMesage(Map<String, Evento> eventList) {
        this.eventList = eventList;
    }

    public Map<String, Evento> getEventList() {
        return eventList;
    }
    
}
