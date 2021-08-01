package com.example.project.api;

import com.example.project.dto.ChattingMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class ChatApiController {

    private final KafkaTemplate<String, ChattingMessage> kafkaTemplate;

    @PostMapping("/api/send")
    public void sendMessage(@RequestBody ChattingMessage message){
        message.setTimeStamp(LocalDate.now().toString());

        try{
            kafkaTemplate.send("topic", message).get();
        }catch(InterruptedException | ExecutionException e){
            throw  new RuntimeException(e);
        }
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public ChattingMessage broadcastGroupMessage(@Payload ChattingMessage message) {
        //Sending this message to all the subscribers
        return message;
    }

}
