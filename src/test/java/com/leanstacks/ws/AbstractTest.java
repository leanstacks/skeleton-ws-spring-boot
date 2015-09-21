package com.leanstacks.ws;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.leanstacks.ws.util.RequestContext;

/**
 * The AbstractTest class is the parent of all JUnit test classes. This class
 * configures the test ApplicationContext and test runner environment.
 * 
 * @author Matt Warman
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(
        classes = Application.class)
public abstract class AbstractTest {

    public static final String USERNAME = "unittest";

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void setUp() {

        RequestContext.setUsername(AbstractTest.USERNAME);

    }

}
