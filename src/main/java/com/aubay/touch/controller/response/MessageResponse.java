package com.aubay.touch.controller.response;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class MessageResponse {

    private final BigInteger id;
    private final String title;
    private final String message;
    private final Object deliveryDate;
    private final BigInteger deliverySuccess;
    private final BigInteger deliveryFailed;

    public MessageResponse(BigInteger id, String title, String message, Object deliveryDate, BigInteger deliverySuccess, BigInteger deliveryFailed) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.deliveryDate = deliveryDate;
        this.deliverySuccess = deliverySuccess;
        this.deliveryFailed = deliveryFailed;
    }

    public BigInteger getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Object getDeliveryDate() {
        return deliveryDate;
    }

    public BigInteger getDeliverySuccess() {
        return deliverySuccess;
    }

    public BigInteger getDeliveryFailed() {
        return deliveryFailed;
    }
}
