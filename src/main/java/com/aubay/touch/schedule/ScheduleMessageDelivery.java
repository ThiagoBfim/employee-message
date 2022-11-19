package com.aubay.touch.schedule;

import com.aubay.touch.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduleMessageDelivery {

    private static final Logger log = LoggerFactory.getLogger(ScheduleMessageDelivery.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final MessageService messageService;

    public ScheduleMessageDelivery(MessageService messageService) {
        this.messageService = messageService;
    }

    @Scheduled(cron = "0 15 * * * ?")
    public void reportCurrentTime() {
        log.info("Time executing the delivery messages is now {}", dateFormat.format(new Date()));
        messageService.sendMessage();
    }
}
