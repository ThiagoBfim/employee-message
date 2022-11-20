package com.aubay.touch.service.delivery;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class WhatsappDeliveryService implements IDeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhatsappDeliveryService.class.getName());

    private final String ACCOUNT_SID;

    private final String AUTH_TOKEN;

    public WhatsappDeliveryService(@Value("${twillio.account-sid}") String accountSid, @Value("${twillio.auth-token}") String authToken) {
        ACCOUNT_SID = accountSid;
        AUTH_TOKEN = authToken;
    }

    public String getChannel() {
        return "Whatsapp";
    }

    public MessageResult sendMessage(MessageCtx messageCtx) {
        LOGGER.info("Sending Whatsapp message");
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("whatsapp:" + messageCtx.employeeIdentifier()),
                            new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                            messageCtx.message())
                    .create();
            System.out.println(message.getSid());
            return new MessageResult(null, true);

        } catch (Exception ex) {
            LOGGER.error("Error sending whatsapp message!", ex);
            return new MessageResult(ex.getMessage(), false);

        }
    }

}
