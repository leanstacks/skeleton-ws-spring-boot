package com.leanstacks.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot main application class.
 * 
 * @author Matt Warman
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
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

    @Bean
    public CacheManager cacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager("greetings");

        return cacheManager;
    }

}
