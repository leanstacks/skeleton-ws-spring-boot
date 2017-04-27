package com.leanstacks.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The ApiDocsConfiguration class provides configuration beans for the Swagger API documentation generator.
 * 
 * @author Matt Warman
 */
@Profile("docs")
@Configuration
@EnableSwagger2
public class ApiDocsConfiguration {

    /**
     * The project version.
     */
    public static final String PROJECT_VERSION = "1.7.3";
    /**
     * The project contact name.
     */
    public static final String PROJECT_CONTACT_NAME = "LeanStacks.com";
    /**
     * The project contact URL.
     */
    public static final String PROJECT_CONTACT_URL = "http://www.leanstacks.com/";

    /**
     * Create a Contact class to be used by Springfox's Swagger API Documentation framework.
     * 
     * @return A Contact instance.
     */
    private Contact contact() {
        return new Contact(PROJECT_CONTACT_NAME, PROJECT_CONTACT_URL, null);
    }

    /**
     * Create an ApiInfo class to be used by Springfox's Swagger API Documentation framework.
     * 
     * @return An ApiInfo instance.
     */
    private ApiInfo apiInfo() {

        // @formatter:off
        final ApiInfo apiInfo = 
            new ApiInfoBuilder()
                .title("Project Skeleton for Spring Boot Web Services")
                .description("The Spring Boot web services starter project provides a foundation "
                        + "to rapidly construct a RESTful web services application.")
                .contact(contact())
                .version(PROJECT_VERSION)
                .build();
        // @formatter:on

        return apiInfo;
    }

    /**
     * Create a Docket class to be used by Springfox's Swagger API Documentation framework. See
     * http://springfox.github.io/springfox/ for more information.
     * 
     * @return A Docket instance.
     */
    @Bean
    public Docket docket() {
        final Predicate<String> paths = PathSelectors.ant("/api/**");

        // @formatter:off
        final Docket docket = 
            new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                    .paths(paths)
                .build();
        // @formatter:on

        return docket;
    }

}
