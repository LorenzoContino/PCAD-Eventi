package com.eventi.server;

import java.net.ServerSocket;
import java.net.Socket;

import com.eventi.gestione_eventi.Eventi;

public class EventsServer{
    
    private ServerSocket socket;

    public void start(){
        initEventServerThreads();
        while (!socket.isClosed()) {
            Socket client_socket;
            try {
                client_socket = socket.accept();
            } catch (Exception e) {    
                System.out.println("Server error: " + e.getMessage());
                return;
            }
            new Thread(new ServeClient(client_socket));
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
        new Thread(new Eventi());
    }


    public static void main(String[] args) {
        EventsServer server = new EventsServer(6000);
        server.start();
    }

}
