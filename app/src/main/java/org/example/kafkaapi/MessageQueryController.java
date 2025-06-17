package com.example.kafkaapi;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageQueryController {

    private final KafkaConsumerService consumerService;

    public MessageQueryController(KafkaConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    // ConsumerAPI
    // メソッド GET
    // エンドポイント　http://localhost:8080/api/consume
    @GetMapping("/consume")
    public List<String> getConsumedMessages() {
        return consumerService.getMessages();
    }
}
