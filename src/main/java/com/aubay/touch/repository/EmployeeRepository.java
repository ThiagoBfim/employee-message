package com.aubay.touch.repository;

import java.util.List;
import java.util.Set;

import org.apache.catalina.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aubay.touch.domain.Employee;
import com.aubay.touch.domain.EmployeeGroup;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByGroups(Set<EmployeeGroup> group);
}
