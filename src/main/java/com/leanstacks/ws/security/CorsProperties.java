package com.leanstacks.ws.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * A container for CORS configuration values.
 * 
 * @author Matt Warman
 *
 */
@ConfigurationProperties("leanstacks.cors")
public class CorsProperties {

    /**
     * The path at which the CorsFilter is registered. The CorsFilter will handle all requests matching this path.
     */
    private String filterRegistrationPath = "/**";

    /**
     * The value of the Access-Control-Allow-Credentials header.
     */
    private Boolean allowCredentials = false;

    /**
     * The value of the Access-Control-Allow-Headers header.
     */
    private List<String> allowedHeaders = Arrays.asList("accept", "content-type");

    /**
     * The value of the Access-Control-Allow-Methods header.
     */
    private List<String> allowedMethods = Arrays.asList("GET");

    /**
     * The value of the Access-Control-Allow-Origin header.
     */
    private List<String> allowedOrigins = Arrays.asList("*");

    /**
     * The value of the Access-Control-Expose-Headers header.
     */
    private List<String> exposedHeaders;

    /**
     * The value of the Access-Control-Max-Age header.
     */
    private Long maxAgeSeconds = 1800L;

    /**
     * Returns the filter registration path.
     * 
     * @return A String.
     */
    public String getFilterRegistrationPath() {
        return filterRegistrationPath;
    }

    /**
     * Sets the filter registration path.
     * 
     * @param filterRegistrationPath A String.
     */
    public void setFilterRegistrationPath(final String filterRegistrationPath) {
        this.filterRegistrationPath = filterRegistrationPath;
    }

    /**
     * Returns the value of the Access-Control-Allow-Credentials header.
     * 
     * @return A Boolean.
     */
    public Boolean getAllowCredentials() {
        return allowCredentials;
    }

    /**
     * Sets the value of the Access-Control-Allow-Credentials header.
     * 
     * @param allowCredentials A Boolean.
     */
    public void setAllowCredentials(final Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    /**
     * Returns the value of the Access-Control-Allow-Headers header.
     * 
     * @return A List of Strings.
     */
    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    /**
     * Sets the value of the Access-Control-Allow-Headers header.
     * 
     * @param allowedHeaders A List of Strings.
     */
    public void setAllowedHeaders(final List<String> allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    /**
     * Returns the value of the Access-Control-Allow-Methods header.
     * 
     * @return A List of Strings.
     */
    public List<String> getAllowedMethods() {
        return allowedMethods;
    }

    /**
     * Sets the value of the Access-Control-Allow-Methods header.
     * 
     * @param allowedMethods A List of Strings.
     */
    public void setAllowedMethods(final List<String> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    /**
     * Returns the value of the Access-Control-Allow-Origin header.
     * 
     * @return A List of Strings.
     */
    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    /**
     * Sets the value of the Access-Control-Allow-Origin header.
     * 
     * @param allowedOrigins A List of Strings.
     */
    public void setAllowedOrigins(final List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    /**
     * Returns the value of the Access-Control-Expose-Headers header.
     * 
     * @return A List of Strings.
     */
    public List<String> getExposedHeaders() {
        return exposedHeaders;
    }

    /**
     * Sets the value of the Access-Control-Expose-Headers header.
     * 
     * @param exposedHeaders A List of Strings.
     */
    public void setExposedHeaders(final List<String> exposedHeaders) {
        this.exposedHeaders = exposedHeaders;
    }

    /**
     * Returns the value of the Access-Control-Max-Age header in seconds.
     * 
     * @return A Long.
     */
    public Long getMaxAgeSeconds() {
        return maxAgeSeconds;
    }

    /**
     * Sets the value of the Access-Control-Max-Age header in seconds.
     * 
     * @param maxAgeSeconds A Long.
     */
    public void setMaxAgeSeconds(final Long maxAgeSeconds) {
        this.maxAgeSeconds = maxAgeSeconds;
    }

}
