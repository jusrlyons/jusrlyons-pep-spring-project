package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account register(String username, String password) {
        if (getAccountByUsername(username) != null || username.isEmpty() || password.length() < 5) {
            return null;
        }
        return accountRepository.save(new Account(username, password));
    }

    public Account login(String username, String password) {
        Account account = null;
        if (accountRepository.findByUsername(username) != null) {
            account = accountRepository.findByUsername(username);
        }
        if (account == null || !account.getUsername().equals(username) || !account.getPassword().equals(password)) {
            return null;
        }
        return account;
    }

    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account getAccountById(int id) {
        return accountRepository.findById(id);
    }
}
