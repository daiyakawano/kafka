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

    @GetMapping("/consume")
    public List<String> getConsumedMessages() {
        return consumerService.getMessages();
    }
}
