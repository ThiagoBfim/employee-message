package com.aubay.touch.service.delivery;

public interface IDeliveryService {

    String getChannel();

    MessageResult sendMessage(MessageCtx messageCtx);
}
