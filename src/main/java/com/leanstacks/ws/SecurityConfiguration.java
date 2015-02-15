package com.leanstacks.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.leanstacks.ws.security.AccountAuthenticationProvider;

/**
 * The SecurityConfiguration class provides a centralized location for
 * application security configuration. This class bootstraps the Spring Security
 * components during application startup.
 * 
 * @author Matt Warman
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountAuthenticationProvider accountAuthenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.authenticationProvider(accountAuthenticationProvider);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
          .csrf().disable()
          .authorizeRequests()
            .antMatchers("/api/**").hasRole("USER")
            .antMatchers("/actuators/**").hasRole("SYSADMIN")
            .anyRequest().authenticated()
          .and()
          .httpBasic()
          .and()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

}
