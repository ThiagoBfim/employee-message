package com.aubay.touch.repository.impl;

import com.aubay.touch.controller.response.MessageResponse;

import java.util.List;

public interface CustomMessageRepository {

    List<MessageResponse> findAllMessages();

    void cleanDatabase();
}
