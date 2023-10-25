package me.songha.kafka.basic.sample1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

//@Service
public class KafkaSampleProducerService {

    /**
     * RestTemplate처럼 application.yml에 설정해놓은 Kafka 서버로 바로 통신할 수 있게 해주는 역할을 한다.
     * KafkaTemplate 을 주입받고 이것을 통해 message를 보낸다.
     * 그리고 send() 에서는 첫번째 파라미터로 topic을 두번째 파라미터로는 실제로 보낼 메세지를 넣어주면 된다.
     */
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        System.out.println("send message : " + message);
//        this.kafkaTemplate.send("topics-sample", message);
        /**
         * send() 비동기로 처리된다.
         * 대체로 Kafka로 메세지를 발행할때 굳이 반환값이 궁금할 일은 없다.
         * 하지만 때론, "정확히 브로커까지 전달이 되었는지" 판별이 필요할 때가 있다.
         * 이때 동기적으로 처리해 버리면 성능이슈가 있으니 주의하자.
         * future.get() 메소드에서 블로킹이 발생하기 때문에 결과적으로 동기적으로 프로세스가 처리된다.
         */

        /**
         * Kafka는 빠른 스트림 처리 플랫폼이다.
         * 따라서 후속 메시지가 이전 메시지의 결과를 기다리지 않도록 결과를 비동기적으로 처리하는 것이 좋다.
         * 콜백을 통해 이를 수행할 수 있다.
         */
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("topics-sample", message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }
}