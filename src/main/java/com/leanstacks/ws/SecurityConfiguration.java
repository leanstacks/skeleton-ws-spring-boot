package com.leanstacks.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
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
public class SecurityConfiguration {

    /**
     * The AccountAuthenticationProvider is a custom Spring Security
     * AuthenticationProvider.
     */
    @Autowired
    private transient AccountAuthenticationProvider accountAuthenticationProvider;

    /**
     * Supplies a PasswordEncoder instance to the Spring ApplicationContext. The
     * PasswordEncoder is used by the AuthenticationProvider to perform one-way
     * hash operations on passwords for credential comparison.
     * 
     * @return A PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method builds the AuthenticationProvider used by the system to
     * process authentication requests.
     * 
     * @param auth An AuthenticationManagerBuilder instance used to construct
     *        the AuthenticationProvider.
     * @throws Exception Thrown if a problem occurs constructing the
     *         AuthenticationProvider.
     */
    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth)
            throws Exception {

        auth.authenticationProvider(accountAuthenticationProvider);

    }

    /**
     * This inner class configures the WebSecurityConfigurerAdapter instance for
     * the web service API context paths.
     * 
     * @author Matt Warman
     */
    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurerAdapter
            extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(final HttpSecurity http) throws Exception {

            // @formatter:off
            
            http
            .csrf().disable()
            .antMatcher("/api/**")
              .authorizeRequests()
                .anyRequest().hasRole("USER")
            .and()
            .httpBasic()
            .and()
            .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            
            // @formatter:on

        }

    }

    /**
     * This inner class configures the WebSecurityConfigurerAdapter instance for
     * the Spring Actuator web service context paths.
     * 
     * @author Matt Warman
     */
    @Configuration
    @Order(2)
    public static class ActuatorWebSecurityConfigurerAdapter
            extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(final HttpSecurity http) throws Exception {

            // @formatter:off
            
            http
            .csrf().disable()
            .antMatcher("/actuators/**")
              .authorizeRequests()
                .anyRequest().hasRole("SYSADMIN")
            .and()
            .httpBasic()
            .and()
            .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
            
            // @formatter:on

        }

    }

    /**
     * This inner class configures the WebSecurityConfigurerAdapter instance for
     * any remaining context paths not handled by other adapters.
     * 
     * @author Matt Warman
     */
    @Profile("docs")
    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter
            extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(final HttpSecurity http) throws Exception {

            // @formatter:off
            
            http
              .csrf().disable()
              .authorizeRequests()
                .anyRequest().authenticated()
              .and()
              .formLogin();
            
            // @formatter:on

        }

    }

}
