package com.eventi.gestione_eventi;

public class Evento {

    private String name;
    private Integer seats = 0;
    private Integer maxSeats;

    public Evento(String name, Integer seats) {
        if (seats < 0)
            throw new IllegalArgumentException("Il numero di posti per un evento non può essere negativo");
        this.seats = seats;
        this.name = name;
        this.maxSeats=seats;
        
    }

    protected  boolean checkSeatsNumber() {
        return this.seats>0;
    }

    @Override
    public String toString() {
        return "Evento " + name + ", posti disponibili :" + seats;
    }

    public String getName() {
        return name;
    }

    public Integer getSeats() {
        return seats;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    protected void addSeats(Integer addedSeats) {

        if (addedSeats < 0)
            throw new IllegalArgumentException("Non è possibile aggiungere un numero di posti negativo");
        this.seats += addedSeats;
        this.maxSeats += addedSeats;
    }

    

    public void removeSeats(Integer removedSeats) {
        if (removedSeats < 0)
            throw new IllegalArgumentException("Non è possibile prenotare  un numero di posti negativo");
        if(this.seats-removedSeats<0)
            throw new ArgumentOutOfBoundException("Non ci sono abbastanza posti disponibili");

        this.seats -= removedSeats;
    }



}


