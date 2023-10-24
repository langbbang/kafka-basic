package me.songha.kafka.basic;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
 
import java.io.IOException;
 
@Service
public class KafkaSampleConsumerService {
 
    @KafkaListener(topics = "topics-sample", groupId = "group-id-sample-test")
    public void consume(String message) throws IOException {
        System.out.println("receive message : " + message);
    }
}