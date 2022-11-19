package com.aubay.touch.service.delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class EmailDeliveryService implements IDeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailDeliveryService.class.getName());

    public String getChannel() {
        return "Email";
    }

    public MessageResult sendMessage(MessageCtx messageCtx) {
        LOGGER.info("Sending Email message");
        return new MessageResult(null, true);
    }
}
