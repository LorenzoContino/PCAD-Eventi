package com.eventi.gestione_eventi;

import java.util.TreeMap;

import com.eventi.calvino.Subscriber;

import java.util.Map;


public class Eventi extends Subscriber{    
    private Map<String,Evento> eventList;

    public Eventi() {
        this.eventList = new TreeMap<>();
    }

    public void crea(String name, Integer seats) {
        if (this.containsEvent(name))
            throw new IllegalArgumentException("L'evento che si intende creare è già esistente");
        
        eventList.put(name,new Evento(name, seats));
    }

    public void aggiungi(String name, Integer seats) {
       
        if(!this.containsEvent(name))
            throw new IllegalArgumentException("Evento non esistente");
        eventList.get(name).addSeats(seats);

    }

    public void prenota(String name, Integer seats) {
        
       
        if(!this.containsEvent(name))
            throw new IllegalArgumentException("Evento non esistente");
        eventList.get(name).removeSeats(seats);

    }

    public void listaEventi() {
        for (Evento evento : eventList.values()) {
            System.out.println( evento.toString());
           
        }
    }

    public void chiudi(String name) { 
        if(!this.containsEvent(name))
            throw new IllegalArgumentException("L'evento che si intende chiudere non è esistente");
        eventList.remove(name);       
}

    private boolean containsEvent(String name) {
        return this.eventList.containsKey(name);
    }

}
