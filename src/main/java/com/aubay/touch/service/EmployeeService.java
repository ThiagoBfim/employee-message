package com.aubay.touch.service;

import com.aubay.touch.controller.response.EmployeeResponse;
import com.aubay.touch.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeResponse> getEmployees() {
        return employeeRepository.findAll().stream().map(e -> new EmployeeResponse(e.getId(), e.getName(),
                        e.getEmployeeChannelsSeparatedByComma(),
                        e.getEmployeeGroupsSeparatedByComma()))
                .toList();
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
