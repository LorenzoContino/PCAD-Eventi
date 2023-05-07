package com.eventi.server;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.TreeMap;

import org.json.JSONObject;
import org.junit.Test;

import com.eventi.gestione_eventi.Evento;
import com.eventi.messaggi.AddSeatsEventMessage;
import com.eventi.messaggi.BroadcastEventsListMesage;
import com.eventi.messaggi.EventMessage;
import com.eventi.messaggi.ListEventMessage;

public class ServeClientTest {
    @Test
    public void testHadleJSON_ADD() {
        String jsonString =   "{\"role\": \"ADMIN\",\"command\": {\"type\": \"ADD\",\"name\": \"FOO\",\"seats\": 345}}";
        JSONObject json = new JSONObject(jsonString);
        ServeClient testClient = new ServeClient(null);
        EventMessage result;
        try {
            result = testClient.handleJSON(json);
        } catch (Exception e) {
            assertTrue(false);
            return;
        }
        assertTrue(result instanceof AddSeatsEventMessage);
    }

    @Test
    public void testHadleJSON_LIST() {
        String jsonString =   "{\"role\": \"USER\",\"command\": {\"type\": \"LIST\"}}";
        JSONObject json = new JSONObject(jsonString);
        ServeClient testClient = new ServeClient(null);
        EventMessage result;
        try {
            result = testClient.handleJSON(json);
        } catch (Exception e) {
            assertTrue(false);
            return;
        }
        assertTrue(result instanceof ListEventMessage);
    }


    @Test
    public void testHadleJSON_EXC() {
        String jsonString =   "{\"role\": \"USER\",\"command\": {\"type\": \"PIPPO\"}}";
        JSONObject json = new JSONObject(jsonString);
        ServeClient testClient = new ServeClient(null);
        try {
            testClient.handleJSON(json);
        } catch (Exception e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testGenerateEventListJSON() {
        ServeClient testClient = new ServeClient(null);
        Map<String, Evento> test_map = new TreeMap<String, Evento>();
        test_map.put("pippo", new Evento("pippo", 200));
        test_map.put("pluto", new Evento("pluto", 100));
        test_map.put("paperino", new Evento("paperino", 300));
        BroadcastEventsListMesage message = new BroadcastEventsListMesage(test_map);
        String testJSON = testClient.generateEventListJSON(message);        
        System.out.println(testJSON);
        assertTrue(true);
    }
}
