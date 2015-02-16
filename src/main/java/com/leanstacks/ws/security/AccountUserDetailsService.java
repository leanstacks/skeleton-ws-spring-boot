package com.leanstacks.ws.security;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.leanstacks.ws.model.Account;
import com.leanstacks.ws.model.Role;
import com.leanstacks.ws.service.AccountService;

/**
 * A Spring Security UserDetailsService implementation which creates UserDetails
 * objects from the Account and Role entities.
 * 
 * @author Matt Warman
 */
@Service
public class AccountUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The AccountService business service.
     */
    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        logger.info("> loadUserByUsername {}", username);

        Account account = accountService.findByUsername(username);
        if (account == null) {
            logger.info("< loadUserByUsername {}", username);
            return null;
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (Role role : account.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
        }

        User userDetails = new User(account.getUsername(),
                account.getPassword(), account.isEnabled(),
                !account.isExpired(), !account.isCredentialsexpired(),
                !account.isLocked(), grantedAuthorities);

        logger.info("< loadUserByUsername {}", username);
        return userDetails;
    }

}
