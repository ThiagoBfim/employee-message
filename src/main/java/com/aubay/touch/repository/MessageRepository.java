package com.aubay.touch.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aubay.touch.domain.Employee;
import com.aubay.touch.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByDeliveryTimeAfter(LocalDateTime now);
}
