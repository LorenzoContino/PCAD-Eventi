package com.eventi.server;

import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class ServeClient implements Runnable{

    private Socket clientSocket;

    @Override
    public void run() {
        serve();
    }

    private void serve(){
        while (clientSocket.isConnected() /* Finche il client non si disconnette */) {
            String recv_message = new String();
            try (InputStreamReader in = new InputStreamReader(
                clientSocket.getInputStream(), StandardCharsets.UTF_8)){
                    var message_size = in.read(recv_message.toCharArray());
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    protected ServeClient(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    
}
