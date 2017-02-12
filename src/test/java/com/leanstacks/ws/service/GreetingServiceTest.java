package com.leanstacks.ws.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.leanstacks.ws.AbstractTest;
import com.leanstacks.ws.model.Greeting;

/**
 * Unit test methods for the GreetingService and GreetingServiceBean.
 * 
 * @author Matt Warman
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GreetingServiceTest extends AbstractTest {

    private static final String VALUE_TEXT = "test";

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

    @Test
    public void testGetGreetings() {

        final Collection<Greeting> greetings = greetingService.findAll();

        Assert.assertNotNull("failure - expected not null", greetings);
        Assert.assertEquals("failure - expected 2 greetings", 2, greetings.size());

    }

    @Test
    public void testGetGreeting() {

        final Long id = new Long(1);

        final Greeting greeting = greetingService.findOne(id);

        Assert.assertNotNull("failure - expected not null", greeting);
        Assert.assertEquals("failure - expected greeting.id match", id, greeting.getId());

    }

    @Test
    public void testGetGreetingNotFound() {

        final Long id = Long.MAX_VALUE;

        final Greeting greeting = greetingService.findOne(id);

        Assert.assertNull("failure - expected null", greeting);

    }

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

    @Test
    public void testCreateGreetingWithId() {

        Exception exception = null;

        final Greeting greeting = new Greeting();
        greeting.setId(Long.MAX_VALUE);
        greeting.setText(VALUE_TEXT);

        try {
            greetingService.create(greeting);
        } catch (EntityExistsException eee) {
            exception = eee;
        }

        Assert.assertNotNull("failure - expected exception", exception);
        Assert.assertTrue("failure - expected EntityExistsException", exception instanceof EntityExistsException);

    }

    @Test
    public void testUpdateGreeting() {

        final Long id = new Long(1);

        final Greeting greeting = greetingService.findOne(id);

        Assert.assertNotNull("failure - expected greeting not null", greeting);

        final String updatedText = greeting.getText() + " test";
        greeting.setText(updatedText);
        final Greeting updatedGreeting = greetingService.update(greeting);

        Assert.assertNotNull("failure - expected updated greeting not null", updatedGreeting);
        Assert.assertEquals("failure - expected updated greeting id unchanged", id, updatedGreeting.getId());
        Assert.assertEquals("failure - expected updated greeting text match", updatedText, updatedGreeting.getText());

    }

    @Test
    public void testUpdateGreetingNotFound() {

        Exception exception = null;

        final Greeting greeting = new Greeting();
        greeting.setId(Long.MAX_VALUE);
        greeting.setText("test");

        try {
            greetingService.update(greeting);
        } catch (NoResultException nre) {
            exception = nre;
        }

        Assert.assertNotNull("failure - expected exception", exception);
        Assert.assertTrue("failure - expected NoResultException", exception instanceof NoResultException);

    }

    @Test
    public void testDeleteGreeting() {

        final Long id = new Long(1);

        final Greeting greeting = greetingService.findOne(id);

        Assert.assertNotNull("failure - expected greeting not null", greeting);

        greetingService.delete(id);

        final Collection<Greeting> greetings = greetingService.findAll();

        Assert.assertEquals("failure - expected 1 greeting", 1, greetings.size());

        final Greeting deletedGreeting = greetingService.findOne(id);

        Assert.assertNull("failure - expected greeting to be deleted", deletedGreeting);

    }

}
