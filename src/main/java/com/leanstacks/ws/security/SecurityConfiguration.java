package com.leanstacks.ws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * The SecurityConfiguration class provides a centralized location for application security configuration. This class
 * bootstraps the Spring Security components during application startup.
 * 
 * @author Matt Warman
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CorsProperties.class)
public class SecurityConfiguration {

    /**
     * The AccountAuthenticationProvider is a custom Spring Security AuthenticationProvider.
     */
    @Autowired
    private transient AccountAuthenticationProvider accountAuthenticationProvider;

    /**
     * Supplies a PasswordEncoder instance to the Spring ApplicationContext. The PasswordEncoder is used by the
     * AuthenticationProvider to perform one-way hash operations on passwords for credential comparison.
     * 
     * @return A PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method builds the AuthenticationProvider used by the system to process authentication requests.
     * 
     * @param auth An AuthenticationManagerBuilder instance used to construct the AuthenticationProvider.
     * @throws Exception Thrown if a problem occurs constructing the AuthenticationProvider.
     */
    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(accountAuthenticationProvider);

    }

    /**
     * This inner class configures the WebSecurityConfigurerAdapter instance for the web service API context paths.
     * 
     * @author Matt Warman
     */
    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        /**
         * The CORS configuration.
         */
        @Autowired
        private transient CorsProperties corsProperties;

        /**
         * Defines a ConfigurationSource for CORS attributes.
         * 
         * @return A CorsConfigurationSource.
         */
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            final CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());
            configuration.setAllowedMethods(corsProperties.getAllowedMethods());
            configuration.setAllowedHeaders(corsProperties.getAllowedHeaders());
            configuration.setAllowCredentials(corsProperties.getAllowCredentials());
            configuration.setExposedHeaders(corsProperties.getExposedHeaders());
            configuration.setMaxAge(corsProperties.getMaxAgeSeconds());

            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration(corsProperties.getFilterRegistrationPath(), configuration);
            return source;
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {

            // @formatter:off
            
            http
                .cors()
                .and()
                .csrf().disable()
                .requestMatchers().antMatchers("/api/**")
                .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .anyRequest().hasRole("USER")
                .and()
                .httpBasic().authenticationEntryPoint(apiAuthenticationEntryPoint())
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            
            // @formatter:on

        }

        /**
         * Create a RestBasicAuthenticationEntryPoint bean. Overrides the default BasicAuthenticationEntryPoint behavior
         * to support Basic Authentication for REST API interaction.
         * 
         * @return An AuthenticationEntryPoint instance.
         */
        @Bean
        public AuthenticationEntryPoint apiAuthenticationEntryPoint() {
            final RestBasicAuthenticationEntryPoint entryPoint = new RestBasicAuthenticationEntryPoint();
            entryPoint.setRealmName("api realm");
            return entryPoint;
        }

    }

    /**
     * This inner class configures the WebSecurityConfigurerAdapter instance for the Spring Actuator web service context
     * paths.
     * 
     * @author Matt Warman
     */
    @Configuration
    @Order(2)
    public static class ActuatorWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(final HttpSecurity http) throws Exception {

            // @formatter:off
            
            http
                .csrf().disable()
                .requestMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeRequests()
                    // Permit access to health check
                    .requestMatchers(EndpointRequest.to("health")).permitAll()
                    // Require authorization for everthing else
                    .anyRequest().hasRole("SYSADMIN")
                .and()
                .httpBasic().authenticationEntryPoint(actuatorAuthenticationEntryPoint())
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
            
            // @formatter:on

        }

        /**
         * Create a RestBasicAuthenticationEntryPoint bean. Overrides the default BasicAuthenticationEntryPoint behavior
         * to support Basic Authentication for REST API interaction.
         * 
         * @return An AuthenticationEntryPoint instance.
         */
        @Bean
        public AuthenticationEntryPoint actuatorAuthenticationEntryPoint() {
            final RestBasicAuthenticationEntryPoint entryPoint = new RestBasicAuthenticationEntryPoint();
            entryPoint.setRealmName("actuator realm");
            return entryPoint;
        }

    }

}
