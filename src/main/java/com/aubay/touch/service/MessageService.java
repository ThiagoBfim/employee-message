package com.aubay.touch.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.aubay.touch.controller.response.MessageResponse;
import com.aubay.touch.domain.*;
import com.aubay.touch.service.delivery.MessageCtx;
import com.aubay.touch.service.importer.CSVHelper;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
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
    private final EmployeeRepository employeeRepository;

    public MessageService(List<IDeliveryService> deliveryService, MessageRepository messageRepository,
                          EmployeeRepository employeeRepository) {
        this.deliveryService = deliveryService;
        this.messageRepository = messageRepository;
        this.employeeRepository = employeeRepository;
    }

    public int sendMessage() {
        final List<Message> messagesToBeDelivered = messageRepository.findAllByDeliveryTimeBeforeAndStatusEquals(LocalDateTime.now(), MessageStatus.PENDING);
        AtomicReference<Integer> messagesDelivered = new AtomicReference<>(0);
        messagesToBeDelivered.forEach(message -> {
            //TODO improve to do it 100 per 100
            final List<Employee> employees = employeeRepository.findAllByGroupsIn(message.getGroups());
            AtomicBoolean containsError = new AtomicBoolean(false);
            employees.forEach(employee -> employee.getEmployeeChannels()
                    .stream()
                    .filter(c -> message.getDeliveryChannel().contains(c.getChannel()))
                    .forEach(channel -> {
                        deliveryService
                                .forEach(deliveryService -> {
                                    if (deliveryService.getChannel().equalsIgnoreCase(channel.getChannelName())) {

                                        Optional<DeliveryMessage> alreadySent = employee.getMessagesDelivered().stream().filter(deliveryMessage -> deliveryMessage.getMessage().equals(message))
                                                .filter(d -> d.getChannel().getName().equals(channel.getChannel().getName()))
                                                .findFirst();
                                        if (alreadySent.isPresent()) {
                                            if (BooleanUtils.isNotTrue(alreadySent.get().getSuccess())) {
                                                DeliveryMessage deliveryMessage = sendMessage(message, employee, channel, deliveryService, alreadySent.get());
                                                employee.addMessage(deliveryMessage);
                                                if (!deliveryMessage.getSuccess()) {
                                                    containsError.set(true);
                                                }
                                                messagesDelivered.updateAndGet(v -> v + employees.size());
                                            }
                                        } else {
                                            DeliveryMessage deliveryMessage = sendMessage(message, employee, channel, deliveryService, null);
                                            employee.addMessage(deliveryMessage);
                                            if (!deliveryMessage.getSuccess()) {
                                                containsError.set(true);
                                            }
                                            messagesDelivered.updateAndGet(v -> v + employees.size());
                                        }
                                    }
                                });
                    }));
            employeeRepository.saveAll(employees);

            if (containsError.get()) {
                message.setStatus(MessageStatus.PENDING);
            } else {
                message.setStatus(MessageStatus.SENT);
            }
            messageRepository.save(message);
        });
        return messagesDelivered.get();
    }

    private static DeliveryMessage sendMessage(Message m, Employee employee, EmployeeChannel channel, IDeliveryService deliveryService, @Nullable DeliveryMessage deliveryMessage) {
        var messageCtx = new MessageCtx(m.getTitle(), m.getMessage(), employee.getName(), channel.getIdentifier());
        var messageResult = deliveryService.sendMessage(messageCtx);
        var message = Optional.ofNullable(deliveryMessage).orElse(new DeliveryMessage(m));
        if (!messageResult.success()) {
            message.setError(messageResult.errorMessage());
        } else {
            message.setSuccess(true);
            message.setDeliveryTime(LocalDateTime.now());
        }
        message.setChannel(channel.getChannel());
        return message;
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> findAll() {
        return messageRepository.findAllMessages();
    }

    @Transactional
    public int importMessages(MultipartFile file) {
        try {
            List<Message> messages = CSVHelper.csvToMessages(file.getInputStream());
            messageRepository.saveAll(messages);
            return messages.size();
        } catch (Exception e) {
            LOGGER.error("Error importing messages", e);
            throw new RuntimeException("Error importing messages", e);
        }
    }

}
