package com.leanstacks.ws.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.leanstacks.ws.AbstractTest;
import com.leanstacks.ws.BasicTransactionalTest;
import com.leanstacks.ws.model.Greeting;

/**
 * Unit test methods for the GreetingService and GreetingServiceBean.
 * 
 * @author Matt Warman
 */
@RunWith(SpringRunner.class)
@BasicTransactionalTest
public class GreetingServiceTest extends AbstractTest {

    /**
     * Constant 'test'.
     */
    private static final String VALUE_TEXT = "test";

    /**
     * The GreetingService business service.
     */
    @Autowired
    private transient GreetingService greetingService;

    @Override
    public void doBeforeEachTest() {
        greetingService.evictCache();
    }

    @Override
    public void doAfterEachTest() {
        // perform test clean up
    }

    /**
     * Test fetch a collection of Greetings.
     */
    @Test
    public void testGetGreetings() {

        final Collection<Greeting> greetings = greetingService.findAll();

        Assert.assertNotNull("failure - expected not null", greetings);
        Assert.assertEquals("failure - expected 2 greetings", 2, greetings.size());

    }

    /**
     * Test fetch a single Greeting.
     */
    @Test
    public void testGetGreeting() {

        final Long id = Long.valueOf(1);

        final Greeting greeting = greetingService.findOne(id);

        Assert.assertNotNull("failure - expected not null", greeting);
        Assert.assertEquals("failure - expected greeting.id match", id, greeting.getId());

    }

    /**
     * Test fetch a single greeting with invalid identifier.
     */
    @Test
    public void testGetGreetingNotFound() {

        final Long id = Long.MAX_VALUE;

        final Greeting greeting = greetingService.findOne(id);

        Assert.assertNull("failure - expected null", greeting);

    }

    /**
     * Test create a Greeting.
     */
    @Test
    public void testCreateGreeting() {

        final Greeting greeting = new Greeting();
        greeting.setText(VALUE_TEXT);

        final Greeting createdGreeting = greetingService.create(greeting);

        Assert.assertNotNull("failure - expected greeting not null", createdGreeting);
        Assert.assertNotNull("failure - expected greeting.id not null", createdGreeting.getId());
        Assert.assertEquals("failure - expected greeting.text match", VALUE_TEXT, createdGreeting.getText());

        final Collection<Greeting> greetings = greetingService.findAll();

        Assert.assertEquals("failure - expected 3 greetings", 3, greetings.size());

    }

    /**
     * Test create a Greeting with invalid data.
     */
    @Test
    public void testCreateGreetingWithId() {

        final Greeting greeting = new Greeting();
        greeting.setId(Long.MAX_VALUE);
        greeting.setText(VALUE_TEXT);

        try {
            greetingService.create(greeting);
            Assert.fail("failure - expected exception");
        } catch (EntityExistsException eee) {
            Assert.assertNotNull("failure - expected exception not null", eee);
        }

    }

    /**
     * Test update a Greeting.
     */
    @Test
    public void testUpdateGreeting() {

        final Long id = Long.valueOf(1);

        final Greeting greeting = greetingService.findOne(id);

        Assert.assertNotNull("failure - expected greeting not null", greeting);

        final String updatedText = greeting.getText() + " test";
        greeting.setText(updatedText);
        final Greeting updatedGreeting = greetingService.update(greeting);

        Assert.assertNotNull("failure - expected updated greeting not null", updatedGreeting);
        Assert.assertEquals("failure - expected updated greeting id unchanged", id, updatedGreeting.getId());
        Assert.assertEquals("failure - expected updated greeting text match", updatedText, updatedGreeting.getText());

    }

    /**
     * Test update a Greeting which does not exist.
     */
    @Test
    public void testUpdateGreetingNotFound() {

        final Greeting greeting = new Greeting();
        greeting.setId(Long.MAX_VALUE);
        greeting.setText("test");

        try {
            greetingService.update(greeting);
            Assert.fail("failure - expected exception");
        } catch (NoResultException nre) {
            Assert.assertNotNull("failure - expected exception not null", nre);
        }

    }

    /**
     * Test delete a Greeting.
     */
    @Test
    public void testDeleteGreeting() {

        final Long id = Long.valueOf(1);

        final Greeting greeting = greetingService.findOne(id);

        Assert.assertNotNull("failure - expected greeting not null", greeting);

        greetingService.delete(id);

        final Collection<Greeting> greetings = greetingService.findAll();

        Assert.assertEquals("failure - expected 1 greeting", 1, greetings.size());

        final Greeting deletedGreeting = greetingService.findOne(id);

        Assert.assertNull("failure - expected greeting to be deleted", deletedGreeting);

    }

}
