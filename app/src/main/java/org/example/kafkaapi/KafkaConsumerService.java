package com.example.kafkaapi;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class KafkaConsumerService {

    private final List<String> messages = new CopyOnWriteArrayList<>();

    // consumerで取得した時の処理
    @KafkaListener(topics = "output-topic", groupId = "my-consumer-group")
    public void consume(String message) {
        System.out.println("Received: " + message);
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
