package com.aubay.touch.service.delivery;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class SMSDeliveryService implements IDeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSDeliveryService.class.getName());

    private final String ACCOUNT_SID;

    private final String AUTH_TOKEN;

    public SMSDeliveryService(@Value("${twillio.account-sid}") String accountSid, @Value("${twillio.auth-token}") String authToken) {
        ACCOUNT_SID = accountSid;
        AUTH_TOKEN = authToken;
    }

    public String getChannel() {
        return "SMS";
    }

    public MessageResult sendMessage(MessageCtx messageCtx) {
        LOGGER.info("Sending SMS message");
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber(messageCtx.employeeIdentifier()),
                            "MG19635c819ea38f255facaa811eb3a06e",
                            messageCtx.message())
                    .create();

            System.out.println(message.getSid());
        } catch (Exception ex) {
            LOGGER.error("Error sending SMS message!", ex);
            return new MessageResult(ex.getMessage(), false);

        }
        return new MessageResult(null, true);
    }
}
