package com.leanstacks.ws.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leanstacks.ws.model.Account;
import com.leanstacks.ws.repository.AccountRepository;

@Service
public class AccountServiceBean implements AccountService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account findByUsername(String username) {
        logger.info("> findByUsername");

        Account account = accountRepository.findByUsername(username);

        logger.info("< findByUsername");
        return account;
    }

}
