package com.aubay.touch.service.delivery;

public interface IDeliveryService {

    String getChannel();

    DeliveredMessage sendMessage(DeliveryMessage deliveryMessage);
}
