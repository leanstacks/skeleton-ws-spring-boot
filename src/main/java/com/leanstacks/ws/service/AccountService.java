package com.leanstacks.ws.service;

import com.leanstacks.ws.model.Account;

public interface AccountService {

    Account findByUsername(String username);

}
