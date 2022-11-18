package com.aubay.touch.service;

import com.aubay.touch.controller.response.EmployeeResponse;
import com.aubay.touch.domain.Employee;
import com.aubay.touch.domain.Message;
import com.aubay.touch.repository.EmployeeRepository;
import com.aubay.touch.service.importer.CSVHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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

    public int importEmployees(MultipartFile file) {
        try {
            List<Employee> employees = CSVHelper.csvToEmployees(file.getInputStream());
            employeeRepository.saveAll(employees);
            return employees.size();
        } catch (Exception e) {
            throw new RuntimeException("Error importing employees", e);
        }
    }
}
