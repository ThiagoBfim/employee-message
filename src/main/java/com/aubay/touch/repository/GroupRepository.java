package com.aubay.touch.repository;

import com.aubay.touch.domain.Channel;
import com.aubay.touch.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
