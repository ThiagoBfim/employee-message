package com.aubay.touch.service.delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class SMSDeliveryService implements IDeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSDeliveryService.class.getName());

    public String getChannel() {
        return "SMS";
    }

    public MessageResult sendMessage(MessageCtx messageCtx) {
        LOGGER.info("Sending SMS message");
        return new MessageResult(null, true);
    }
}
