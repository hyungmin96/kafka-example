package com.example.project.component;

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
            topics = "topic",
            groupId = "group_id"
    )
    public void listen(ChattingMessage message){
        System.out.println("sending message..");
        simpMessagingTemplate.convertAndSend("/topic/group", message);
    }

}
