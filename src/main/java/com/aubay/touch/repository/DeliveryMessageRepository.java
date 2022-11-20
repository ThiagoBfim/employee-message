package com.aubay.touch.repository;

import com.aubay.touch.domain.DeliveryMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryMessageRepository extends JpaRepository<DeliveryMessage, Long> {
}
