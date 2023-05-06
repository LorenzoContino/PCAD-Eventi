package com.eventi.server;

import java.net.ServerSocket;
import java.net.Socket;

public class EventsServerSocket implements Runnable{
    
    private ServerSocket socket;

    public void start(){
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

    public EventsServerSocket(int port) {
        try {    
            this.socket = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("Server unable to create the socket: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        start();
    }

}
