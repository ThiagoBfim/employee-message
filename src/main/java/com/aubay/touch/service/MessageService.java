package com.aubay.touch.service;

import java.time.LocalDateTime;
import java.util.List;

import com.aubay.touch.controller.response.MessageResponse;
import com.aubay.touch.domain.*;
import com.aubay.touch.repository.ChannelRepository;
import com.aubay.touch.repository.GroupRepository;
import com.aubay.touch.service.importer.CSVHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aubay.touch.repository.EmployeeRepository;
import com.aubay.touch.repository.MessageRepository;
import com.aubay.touch.service.delivery.IDeliveryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class.getName());

    private final List<IDeliveryService> deliveryService;
    private final MessageRepository messageRepository;
    private final GroupRepository groupRepository;
    private final ChannelRepository channelRepository;
    private final EmployeeRepository employeeRepository;

    public MessageService(List<IDeliveryService> deliveryService, MessageRepository messageRepository,
                          GroupRepository groupRepository,
                          ChannelRepository channelRepository,
                          EmployeeRepository employeeRepository) {
        this.deliveryService = deliveryService;
        this.messageRepository = messageRepository;
        this.groupRepository = groupRepository;
        this.channelRepository = channelRepository;
        this.employeeRepository = employeeRepository;
    }

    public int sendMessage() {
        final List<Message> messagesToBeDelivered = messageRepository.findAllByDeliveryTimeBefore(LocalDateTime.now());
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

    @Transactional
    public int importMessages(MultipartFile file) {
        try {
            List<Message> messages = CSVHelper.csvToMessages(file.getInputStream());
            List<Channel> channels = channelRepository.findAll();
            List<Group> groups = groupRepository.findAll();
            messages.forEach(m -> {
                m.getGroups().forEach(g -> groups.stream().filter(g2 -> g2.getName().equals(g.getName()))
                        .findFirst()
                        .ifPresentOrElse(group -> g.setId(group.getId()), () -> LOGGER.error("Not found group")));

                m.getDeliveryChannel().forEach(c -> channels.stream().filter(c2 -> c2.getName().equals(c.getName()))
                        .findFirst()
                        .ifPresentOrElse(group -> c.setId(group.getId()), () -> LOGGER.error("Not found channel")));
            });
            messageRepository.saveAll(messages);
            return messages.size();
        } catch (
                Exception e) {
            throw new RuntimeException("Error importing messages", e);
        }
    }
}
