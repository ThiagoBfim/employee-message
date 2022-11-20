package com.aubay.touch.service.delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Order(1)
public class EmailDeliveryService implements IDeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailDeliveryService.class.getName());

    private final String rapidKey;

    private final String rapidHost;

    public EmailDeliveryService(@Value("${xrapid.key}") String rapidKey, @Value("${xrapid.host}") String rapidHost) {
        this.rapidKey = rapidKey;
        this.rapidHost = rapidHost;
    }

    public String getChannel() {
        return "Email";
    }

    public MessageResult sendMessage(MessageCtx messageCtx) {
        LOGGER.info("Sending Email message");
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://simple-mail-sending.p.rapidapi.com/mail/sendmail"))
                    .header("content-type", "application/json")
                    .header("X-RapidAPI-Key", rapidKey)
                    .header("X-RapidAPI-Host", rapidHost)
                    .method("POST", HttpRequest.BodyPublishers.ofString(String.format("""
                                                   {
                                                   "to": "%s",
                                                   "message": "%s",
                                                   "subject": "%s"
                             }
                            """, messageCtx.employeeIdentifier(), messageCtx.message(), messageCtx.title())))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            return new MessageResult(null, true);
        } catch (Exception ex) {
            LOGGER.error("Error sending Email message!", ex);
            return new MessageResult(ex.getMessage(), false);

        }


    }
}
