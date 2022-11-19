package com.aubay.touch.controller.response;

import java.math.BigInteger;

public record MessageResponse(BigInteger id, String title, String status, String message, Object deliveryDate,
                              BigInteger deliverySuccess, BigInteger deliveryFailed) {

}
