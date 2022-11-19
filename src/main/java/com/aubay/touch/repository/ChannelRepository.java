package com.aubay.touch.repository;

import com.aubay.touch.domain.Channel;
import com.aubay.touch.domain.Message;
import com.aubay.touch.repository.impl.CustomMessageRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
