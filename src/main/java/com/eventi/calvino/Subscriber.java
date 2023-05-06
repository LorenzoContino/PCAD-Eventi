package com.eventi.calvino;

import java.util.HashMap;
import java.util.Map;

import com.eventi.calvino.calvinoExceptions.TopicAlreadyHasConsumerException;
import com.eventi.calvino.calvinoExceptions.TopicAlreadyHasProducerException;
import com.eventi.messaggi.EventMessage;

public abstract class Subscriber {

    private Map<String, Topic> myProducer = new HashMap<>();
    private Map<String, Topic> myConsumer = new HashMap<>();
    private Map<String, Topic> myPeeker   = new HashMap<>();

    public void SubscribeProd(String producer_name) throws TopicAlreadyHasProducerException{
        this.myProducer.put(producer_name, Calvino.SubscribeProd(producer_name));
    }

    public void SubscribeCons(String consumer_name) throws TopicAlreadyHasConsumerException{
        this.myConsumer.put(consumer_name, Calvino.SubscribeCons(consumer_name));
    }

    public void SubscribePeek(String peeker_name) {
        this.myPeeker.put(peeker_name, Calvino.SubscribePeek(peeker_name));
    }

    public void UnSubscribeCons(String consumer_name) {
        Calvino.UnSubscribeCons(consumer_name);
        this.myConsumer.remove(consumer_name);
    }

    public void UnSubscribeProd(String producer_name) {
        Calvino.UnSubscribeProd(producer_name);
        this.myProducer.remove(producer_name);
    }

    public void UnSubscribePeek(String peeker_name) {
        this.myPeeker.remove(peeker_name);
    }

    public void produce(String topicName, EventMessage message) {
        if (myProducer.containsKey(topicName)) {
            myProducer.get(topicName).getTopicData().add(message);
        }
    }

    public EventMessage consume(String topicName) {
        if (myConsumer.containsKey(topicName)) {
            if (myConsumer.get(topicName).hasData()){
                return myConsumer.get(topicName).getTopicData().poll();
            }
        }
        return null; 
    }

    public EventMessage peek(String topicName) {
        if (myPeeker.containsKey(topicName)) {
            if (myPeeker.get(topicName).hasData()){
                return myPeeker.get(topicName).getTopicData().element();
            }
        }
        return null; 
    }

    public boolean isProducer(String topicName){
        return myProducer.containsKey(topicName);
    }

    public boolean isConsumer(String topicName){
        return myConsumer.containsKey(topicName);
    }
}
