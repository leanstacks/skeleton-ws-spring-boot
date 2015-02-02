package com.leanstacks.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Main Application Class.  Entry point for the application.
 */
@SpringBootApplication
public class Application
{
    public static void main( String[] args ) throws Exception
    {
        SpringApplication.run(Application.class, args);
    }
}
