package com.leanstacks.ws.web.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leanstacks.ws.model.Greeting;
import com.leanstacks.ws.service.GreetingService;

/**
 * The GreetingController class is a RESTful web service controller. The
 * <code>@RestController</code> annotation informs Spring that each
 * <code>@RequestMapping</code> method returns a <code>@ResponseBody</code>.
 * 
 * @author Matt Warman
 */
@RestController
public class GreetingController extends BaseController {

    @Autowired
    private GreetingService greetingService;

    @RequestMapping(
            value = "/api/greetings",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreetings() throws Exception {
        logger.info("> getGreetings");

        Collection<Greeting> greetings = greetingService.findAll();

        logger.info("< getGreetings");
        return new ResponseEntity<Collection<Greeting>>(greetings,
                HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/greetings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getGreeting(@PathVariable Long id)
            throws Exception {
        logger.info("> getGreeting");

        Greeting greeting = greetingService.findOne(id);
        if (greeting == null) {
            logger.info("< getGreeting");
            return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
        }

        logger.info("< getGreeting");
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/greetings",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> createGreeting(
            @RequestBody Greeting greeting) throws Exception {
        logger.info("> createGreeting");

        Greeting savedGreeting = greetingService.create(greeting);

        logger.info("< createGreeting");
        return new ResponseEntity<Greeting>(savedGreeting, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/greetings/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> updateGreeting(
            @RequestBody Greeting greeting) throws Exception {
        logger.info("> updateGreeting");

        Greeting updatedGreeting = greetingService.update(greeting);

        logger.info("< updateGreeting");
        return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/greetings/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") Long id)
            throws Exception {
        logger.info("> deleteGreeting");

        greetingService.delete(id);

        logger.info("< deleteGreeting");
        return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
    }

}
