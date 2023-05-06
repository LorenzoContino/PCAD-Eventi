package com.eventi.calvino;

import java.util.Map;
import java.util.TreeMap;

import com.eventi.calvino.calvino_exceptions.TopicAlreadyHasConsumerException;
import com.eventi.calvino.calvino_exceptions.TopicAlreadyHasProducerException;

public final class Calvino {

    private static Map<String, Topic> topicMap;
    
    static{
        topicMap = new TreeMap<>();
    }
    
        
    protected static synchronized Topic SubscribeProd(String topicName) {

        if (topicMap.containsKey(topicName)) {
            Topic topic = topicMap.get(topicName);
            if (!topic.getHasProducer()) {
                topic.setHasProducer(true);
                return topic;
            }
            throw new TopicAlreadyHasProducerException("Topic ha già produttore");
        } else {
            Topic newTopic = generateTopic(topicName);
            newTopic.setHasProducer(true);
            return newTopic;
        }

    }

    protected static synchronized Topic SubscribeCons(String topicName) {

        if (topicMap.containsKey(topicName)) {
            Topic topic = topicMap.get(topicName);
            if (!topic.getHasConsumer()) {
                topic.setHasConsumer(true);
                return topic;
            }
            throw new TopicAlreadyHasConsumerException("Topic ha già consumatore");
        } else {

            Topic newTopic = generateTopic(topicName);
            newTopic.setHasConsumer(true);
            return newTopic;

        }
    }
    
    protected static synchronized Topic SubscribePeek(String topicName) {
        if (topicMap.containsKey(topicName)) {
            return topicMap.get(topicName);
        } else {
            Topic newTopic = generateTopic(topicName);
            return newTopic;
        }
    }

    protected static synchronized void UnSubscribeProd(String topicName) {
        if (topicMap.containsKey(topicName)) {
            Topic topic = topicMap.get(topicName);
            topic.setHasProducer(false);
        }
    }

    protected static synchronized void UnSubscribeCons(String topicName) {
        if (topicMap.containsKey(topicName)) {
            Topic topic = topicMap.get(topicName);
            topic.setHasConsumer(false);
        }
    }

    private static synchronized Topic generateTopic(String topic_name) {
        topicMap.put(topic_name, new Topic(topic_name));
        return topicMap.get(topic_name);

    }

    public static Integer topicCount(){
        return topicMap.size();
    }

}