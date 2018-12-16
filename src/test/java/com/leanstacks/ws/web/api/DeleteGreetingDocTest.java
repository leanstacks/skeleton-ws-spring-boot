package com.leanstacks.ws.web.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
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
public class DeleteGreetingDocTest extends AbstractDocTest {

    @Override
    public void doBeforeEachTest() {
        // perform test initialization
    }

    @Override
    public void doAfterEachTest() {
        // perform test cleanup
    }

    /**
     * Generate API documentation for DELETE /api/greetings/{id}.
     * 
     * @throws Exception Thrown if documentation generation failure occurs.
     */
    @Test
    public void documentDeleteGreeting() throws Exception {

        // Generate API Documentation
        final MvcResult result = this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/greetings/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcRestDocumentation.document("delete-greeting")).andReturn();

        // Perform a simple, standard JUnit assertion to satisfy PMD rule
        Assert.assertEquals("failure - expected HTTP status 204", 204, result.getResponse().getStatus());

    }

}
