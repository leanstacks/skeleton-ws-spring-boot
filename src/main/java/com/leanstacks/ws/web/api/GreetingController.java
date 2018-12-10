package com.leanstacks.ws.web.api;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leanstacks.ws.model.Greeting;
import com.leanstacks.ws.service.EmailService;
import com.leanstacks.ws.service.GreetingService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * The GreetingController class is a RESTful web service controller. The <code>@RestController</code> annotation informs
 * Spring that each <code>@RequestMapping</code> method returns a <code>@ResponseBody</code>.
 * 
 * @author Matt Warman
 */
@RestController
@RequestMapping("/api/greetings")
public class GreetingController {

    /**
     * The Logger for this Class.
     */
    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    /**
     * The GreetingService business service.
     */
    @Autowired
    private transient GreetingService greetingService;

    /**
     * The EmailService business service.
     */
    @Autowired
    private transient EmailService emailService;

    /**
     * Web service endpoint to fetch all Greeting entities. The service returns the collection of Greeting entities as
     * JSON.
     * 
     * @return A List of Greeting objects.
     */
    @ApiOperation(value = "${GreetingController.getGreetings.title}",
            notes = "${GreetingController.getGreetings.notes}",
            response = Greeting.class,
            responseContainer = "List")
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization",
            value = "Basic auth_creds",
            required = true,
            dataType = "string",
            paramType = "header"))
    @GetMapping
    public List<Greeting> getGreetings() {
        logger.info("> getGreetings");

        final List<Greeting> greetings = greetingService.findAll();

        logger.info("< getGreetings");
        return greetings;
    }

    /**
     * <p>
     * Web service endpoint to fetch a single Greeting entity by primary key identifier.
     * </p>
     * <p>
     * If found, the Greeting is returned as JSON with HTTP status 200. If not found, the service returns an empty
     * response body with HTTP status 404.
     * </p>
     * 
     * @param id A Long URL path variable containing the Greeting primary key identifier.
     * @return A Greeting object, if found, and a HTTP status code as described in the method comment.
     */
    @ApiOperation(value = "${GreetingController.getGreeting.title}",
            notes = "${GreetingController.getGreeting.notes}",
            response = Greeting.class)
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization",
            value = "Basic auth_creds",
            required = true,
            dataType = "string",
            paramType = "header"))
    @GetMapping("/{id}")
    public Greeting getGreeting(@ApiParam("Greeting ID") @PathVariable final Long id) {
        logger.info("> getGreeting");

        final Optional<Greeting> greetingOptional = greetingService.findOne(id);

        logger.info("< getGreeting");
        return greetingOptional.get();
    }

    /**
     * <p>
     * Web service endpoint to create a single Greeting entity. The HTTP request body is expected to contain a Greeting
     * object in JSON format. The Greeting is persisted in the data repository.
     * </p>
     * <p>
     * If created successfully, the persisted Greeting is returned as JSON with HTTP status 201. If not created
     * successfully, the service returns an ExceptionDetail response body with HTTP status 400 or 500.
     * </p>
     * 
     * @param greeting The Greeting object to be created.
     * @return A Greeting object, if created successfully, and a HTTP status code as described in the method comment.
     */
    @ApiOperation(value = "${GreetingController.createGreeting.title}",
            notes = "${GreetingController.createGreeting.notes}",
            response = Greeting.class,
            code = 201)
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization",
            value = "Basic auth_creds",
            required = true,
            dataType = "string",
            paramType = "header"))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Greeting createGreeting(@RequestBody final Greeting greeting) {
        logger.info("> createGreeting");

        final Greeting savedGreeting = greetingService.create(greeting);

        logger.info("< createGreeting");
        return savedGreeting;
    }

    /**
     * <p>
     * Web service endpoint to update a single Greeting entity. The HTTP request body is expected to contain a Greeting
     * object in JSON format. The Greeting is updated in the data repository.
     * </p>
     * <p>
     * If updated successfully, the persisted Greeting is returned as JSON with HTTP status 200. If not found, the
     * service returns an ExceptionDetail response body and HTTP status 404. If not updated successfully, the service
     * returns an empty response body with HTTP status 400 or 500.
     * </p>
     * 
     * @param greeting The Greeting object to be updated.
     * @return A Greeting object, if updated successfully, and a HTTP status code as described in the method comment.
     */
    @ApiOperation(value = "${GreetingController.updateGreeting.title}",
            notes = "${GreetingController.updateGreeting.notes}",
            response = Greeting.class)
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization",
            value = "Basic auth_creds",
            required = true,
            dataType = "string",
            paramType = "header"))
    @PutMapping("/{id}")
    public Greeting updateGreeting(@ApiParam("Greeting ID") @PathVariable("id") final Long id,
            @RequestBody final Greeting greeting) {
        logger.info("> updateGreeting");

        greeting.setId(id);

        final Greeting updatedGreeting = greetingService.update(greeting);

        logger.info("< updateGreeting");
        return updatedGreeting;
    }

    /**
     * <p>
     * Web service endpoint to delete a single Greeting entity. The HTTP request body is empty. The primary key
     * identifier of the Greeting to be deleted is supplied in the URL as a path variable.
     * </p>
     * <p>
     * If deleted successfully, the service returns an empty response body with HTTP status 204. If not deleted
     * successfully, the service returns an ExceptionDetail response body with HTTP status 500.
     * </p>
     * 
     * @param id A Long URL path variable containing the Greeting primary key identifier.
     */
    @ApiOperation(value = "${GreetingController.deleteGreeting.title}",
            notes = "${GreetingController.deleteGreeting.notes}",
            code = 204)
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization",
            value = "Basic auth_creds",
            required = true,
            dataType = "string",
            paramType = "header"))
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGreeting(@ApiParam("Greeting ID") @PathVariable("id") final Long id) {
        logger.info("> deleteGreeting");

        greetingService.delete(id);

        logger.info("< deleteGreeting");
    }

    /**
     * <p>
     * Web service endpoint to fetch a single Greeting entity by primary key identifier and send it as an email.
     * </p>
     * <p>
     * If found, the Greeting is returned as JSON with HTTP status 200 and sent via Email. If not found, the service
     * returns an Exception response body with HTTP status 404.
     * </p>
     * 
     * @param id A Long URL path variable containing the Greeting primary key identifier.
     * @param waitForAsyncResult A boolean indicating if the web service should wait for the asynchronous email
     *            transmission.
     * @return A Greeting object, if found, and a HTTP status code as described in the method comment.
     */
    @ApiOperation(value = "${GreetingController.sendGreeting.title}",
            notes = "${GreetingController.sendGreeting.notes}",
            response = Greeting.class)
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization",
            value = "Basic auth_creds",
            required = true,
            dataType = "string",
            paramType = "header"))
    @PostMapping("/{id}/send")
    public Greeting sendGreeting(@ApiParam("Greeting ID") @PathVariable("id") final Long id,
            @ApiParam("Wait for Response") @RequestParam(value = "wait",
                    defaultValue = "false") final boolean waitForAsyncResult) {

        logger.info("> sendGreeting");

        Greeting greeting;

        try {
            greeting = greetingService.findOne(id).get();

            if (waitForAsyncResult) {
                final Future<Boolean> asyncResponse = emailService.sendAsyncWithResult(greeting);
                final boolean emailSent = asyncResponse.get();
                logger.info("- greeting email sent? {}", emailSent);
            } else {
                emailService.sendAsync(greeting);
            }
        } catch (ExecutionException | InterruptedException ex) {
            logger.error("A problem occurred sending the Greeting.", ex);
            throw new IllegalStateException(ex);
        }

        logger.info("< sendGreeting");
        return greeting;

    }

}
