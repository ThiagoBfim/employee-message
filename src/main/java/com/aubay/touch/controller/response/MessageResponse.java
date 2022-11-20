package com.aubay.touch.controller.response;

import java.math.BigInteger;

public record MessageResponse(BigInteger id, String title, String status, String message, Object deliveryDate,
                              String groups, String channels, BigInteger deliverySuccess, BigInteger deliveryFailed) {

}
