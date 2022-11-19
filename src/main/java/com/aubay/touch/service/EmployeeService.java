package com.aubay.touch.service;

import com.aubay.touch.controller.response.EmployeeResponse;
import com.aubay.touch.domain.Channel;
import com.aubay.touch.domain.Employee;
import com.aubay.touch.domain.Group;
import com.aubay.touch.repository.ChannelRepository;
import com.aubay.touch.repository.EmployeeRepository;
import com.aubay.touch.repository.GroupRepository;
import com.aubay.touch.service.importer.CSVHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class.getName());

    private final EmployeeRepository employeeRepository;

    private final GroupRepository groupRepository;
    private final ChannelRepository channelRepository;

    public EmployeeService(EmployeeRepository employeeRepository, GroupRepository groupRepository, ChannelRepository channelRepository) {
        this.employeeRepository = employeeRepository;
        this.groupRepository = groupRepository;
        this.channelRepository = channelRepository;
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
            List<Channel> channels = channelRepository.findAll();
            List<Group> groups = groupRepository.findAll();

            employees.forEach(m -> {
                m.getGroups().forEach(g -> groups.stream().filter(g2 -> g2.getName().equals(g.getName()))
                        .findFirst()
                        .ifPresentOrElse(group -> g.setId(group.getId()), () -> LOGGER.error("Not found group")));

                m.getEmployeeChannels().forEach(c -> channels.stream().filter(c2 -> c2.getName().equals(c.getChannel().getName()))
                        .findFirst()
                        .ifPresentOrElse(group -> c.setId(group.getId()), () -> LOGGER.error("Not found channel")));
            });

            employeeRepository.saveAll(employees);
            return employees.size();
        } catch (Exception e) {
            throw new RuntimeException("Error importing employees", e);
        }
    }
}
