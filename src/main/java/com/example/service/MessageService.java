package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;

import java.util.List;
import java.util.ArrayList;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    AccountService accountService;

    public Message createMessage(String messageText, int postedBy, long timePostedEpoch) {
        if (messageText.isEmpty() || messageText.length() > 255 || accountService.getAccountById(postedBy) == null) {
            return null;
        }
        return messageRepository.save(new Message(postedBy, messageText, timePostedEpoch));
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        messages = messageRepository.findAll();
        return messages;
    }

    public Message getMessageById(int id) {
        return messageRepository.findById(id);
    }

    public boolean deleteMessageById(int id) {
        if (messageRepository.findById(id) != null) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean updateMessageById(int id, String messageText) {
        Message message = messageRepository.findById(id);
        if (message == null || messageText == null || messageText.isEmpty() || messageText.length() > 255) {
            return false;
        }
        else {
            messageRepository.save(new Message(id, message.getPostedBy(), messageText, message.getTimePostedEpoch()));
            return true;
        }
    }

    public List<Message> getAllMessagesByAccountId(int id) {
        List<Message> messages = new ArrayList<>();
        messages = messageRepository.findByPostedBy(id);
        return messages;
    }
}
