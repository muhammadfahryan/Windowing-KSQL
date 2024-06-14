package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class TestKsqlWindow {
    public static void main(String[] args) {

        String topicName = "test-ksql-window";
        Properties props = new Properties();

        props.put("bootstrap.servers", "****");
        props.put("ssl.truststore.location", "****");
        props.put("security.protocol", "SASL_SSL");
        props.put("ssl.truststore.password", "****");
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"****\" password=\"****\";");
        props.put("basic.auth.user.info", "****:****");
        props.put("basic.auth.credentials.source", "USER_INFO");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        try {
            // Current time
            long startTime = System.currentTimeMillis();

            // Create messages
            sendMessage(producer, topicName, "key3", "{\"value\": 1000}", startTime); // Message pertama
            Thread.sleep(10000); // Tunggu 10 detik

            // Message kedua
            sendMessage(producer, topicName, "key3", "{\"value\": 1101}", startTime + 10000); // Message kedua

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    private static void sendMessage(KafkaProducer<String, String> producer, String topic, String key, String value, long timestamp) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, timestamp, key, value);
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            } else {
                System.out.println("Sent message: " + key + " -> " + value + " at " + timestamp);
            }
        });
    }
    }
