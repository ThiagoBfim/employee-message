package com.aubay.touch.service.delivery;

public interface IDeliveryService {

    String getChannel();

    DeliveryMessage sendMessage(String username, String identifier);
}
