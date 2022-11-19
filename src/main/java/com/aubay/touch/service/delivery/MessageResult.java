package com.aubay.touch.service.delivery;

import org.springframework.lang.Nullable;

public record MessageResult(@Nullable String errorMessage, boolean success) {

}
