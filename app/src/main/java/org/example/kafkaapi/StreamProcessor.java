package com.example.kafkaapi;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class StreamProcessor {
    private static final Logger logger = LoggerFactory.getLogger(StreamProcessor.class);
    private KafkaStreams streams;

    @PostConstruct
    public void startStream() {
        logger.info(">>> Starting Kafka Streams initialization...");

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "springboot-streams-app-web");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> input = builder.stream("my_topic");
        input.mapValues((String value) -> {
                logger.info("Received value: {}", value);
                String upper = value.toUpperCase();
                logger.info("Transformed to: {}", upper);
                return upper;
            })
            .to("output-topic");

        Topology topology = builder.build();
        logger.info("Topology Description: \n{}", topology.describe());

        streams = new KafkaStreams(topology, props);
        streams.start();
        logger.info(">>> Kafka Streams started successfully.");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info(">>> Shutting down Kafka Streams...");
            streams.close();
        }));
    }
}
