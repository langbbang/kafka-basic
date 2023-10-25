package me.songha.kafka.basic.sample1;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

//@Service
public class KafkaSampleConsumerService {

    /**
     * Producer가 보낸 메세지가 있다면 @KafkaListener에서 이를 받아준다.
     */
    @KafkaListener(topics = "topics-sample", groupId = "group-id-sample-test")
    public void consume(String message) throws IOException {
        System.out.println("receive message : " + message);
    }
}