package co.istad.orderservice.kafka;

import co.istad.orderservice.domain.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {

    private final NewTopic topic;
    private final KafkaTemplate<String, Map<String, Object>> kafkaTemplate;


    public void sendMessage(Map<String, Object> orderEvent) {
        log.info("Order event => {}", orderEvent);

        // create message
        Message<Map<String, Object>> message = MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();

        kafkaTemplate.send(message);
    }

}
