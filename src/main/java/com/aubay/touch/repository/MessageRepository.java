package com.aubay.touch.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.aubay.touch.repository.impl.CustomMessageRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aubay.touch.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long>, CustomMessageRepository {

    List<Message> findAllByDeliveryTimeBefore(LocalDateTime now);
}
