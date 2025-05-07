package com.example.kafkaapi;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class KafkaProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/produce")
    public String sendMessage(@RequestBody String message) {
        kafkaTemplate.send("my_topic", message);
        return "Message sent: " + message;
    }
}
