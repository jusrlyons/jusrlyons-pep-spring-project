package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account registeredAccount = accountService.register(account.getUsername(), account.getPassword());
        
        if (registeredAccount == null && accountService.getAccountByUsername(account.getUsername()) != null) {
            return ResponseEntity.status(409).build();
        }
        
        else if (registeredAccount == null) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(registeredAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account existingAccount = accountService.login(account.getUsername(), account.getPassword());
        
        if (existingAccount != null) {
            return ResponseEntity.ok(existingAccount);
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message.getMessageText(), message.getPostedBy(), message.getTimePostedEpoch());
        
        if (createdMessage == null) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessagedById(@PathVariable("message_id") int id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable("message_id") int id) {
        boolean isDeleted = messageService.deleteMessageById(id);
        
        if (isDeleted == true) {
            return ResponseEntity.ok(1);
        }
        else {
            return ResponseEntity.status(200).build();
        }
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable("message_id") int id, @RequestBody Message message) {
        
        if (messageService.updateMessageById(id, message.getMessageText()) == false) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(1);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable("account_id") int id) {
        return ResponseEntity.ok(messageService.getAllMessagesByAccountId(id));
    }
}
