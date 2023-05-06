package com.eventi.server;

import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.eventi.eratostene.Subscriber;

public class ServeClient extends Subscriber implements Runnable{

    private Socket clientSocket; 

    @Override
    public void run() {
        serve();
    }

    private void serve(){
        while (clientSocket.isConnected() /* Finche il client non si disconnette */) {
            var message = readJSON();
        }
    }

    private JSONObject readJSON() throws InvalidMessageException{
        CharBuffer recv_buffer = CharBuffer.allocate(1024);
        try (InputStreamReader in = new InputStreamReader(
            clientSocket.getInputStream(), StandardCharsets.UTF_8)){
                var message_size = in.read(recv_buffer);
        } catch (Exception e) {
            throw new InvalidMessageException(e.getMessage());
        }
        return new JSONObject(recv_buffer);
    }

    protected ServeClient(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    
}
