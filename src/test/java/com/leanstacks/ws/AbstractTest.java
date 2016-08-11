package com.leanstacks.ws;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.leanstacks.ws.util.RequestContext;

/**
 * The AbstractTest class is the parent of all JUnit test classes. This class configures the test ApplicationContext and
 * test runner environment.
 * 
 * @author Matt Warman
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public abstract class AbstractTest {

    /**
     * The username value used in the RequestContext for Unit Tests.
     */
    public static final String USERNAME = "unittest";

    @Before
    public void before() {
        RequestContext.setUsername(AbstractTest.USERNAME);
        doBeforeEachTest();
    }

    /**
     * Perform initialization tasks before the execution of each test method.
     */
    public abstract void doBeforeEachTest();

    @After
    public void after() {
        doAfterEachTest();
    }

    /**
     * Perform clean up tasks after the execution of each test method.
     */
    public abstract void doAfterEachTest();

}
