package com.aubay.touch.service.delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class WhatsappDeliveryService implements IDeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhatsappDeliveryService.class.getName());

    public String getChannel() {
        return "Whatsapp";
    }

    public MessageResult sendMessage(MessageCtx messageCtx) {
        LOGGER.info("Sending Whatsapp message");
        return new MessageResult(null, true);
    }
}
