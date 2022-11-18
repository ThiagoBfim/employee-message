package com.aubay.touch.service;

import java.time.LocalDateTime;
import java.util.List;

import com.aubay.touch.controller.response.MessageResponse;
import com.aubay.touch.service.importer.CSVHelper;
import org.springframework.stereotype.Service;

import com.aubay.touch.domain.DeliveryMessage;
import com.aubay.touch.domain.Employee;
import com.aubay.touch.domain.Message;
import com.aubay.touch.repository.EmployeeRepository;
import com.aubay.touch.repository.MessageRepository;
import com.aubay.touch.service.delivery.IDeliveryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class MessageService {

    private final List<IDeliveryService> deliveryService;
    private final MessageRepository messageRepository;
    private final EmployeeRepository employeeRepository;

    public MessageService(List<IDeliveryService> deliveryService, MessageRepository messageRepository, EmployeeRepository employeeRepository) {
        this.deliveryService = deliveryService;
        this.messageRepository = messageRepository;
        this.employeeRepository = employeeRepository;
    }

    public int sendMessage() {
        final List<Message> messagesToBeDelivered = messageRepository.findAllByDeliveryTimeAfter(LocalDateTime.now());
        messagesToBeDelivered.forEach(m -> {
            //TODO improve to do it 100 per 100
            final List<Employee> employees = employeeRepository.findAllByGroupsIn(m.getGroups());
            employees.forEach(u -> u.getEmployeeChannels()
                    .stream()
                    .filter(c -> m.getDeliveryChannel().contains(c.getChannel()))
                    .forEach(c -> deliveryService
                            .forEach(d -> {
                                if (d.getChannel().equals(c.getChannelName())) {
                                    var deliveryMessage = d.sendMessage(new com.aubay.touch.service.delivery.DeliveryMessage(m.getTitle(), m.getMessage(), u.getName(), c.getIdentifier()));
                                    final DeliveryMessage message = new DeliveryMessage(m);
                                    if (!deliveryMessage.success()) {
                                        message.setError(deliveryMessage.errorMessage());
                                    }
                                    u.addMessage(message);
                                }
                            })));
            employeeRepository.saveAll(employees);
        });
        return messagesToBeDelivered.size();
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> findAll() {
        return messageRepository.findAllMessages();
    }

    public int importMessages(MultipartFile file) {
        try {
            List<Message> messages = CSVHelper.csvToMessages(file.getInputStream());
            messageRepository.saveAll(messages);
            return messages.size();
        } catch (Exception e) {
            throw new RuntimeException("Error importing messages", e);
        }
    }
}
