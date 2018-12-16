package com.leanstacks.ws.web.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.leanstacks.ws.AbstractDocTest;
import com.leanstacks.ws.RestControllerTest;

/**
 * <p>
 * Generate REST API documentation for the GreetingController.
 * </p>
 * <p>
 * These tests utilize Spring's REST Docs Framework to generate API documentation. There is a separate test class
 * responsible for unit testing functionality.
 * </p>
 * 
 * @author Matt Warman
 */
@RunWith(SpringRunner.class)
@RestControllerTest
public class GetGreetingsDocTest extends AbstractDocTest {

    @Override
    public void doBeforeEachTest() {
        // perform test initialization
    }

    @Override
    public void doAfterEachTest() {
        // perform test cleanup
    }

    /**
     * Generate API documentation for GET /api/greetings.
     * 
     * @throws Exception Thrown if documentation generation failure occurs.
     */
    @Test
    public void documentGetGreetings() throws Exception {

        // Generate API Documentation
        final MvcResult result = this.mockMvc
                .perform(RestDocumentationRequestBuilders.get("/api/greetings").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("get-greetings",
                        PayloadDocumentation.relaxedResponseFields(
                                PayloadDocumentation.fieldWithPath("[].id").description(
                                        "The identifier. Used to reference specific greetings in API requests."),
                                PayloadDocumentation.fieldWithPath("[].referenceId")
                                        .description("The supplementary identifier."),
                                PayloadDocumentation.fieldWithPath("[].text").description("The text."),
                                PayloadDocumentation.fieldWithPath("[].version").description("The entity version."),
                                PayloadDocumentation.fieldWithPath("[].createdBy").description("The entity creator."),
                                PayloadDocumentation.fieldWithPath("[].createdAt")
                                        .description("The creation timestamp."),
                                PayloadDocumentation.fieldWithPath("[].updatedBy").description("The last modifier."),
                                PayloadDocumentation.fieldWithPath("[].updatedAt")
                                        .description("The last modification timestamp."))))
                .andReturn();

        // Perform a simple, standard JUnit assertion to satisfy PMD rule
        Assert.assertEquals("failure - expected HTTP status 200", 200, result.getResponse().getStatus());

    }

}
