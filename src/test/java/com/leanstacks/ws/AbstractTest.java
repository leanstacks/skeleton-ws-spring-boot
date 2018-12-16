package com.leanstacks.ws;

import org.junit.After;
import org.junit.Before;

import com.leanstacks.ws.util.RequestContext;

/**
 * The AbstractTest class is the parent of all JUnit test classes. This class configures the test ApplicationContext and
 * test runner environment.
 * 
 * @author Matt Warman
 */
public abstract class AbstractTest {

    /**
     * The username value used in the RequestContext for Unit Tests.
     */
    public static final String USERNAME = "unittest";

    /**
     * Tasks performed before each test method.
     */
    @Before
    public void before() {
        RequestContext.setUsername(AbstractTest.USERNAME);
        doBeforeEachTest();
    }

    /**
     * Perform initialization tasks before the execution of each test method. Concrete test classes may override this
     * method to implement class-specific tasks.
     */
    public abstract void doBeforeEachTest();

    /**
     * Tasks performed after each test method.
     */
    @After
    public void after() {
        doAfterEachTest();
    }

    /**
     * Perform clean up tasks after the execution of each test method. Concrete test classes may override this method to
     * implement class-specific tasks.
     */
    public abstract void doAfterEachTest();

}
