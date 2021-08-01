package com.example.project.component;

import com.example.project.config.KafkaConstants;
import com.example.project.dto.ChattingMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void listen(ChattingMessage message){
        System.out.println("sending message..");
        simpMessagingTemplate.convertAndSend("/topic/group", message);
    }

}
