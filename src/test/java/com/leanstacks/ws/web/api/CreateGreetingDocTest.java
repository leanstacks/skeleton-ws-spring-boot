package com.leanstacks.ws.web.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
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
public class CreateGreetingDocTest extends AbstractDocTest {

    /**
     * The HTTP request body content.
     */
    private static final String REQUEST_BODY = "{ \"text\": \"Bonjour le monde!\" }";

    @Override
    public void doBeforeEachTest() {
        // perform test initialization
    }

    @Override
    public void doAfterEachTest() {
        // perform test cleanup
    }

    /**
     * Generate API documentation for POST /api/greetings.
     * 
     * @throws Exception Thrown if documentation generation failure occurs.
     */
    @Test
    public void documentCreateGreeting() throws Exception {

        // Generate API Documentation
        final MvcResult result = this.mockMvc
                .perform(RestDocumentationRequestBuilders.post("/api/greetings").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(REQUEST_BODY))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcRestDocumentation.document("create-greeting",
                        PayloadDocumentation.relaxedRequestFields(PayloadDocumentation.fieldWithPath("text")
                                .description("The text.").type(JsonFieldType.STRING)),
                        PayloadDocumentation.relaxedResponseFields(
                                PayloadDocumentation
                                        .fieldWithPath("id")
                                        .description(
                                                "The identifier. Used to reference specific greetings in API requests.")
                                        .type(JsonFieldType.NUMBER),
                                PayloadDocumentation.fieldWithPath("referenceId")
                                        .description("The supplementary identifier.").type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("text").description("The text.")
                                        .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("version").description("The entity version.")
                                        .type(JsonFieldType.NUMBER),
                                PayloadDocumentation.fieldWithPath("createdBy").description("The entity creator.")
                                        .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("createdAt").description("The creation timestamp.")
                                        .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("updatedBy").description("The last modifier.")
                                        .type(JsonFieldType.STRING).optional(),
                                PayloadDocumentation.fieldWithPath("updatedAt")
                                        .description("The last modification timestamp.").type(JsonFieldType.STRING)
                                        .optional())))
                .andReturn();

        // Perform a simple, standard JUnit assertion to satisfy PMD rule
        Assert.assertEquals("failure - expected HTTP status 201", 201, result.getResponse().getStatus());

    }

}
