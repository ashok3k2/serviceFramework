package com.abhinav.learn_spring.consumers;

import com.abhinav.learn_spring.models.entries.AppUpdateConfigEntry;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log
@Component
public class Consumer {
    @RabbitListener(queues = "learn_spring_queue")
    public void recievedMessage(AppUpdateConfigEntry appUpdateConfigEntry) {
        log.info("abhinav - msg recevied from queue" + appUpdateConfigEntry.toString());
        System.out.println("Recieved Message: " + appUpdateConfigEntry);
    }
}
