package com.example.project.api;

import com.example.project.config.KafkaConstants;
import com.example.project.dto.ChattingMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final Logger log = LogManager.getLogger();

    @PostMapping("/api/send")
    public void sendMessage(@RequestBody ChattingMessage message){
        message.setTimeStamp(LocalDate.now().toString());

        try{
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        }catch(InterruptedException | ExecutionException e){
            throw  new RuntimeException(e);
        }
    }

    @MessageMapping("/send/chat")
    public ChattingMessage broadcastGroupMessage(@Payload ChattingMessage message) {
        //Sending this message to all the subscribers
        log.info("value : " + message.getMessage());
        kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message);

        return message;
    }

}
