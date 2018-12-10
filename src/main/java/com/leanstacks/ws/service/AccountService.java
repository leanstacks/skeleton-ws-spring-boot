package com.leanstacks.ws.service;

import java.util.Optional;

import com.leanstacks.ws.model.Account;

/**
 * <p>
 * The AccountService interface defines all public business behaviors for operations on the Account entity model and
 * some related entities such as Role.
 * </p>
 * <p>
 * This interface should be injected into AccountService clients, not the implementation bean.
 * </p>
 * 
 * @author Matt Warman
 */
public interface AccountService {

    /**
     * Find an Account by the username attribute value.
     * 
     * @param username A String username to query the repository.
     * @return An Optional wrapped Account.
     */
    Optional<Account> findByUsername(String username);

}
