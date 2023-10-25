package me.songha.kafka.basic.sample2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class PingService {
    @Autowired
    private KafkaTemplate<String, Ping> pingKafkaTemplate;

    @Value(value = "${ping.topic.name}")
    private String pingTopicName;

    public Pong pingAndPong(Ping ping) throws Exception {

        System.out.println("send message : " + ping);
        CompletableFuture<SendResult<String, Ping>> future = pingKafkaTemplate.send(pingTopicName, ping);

//        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("topics-sample", message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + ping +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        ping + "] due to : " + ex.getMessage());
            }
        });

        return new Pong(ping.getName(), ping.getMsg());
    }
}