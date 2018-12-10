package com.leanstacks.ws.security;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
 * A Spring Security UserDetailsService implementation which creates UserDetails objects from the Account and Role
 * entities.
 * 
 * @author Matt Warman
 */
@Service
public class AccountUserDetailsService implements UserDetailsService {

    /**
     * The Logger for this Class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AccountUserDetailsService.class);

    /**
     * The AccountService business service.
     */
    @Autowired
    private transient AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        logger.info("> loadUserByUsername {}", username);

        final Optional<Account> accountOptional = accountService.findByUsername(username);
        final Account account = accountOptional
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials."));

        final Set<Role> roles = account.getRoles();
        if (roles == null || roles.isEmpty()) {
            // No Roles assigned to Account...
            throw new UsernameNotFoundException("Invalid credentials.");
        }

        final Set<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getCode()))
                .collect(Collectors.toSet());

        final User userDetails = new User(account.getUsername(), account.getPassword(), account.isEnabled(),
                !account.isExpired(), !account.isCredentialsexpired(), !account.isLocked(), authorities);

        logger.info("< loadUserByUsername {}", username);
        return userDetails;
    }

}
