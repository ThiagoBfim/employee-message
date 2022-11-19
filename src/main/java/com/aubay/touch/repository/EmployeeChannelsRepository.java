package com.aubay.touch.repository;

import com.aubay.touch.domain.EmployeeChannel;
import com.aubay.touch.domain.Message;
import com.aubay.touch.repository.impl.CustomMessageRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeChannelsRepository extends JpaRepository<EmployeeChannel, Long> {

}
