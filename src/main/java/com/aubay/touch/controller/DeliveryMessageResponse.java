package com.aubay.touch.controller;

public record DeliveryMessageResponse(String message, String channel, String employee, boolean success,
                                      java.time.LocalDateTime deliveryTime){
}
