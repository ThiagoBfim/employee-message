package com.aubay.touch.repository;

import java.util.List;
import java.util.Set;

import com.aubay.touch.domain.EmployeeChannel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aubay.touch.domain.Employee;
import com.aubay.touch.domain.Group;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @EntityGraph(value = "Employee.detail", type = EntityGraph.EntityGraphType.LOAD)
    List<Employee> findAllByGroupsIn(Set<Group> group);

}
