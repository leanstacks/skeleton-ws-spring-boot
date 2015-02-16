package com.leanstacks.ws.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A Spring Security AuthenticationProvider which extends
 * <code>AbstractUserDetailsAuthenticationProvider</code>. This classes uses the
 * <code>AccountUserDetailsService</code> to retrieve a UserDetails instance.
 * 
 * A PasswordEncoder compares the supplied authentication credentials to those
 * in the UserDetails.
 * 
 * @author Matt Warman
 */
@Component
public class AccountAuthenticationProvider extends
        AbstractUserDetailsAuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * A Spring Security UserDetailsService implementation based upon the
     * Account entity model.
     */
    @Autowired
    private AccountUserDetailsService userDetailsService;

    /**
     * A PasswordEncoder instance to hash clear test password values.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken token)
            throws AuthenticationException {
        logger.info("> additionalAuthenticationChecks");

        if (token.getCredentials() == null || userDetails.getPassword() == null) {
            logger.info("< additionalAuthenticationChecks");
            throw new BadCredentialsException("Credentials may not be null.");
        }

        if (!passwordEncoder.matches((String) token.getCredentials(),
                userDetails.getPassword())) {
            logger.info("< additionalAuthenticationChecks");
            throw new BadCredentialsException("Invalid credentials.");
        }

        logger.info("< additionalAuthenticationChecks");
    }

    @Override
    protected UserDetails retrieveUser(String username,
            UsernamePasswordAuthenticationToken token)
            throws AuthenticationException {
        logger.info("> retrieveUser");

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        logger.info("< retrieveUser");
        return userDetails;
    }

}
