
package com.aubay.touch.controller;

import com.aubay.touch.controller.response.MessageResponse;
import com.aubay.touch.service.MessageService;
import com.aubay.touch.service.importer.CSVHelper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/messages", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "Bearer Authentication")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<MessageResponse>> getMessages() {
        return ResponseEntity.ok(messageService.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> forceSendMessage() {
        messageService.sendMessage();
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(
            path = "/upload",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> uploadFile(@RequestParam("file") MultipartFile file) {

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                int qtdMessages = messageService.importMessages(file);
                return ResponseEntity.status(HttpStatus.OK).body(qtdMessages);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(0);
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
    }


}
