package com.aubay.touch.service.delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class TelegramDeliveryService implements IDeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramDeliveryService.class.getName());

    public String getChannel() {
        return "Telegram";
    }

    public MessageResult sendMessage(MessageCtx messageCtx) {
        LOGGER.info("Sending Telegram message");
        return new MessageResult(null, true);
    }
}
