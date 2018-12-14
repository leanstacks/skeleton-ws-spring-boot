package com.leanstacks.ws.web.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
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
public class GetGreetingDocTest extends AbstractDocTest {

    @Override
    public void doBeforeEachTest() {
        // perform test initialization
    }

    @Override
    public void doAfterEachTest() {
        // perform test cleanup
    }

    /**
     * Generate API documentation for GET /api/greetings/{id}.
     * 
     * @throws Exception Thrown if documentation generation failure occurs.
     */
    @Test
    public void documentGetGreeting() throws Exception {

        // Generate API Documentation
        final MvcResult result = this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .get("/api/greetings/{id}", "0").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("get-greeting",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("id").description("The greeting identifier.")),
                        PayloadDocumentation.relaxedResponseFields(
                                PayloadDocumentation.fieldWithPath("id")
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
        Assert.assertEquals("failure - expected HTTP status 200", 200, result.getResponse().getStatus());

    }

}
