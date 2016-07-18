package com.leanstacks.ws.web.api;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.leanstacks.ws.AbstractControllerTest;
import com.leanstacks.ws.model.Greeting;
import com.leanstacks.ws.service.GreetingService;

/**
 * <p>
 * Unit tests for the GreetingController using Spring MVC Mocks.
 * </p>
 * <p>
 * These tests utilize the Spring MVC Mock objects to simulate sending actual HTTP requests to the Controller component.
 * This test ensures that the RequestMappings are configured correctly. Also, these tests ensure that the request and
 * response bodies are serialized as expected.
 * </p>
 * 
 * @author Matt Warman
 */
@Transactional
public class GreetingControllerTest extends AbstractControllerTest {

    private static final String RESOURCE_URI = "/api/greetings";
    private static final String RESOURCE_ITEM_URI = "/api/greetings/{id}";

    /**
     * The GreetingService business service.
     */
    @Autowired
    private transient GreetingService greetingService;

    @Override
    public void doBeforeEachTest() {
        super.setUp();
        greetingService.evictCache();

    }

    @Override
    public void doAfterEachTest() {
        // perform test clean up
    }

    @Test
    public void testGetGreetings() throws Exception {

        final MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(RESOURCE_URI).accept(MediaType.APPLICATION_JSON)).andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !Strings.isNullOrEmpty(content));

    }

    @Test
    public void testGetGreeting() throws Exception {

        final Long id = new Long(1);

        final MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(RESOURCE_ITEM_URI, id).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !Strings.isNullOrEmpty(content));

    }

    @Test
    public void testGetGreetingNotFound() throws Exception {

        final Long id = Long.MAX_VALUE;

        final MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(RESOURCE_ITEM_URI, id).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty", Strings.isNullOrEmpty(content));

    }

    @Test
    public void testCreateGreeting() throws Exception {

        final Greeting greeting = new Greeting();
        greeting.setText("test");
        final String inputJson = super.mapToJson(greeting);

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !Strings.isNullOrEmpty(content));

        final Greeting createdGreeting = super.mapFromJson(content, Greeting.class);

        Assert.assertNotNull("failure - expected greeting not null", createdGreeting);
        Assert.assertNotNull("failure - expected greeting.id not null", createdGreeting.getId());
        Assert.assertEquals("failure - expected greeting.text match", "test", createdGreeting.getText());

    }

    @Test
    public void testUpdateGreeting() throws Exception {

        final Long id = new Long(1);
        final Greeting greeting = greetingService.findOne(id);
        final String updatedText = greeting.getText() + " test";

        greeting.setText(updatedText);

        final String inputJson = super.mapToJson(greeting);

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.put(RESOURCE_ITEM_URI, id)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !Strings.isNullOrEmpty(content));

        final Greeting updatedGreeting = super.mapFromJson(content, Greeting.class);

        Assert.assertNotNull("failure - expected greeting not null", updatedGreeting);
        Assert.assertEquals("failure - expected greeting.id unchanged", greeting.getId(), updatedGreeting.getId());
        Assert.assertEquals("failure - expected updated greeting text match", updatedText, updatedGreeting.getText());

    }

    @Test
    public void testDeleteGreeting() throws Exception {

        final Long id = new Long(1);

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(RESOURCE_ITEM_URI, id)).andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 204", 204, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty", Strings.isNullOrEmpty(content));

        final Greeting deletedGreeting = greetingService.findOne(id);

        Assert.assertNull("failure - expected greeting to be null", deletedGreeting);

    }

}
