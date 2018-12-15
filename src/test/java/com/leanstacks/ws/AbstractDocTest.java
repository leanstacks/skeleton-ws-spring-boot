package com.leanstacks.ws;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.leanstacks.ws.util.RequestContext;

/**
 * The AbstractDocTest class is the parent of all JUnit test classes which create Spring REST Docs. This class
 * configures the test ApplicationContext and test runner environment to facilitate the creation of API documentation
 * via Spring REST Docs.
 * 
 * @author Matt Warman
 */
public abstract class AbstractDocTest {

    /**
     * A MockMvc instance configured with Spring REST Docs configuration.
     */
    protected transient MockMvc mockMvc;

    /**
     * A WebApplicationContext instance.
     */
    @Autowired
    private transient WebApplicationContext context;

    /**
     * A JUnit 4.x Rule for Spring REST Documentation generation. Note that the snippet output directory is only
     * provided because this project contains both 'build.gradle' and 'pom.xml' files. Spring REST Docs uses those files
     * to auto-detect the build system and automatically sets certain configuration values which cannot be overridden.
     */
    @Rule
    public transient JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    /**
     * Perform set up activities before each unit test. Invoked by the JUnit framework.
     */
    @Before
    public void before() {
        RequestContext.setUsername(AbstractTest.USERNAME);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation).uris().withScheme("https")
                        .withHost("api.leanstacks.net").withPort(443).and().operationPreprocessors()
                        .withRequestDefaults(prettyPrint()).withResponseDefaults(prettyPrint()))
                .build();
        doBeforeEachTest();
    }

    /**
     * Perform initialization tasks before the execution of each test method.
     */
    public abstract void doBeforeEachTest();

    /**
     * Perform clean up activities after each unit test. Invoked by the JUnit framework.
     */
    @After
    public void after() {
        doAfterEachTest();
    }

    /**
     * Perform clean up tasks after the execution of each test method.
     */
    public abstract void doAfterEachTest();

}
