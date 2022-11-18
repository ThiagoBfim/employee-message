package com.aubay.touch.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aubay.touch.domain.Employee;
import com.aubay.touch.domain.Message;
import com.aubay.touch.domain.MessageDelivery;
import com.aubay.touch.repository.EmployeeRepository;
import com.aubay.touch.repository.MessageRepository;
import com.aubay.touch.service.delivery.IDeliveryService;

@Service
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
            final List<Employee> employees = employeeRepository.findAllByGroups(m.getGroups());
            employees.forEach(u -> u.getEmployeeChannels()
                .stream()
                .filter(c -> m.getDeliveryChannel().contains(c.getChannel()))
                .forEach(c -> deliveryService
                    .forEach(d -> {
                        if (d.getChannel().equals(c.getChannelName())) {
                            var deliveryMessage = d.sendMessage(u.getName(), c.getIdentifier());
                            final MessageDelivery message = new MessageDelivery(m);
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

}
