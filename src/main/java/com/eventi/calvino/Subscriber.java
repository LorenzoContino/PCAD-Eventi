package com.eventi.calvino;

import java.util.HashMap;
import java.util.Map;

import com.eventi.calvino.calvino_exceptions.SubscriberAlreadyProducerException;
import com.eventi.calvino.calvino_exceptions.SubscriberNotConsumerException;
import com.eventi.calvino.calvino_exceptions.SubscriberNotPeekerException;
import com.eventi.calvino.calvino_exceptions.SubscriberNotProducerException;
import com.eventi.calvino.calvino_exceptions.SubscriberAlreadyConsumerException;
import com.eventi.calvino.calvino_exceptions.SubscriberAlreadyPeekerException;
import com.eventi.calvino.calvino_exceptions.TopicAlreadyHasConsumerException;
import com.eventi.calvino.calvino_exceptions.TopicAlreadyHasProducerException;
import com.eventi.messaggi.EventMessage;

public abstract class Subscriber {

    private Map<String, Topic> myProducer = new HashMap<>();
    private Map<String, Topic> myConsumer = new HashMap<>();
    private Map<String, Topic> myPeeker   = new HashMap<>();


    public Map<String, Topic> getMyProducer() {
        return myProducer;
    }

    public Map<String, Topic> getMyConsumer() {
        return myConsumer;
    }

    public Map<String, Topic> getMyPeeker() {
        return myPeeker;
    }

    public void SubscribeProd(String producer_name) throws SubscriberAlreadyProducerException, TopicAlreadyHasProducerException {
        if (this.myProducer.containsKey(producer_name)) {
            throw new SubscriberAlreadyProducerException("subscriber is already a producer");
        }
        this.myProducer.put(producer_name, Calvino.SubscribeProd(producer_name));
    }

    public void SubscribeCons(String consumer_name) throws SubscriberAlreadyConsumerException, TopicAlreadyHasConsumerException {
        if (this.myConsumer.containsKey(consumer_name)) {
            throw new SubscriberAlreadyConsumerException("subscriber is already a consumer");
        }
        this.myConsumer.put(consumer_name, Calvino.SubscribeCons(consumer_name));
    }

    public void SubscribePeek(String peeker_name) throws SubscriberAlreadyPeekerException {
        if (this.myPeeker.containsKey(peeker_name)) {
            throw new SubscriberAlreadyPeekerException("subscriber is already a peeker");
        }
        this.myPeeker.put(peeker_name, Calvino.SubscribePeek(peeker_name));
    }

    public void UnSubscribeCons(String consumer_name) throws SubscriberNotConsumerException {
        if(!this.myConsumer.containsKey(consumer_name)){
            throw new SubscriberNotConsumerException("subscriber is not a consumer");
        }
        Calvino.UnSubscribeCons(consumer_name);
        this.myConsumer.remove(consumer_name);
    }

    public void UnSubscribeProd(String producer_name) throws SubscriberNotProducerException {
        if(!this.myProducer.containsKey(producer_name)){
            throw new SubscriberNotProducerException("subscriber is not a producer");
        }
        Calvino.UnSubscribeProd(producer_name);
        this.myProducer.remove(producer_name);
    }

    public void UnSubscribePeek(String peeker_name) throws SubscriberNotPeekerException {
        if(!this.myPeeker.containsKey(peeker_name)){
            throw new SubscriberNotPeekerException("subscriber is not a peeker");
        }
        this.myPeeker.remove(peeker_name);
    }

    public void produce(String topicName, EventMessage message) throws SubscriberNotProducerException{
        if (myProducer.containsKey(topicName)) {
            myProducer.get(topicName).getTopicData().add(message);
            return;
        }
        throw new SubscriberNotProducerException("subscriber is not a producer");
    }

    public EventMessage consume(String topicName) throws SubscriberNotConsumerException {
        if (myConsumer.containsKey(topicName)) {
            if (myConsumer.get(topicName).hasData()){
                return myConsumer.get(topicName).getTopicData().poll();
            }
        }
        throw new SubscriberNotConsumerException("subscriber is not a consumer");
    }

    public EventMessage peek(String topicName) throws SubscriberNotPeekerException {
        if (myPeeker.containsKey(topicName)) {
            if (myPeeker.get(topicName).hasData()){
                return myPeeker.get(topicName).getTopicData().element();
            }
        }
        throw new SubscriberNotPeekerException("subscriber is not a peeker");
    }

    public boolean isProducer(String topicName){
        return myProducer.containsKey(topicName);
    }

    public boolean isConsumer(String topicName){
        return myConsumer.containsKey(topicName);
    }
}
