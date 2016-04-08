package com.leanstacks.ws.web.api;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.leanstacks.ws.AbstractControllerTest;
import com.leanstacks.ws.model.Greeting;
import com.leanstacks.ws.service.EmailService;
import com.leanstacks.ws.service.GreetingService;

/**
 * <p>
 * Unit tests for the GreetingController using Mockito mocks and spies.
 * </p>
 * <p>
 * These tests utilize the Mockito framework objects to simulate interaction with back-end components. The controller
 * methods are invoked directly bypassing the Spring MVC mappings. Back-end components are mocked and injected into the
 * controller. Mockito spies and verifications are performed ensuring controller behaviors.
 * </p>
 * 
 * @author Matt Warman
 */
@Transactional
public class GreetingControllerMocksTest extends AbstractControllerTest {

    private static final String RESOURCE_URI = "/api/greetings";
    private static final String RESOURCE_ITEM_URI = "/api/greetings/{id}";

    /**
     * A mocked GreetingService.
     */
    @Mock
    private transient GreetingService greetingService;

    /**
     * A mocked EmailService.
     */
    @Mock
    private transient EmailService emailService;

    /**
     * A GreetingController instance with <code>@Mock</code> components injected into it.
     */
    @InjectMocks
    private transient GreetingController greetingController;

    @Override
    public void doBeforeEachTest() {
        // Initialize Mockito annotated components
        MockitoAnnotations.initMocks(this);
        // Prepare the Spring MVC Mock components for standalone testing
        setUp(greetingController);
    }

    @Override
    public void doAfterEachTest() {
        // perform test clean up
    }

    @Test
    public void testGetGreetings() throws Exception {

        // Create some test data
        final Collection<Greeting> list = getEntityListStubData();

        // Stub the GreetingService.findAll method return value
        when(greetingService.findAll()).thenReturn(list);

        // Perform the behavior being tested
        final MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(RESOURCE_URI).accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        // Verify the GreetingService.findAll method was invoked once
        verify(greetingService, times(1)).findAll();

        // Perform standard JUnit assertions on the response
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !Strings.isNullOrEmpty(content));

    }

    @Test
    public void testGetGreeting() throws Exception {

        // Create some test data
        final Long id = new Long(1);
        final Greeting entity = getEntityStubData();

        // Stub the GreetingService.findOne method return value
        when(greetingService.findOne(id)).thenReturn(entity);

        // Perform the behavior being tested
        final MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(RESOURCE_ITEM_URI, id).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        // Verify the GreetingService.findOne method was invoked once
        verify(greetingService, times(1)).findOne(id);

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !Strings.isNullOrEmpty(content));
    }

    @Test
    public void testGetGreetingNotFound() throws Exception {

        // Create some test data
        final Long id = Long.MAX_VALUE;

        // Stub the GreetingService.findOne method return value
        when(greetingService.findOne(id)).thenReturn(null);

        // Perform the behavior being tested
        final MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(RESOURCE_ITEM_URI, id).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        // Verify the GreetingService.findOne method was invoked once
        verify(greetingService, times(1)).findOne(id);

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty", Strings.isNullOrEmpty(content));

    }

    @Test
    public void testCreateGreeting() throws Exception {

        // Create some test data
        final Greeting entity = getEntityStubData();

        // Stub the GreetingService.create method return value
        when(greetingService.create(any(Greeting.class))).thenReturn(entity);

        // Perform the behavior being tested
        final String inputJson = super.mapToJson(entity);

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        // Verify the GreetingService.create method was invoked once
        verify(greetingService, times(1)).create(any(Greeting.class));

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !Strings.isNullOrEmpty(content));

        final Greeting createdEntity = super.mapFromJson(content, Greeting.class);

        Assert.assertNotNull("failure - expected entity not null", createdEntity);
        Assert.assertNotNull("failure - expected id attribute not null", createdEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", entity.getText(), createdEntity.getText());
    }

    @Test
    public void testUpdateGreeting() throws Exception {

        // Create some test data
        final Greeting entity = getEntityStubData();
        entity.setText(entity.getText() + " test");
        final Long id = new Long(1);

        // Stub the GreetingService.update method return value
        when(greetingService.update(any(Greeting.class))).thenReturn(entity);

        // Perform the behavior being tested
        final String inputJson = super.mapToJson(entity);

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.put(RESOURCE_ITEM_URI, id)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        // Verify the GreetingService.update method was invoked once
        verify(greetingService, times(1)).update(any(Greeting.class));

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !Strings.isNullOrEmpty(content));

        final Greeting updatedEntity = super.mapFromJson(content, Greeting.class);

        Assert.assertNotNull("failure - expected entity not null", updatedEntity);
        Assert.assertEquals("failure - expected id attribute unchanged", entity.getId(), updatedEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", entity.getText(), updatedEntity.getText());

    }

    @Test
    public void testDeleteGreeting() throws Exception {

        // Create some test data
        final Long id = new Long(1);

        // Perform the behavior being tested
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(RESOURCE_ITEM_URI, id)).andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        // Verify the GreetingService.delete method was invoked once
        verify(greetingService, times(1)).delete(id);

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 204", 204, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty", Strings.isNullOrEmpty(content));

    }

    private Collection<Greeting> getEntityListStubData() {
        final Collection<Greeting> list = new ArrayList<Greeting>();
        list.add(getEntityStubData());
        return list;
    }

    private Greeting getEntityStubData() {
        final Greeting entity = new Greeting();
        entity.setId(1L);
        entity.setText("hello");
        return entity;
    }

}
