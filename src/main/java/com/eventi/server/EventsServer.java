package com.eventi.server;

import java.net.ServerSocket;
import java.net.Socket;

import com.eventi.gestione_eventi.Eventi;

public class EventsServer{
    
    private ServerSocket socket;


    public void start(){
        initEventServerThreads();
        System.out.println("SERVER: Starting server socket.");
        while (!socket.isClosed()) {
            Socket client_socket;
            try {
                client_socket = socket.accept();
            } catch (Exception e) {    
                System.out.println("Server error: " + e.getMessage());
                continue;
            }
            System.out.println("SERVER: Connection recived.");
            new Thread(new ServeClient(client_socket)).start();
        }
    }

    public EventsServer(int port) {
        try {    
            this.socket = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("Server unable to create the socket: " + e.getMessage());
        }
    }

    private void initEventServerThreads(){
        new Thread(new Eventi()).start();
    }

    private static void printPresentation(){
        System.out.println("=======================================");
        System.out.println("=                                     =");
        System.out.println("=   PCAD - Event Handler API          =");
        System.out.println("=                                     =");
        System.out.println("=======================================\n");
        System.out.println("Thread_ementi:");
        System.out.println("Copyright (C) 2023 Andrea Valenzano 4548315");
        System.out.println("Copyright (C) 2023 Lorenzo Contino 4832500");
        System.out.println("Copyright (C) 2023 Eugenio Pallestrini 4878184\n");
        System.out.println("This program is free software; you can redistribute it and/or modify");
        System.out.println("it under the terms of the GNU General Public License as published by");
        System.out.println("the Free Software Foundation; either version 2 of the License, or");
        System.out.println("(at your option) any later version.\n");
        System.out.println("Server web per la gestione concorrente di eventi tramite API(JSON)\n");
        System.out.println("Start LOGing:");
    }
    public static void main(String[] args) {
        printPresentation();
        EventsServer server = new EventsServer(6000);
        server.start();
    }

}
