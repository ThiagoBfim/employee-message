package com.aubay.touch.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aubay.touch.domain.Employee;
import com.aubay.touch.domain.Group;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByGroupsIn(Set<Group> group);
}
