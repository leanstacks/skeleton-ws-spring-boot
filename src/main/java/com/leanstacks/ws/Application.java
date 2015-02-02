package com.leanstacks.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot main application class.
 */
@SpringBootApplication
public class Application {

    /**
     * Entry point for the application.
     * @param args Command line arguments.
     * @throws Exception Thrown when an unexpected exception is thrown from the
     *         application.
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
