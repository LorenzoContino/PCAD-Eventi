package com.eventi.calvino;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.eventi.messaggi.EventMessage;

public class Topic {

    private String topic_name;
    private ConcurrentLinkedQueue<EventMessage> topic_data;
    private boolean hasProducer = false;
    private boolean hasConsumer= false;

    protected Topic(String name){
        topic_data = new ConcurrentLinkedQueue<EventMessage>();
        this.topic_name= name;
    }

    public void setHasProducer(boolean hasProducer) {
        this.hasProducer = hasProducer;
    }

    public void setHasConsumer(boolean hasVonsumer) {
        this.hasConsumer = hasVonsumer;
    }

    public boolean getHasProducer() {
        return hasProducer;
    }

    public boolean getHasConsumer() {
        return hasConsumer;
    }

    public boolean hasData() {
        return topic_data.size()>0;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public ConcurrentLinkedQueue<EventMessage> getTopicData(){
        return this.topic_data;
    }
      
}
