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

    @Override

    public void cleanDatabase() {
        entityManager.createNativeQuery("DELETE FROM rl_employee_group WHERE 1=1;\n" +
                        "DELETE FROM rl_message_group WHERE 1=1;\n" +
                        "DELETE FROM TB_DELIVERY_MESSAGE WHERE 1=1;\n" +
                        "DELETE FROM RL_DELIVERY_CHANNEL WHERE 1=1;\n" +
                        "DELETE FROM TB_EMPLOYEE_CHANNEL WHERE 1=1;\n" +
                        "DELETE FROM tb_employee WHERE 1=1;\n" +
                        "DELETE FROM tb_message WHERE 1=1;")
                .executeUpdate();
    }
}
