package com.leanstacks.ws.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.leanstacks.ws.AbstractTest;
import com.leanstacks.ws.model.Greeting;

/**
 * Unit test methods for the GreetingService and GreetingServiceBean.
 * 
 * @author Matt Warman
 */
@Transactional
public class GreetingServiceTest extends AbstractTest {

    @Autowired
    private GreetingService greetingService;

    @Before
    public void setUp() {
        greetingService.evictCache();
    }

    @Test
    public void testGetGreetings() {

        Collection<Greeting> greetings = greetingService.findAll();

        Assert.assertNotNull("failure - expected not null", greetings);
        Assert.assertEquals("failure - expected 2 greetings", 2,
                greetings.size());

    }

    @Test
    public void testGetGreeting() {

        Long id = new Long(1);

        Greeting greeting = greetingService.findOne(id);

        Assert.assertNotNull("failure - expected not null", greeting);
        Assert.assertEquals("failure - expected greeting.id match", id,
                greeting.getId());

    }

    @Test
    public void testGetGreetingNotFound() {

        Long id = Long.MAX_VALUE;

        Greeting greeting = greetingService.findOne(id);

        Assert.assertNull("failure - expected null", greeting);

    }

    @Test
    public void testCreateGreeting() {

        Greeting greeting = new Greeting();
        greeting.setText("test");

        Greeting createdGreeting = greetingService.create(greeting);

        Assert.assertNotNull("failure - expected greeting not null",
                createdGreeting);
        Assert.assertNotNull("failure - expected greeting.id not null",
                createdGreeting.getId());
        Assert.assertEquals("failure - expected greeting.text match", "test",
                createdGreeting.getText());

        Collection<Greeting> greetings = greetingService.findAll();

        Assert.assertEquals("failure - expected 3 greetings", 3,
                greetings.size());

    }

    @Test
    public void testCreateGreetingWithId() {

        Exception e = null;

        Greeting greeting = new Greeting();
        greeting.setId(Long.MAX_VALUE);
        greeting.setText("test");

        try {
            Greeting createdGreeting = greetingService.create(greeting);
        } catch (EntityExistsException eee) {
            e = eee;
        }

        Assert.assertNotNull("failure - expected exception", e);
        Assert.assertTrue("failure - expected EntityExistsException",
                e instanceof EntityExistsException);

    }

    @Test
    public void testUpdateGreeting() {

        Long id = new Long(1);

        Greeting greeting = greetingService.findOne(id);

        Assert.assertNotNull("failure - expected greeting not null", greeting);

        String updatedText = greeting.getText() + " test";
        greeting.setText(updatedText);
        Greeting updatedGreeting = greetingService.update(greeting);

        Assert.assertNotNull("failure - expected updated greeting not null",
                updatedGreeting);
        Assert.assertEquals("failure - expected updated greeting id unchanged",
                id, updatedGreeting.getId());
        Assert.assertEquals("failure - expected updated greeting text match",
                updatedText, updatedGreeting.getText());

    }

    @Test
    public void testUpdateGreetingNotFound() {

        Exception e = null;

        Greeting greeting = new Greeting();
        greeting.setId(Long.MAX_VALUE);
        greeting.setText("test");

        try {
            Greeting updatedGreeting = greetingService.update(greeting);
        } catch (NoResultException nre) {
            e = nre;
        }

        Assert.assertNotNull("failure - expected exception", e);
        Assert.assertTrue("failure - expected NoResultException",
                e instanceof NoResultException);

    }

    @Test
    public void testDeleteGreeting() {

        Long id = new Long(1);

        Greeting greeting = greetingService.findOne(id);

        Assert.assertNotNull("failure - expected greeting not null", greeting);

        greetingService.delete(id);

        Collection<Greeting> greetings = greetingService.findAll();

        Assert.assertEquals("failure - expected 1 greeting", 1,
                greetings.size());

        Greeting deletedGreeting = greetingService.findOne(id);

        Assert.assertNull("failure - expected greeting to be deleted",
                deletedGreeting);

    }

}
