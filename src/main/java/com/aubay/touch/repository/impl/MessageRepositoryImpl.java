package com.aubay.touch.repository.impl;

import com.aubay.touch.controller.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public class MessageRepositoryImpl implements CustomMessageRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<MessageResponse> findAllMessages() {
        return (List<MessageResponse>) entityManager.createNamedQuery("MessageResponse")
                .getResultList();
    }
}
