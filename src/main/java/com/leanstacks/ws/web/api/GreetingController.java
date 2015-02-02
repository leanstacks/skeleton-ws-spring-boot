package com.leanstacks.ws.web.api;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leanstacks.ws.model.Greeting;

@RestController
public class GreetingController {

    @RequestMapping(
            value = "/api/greetings",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Greeting>> getGreeting() {

        List<Greeting> greetings = new ArrayList<Greeting>();

        Greeting g1 = new Greeting();
        g1.setId(BigInteger.valueOf(1));
        g1.setText("Hello World!");
        greetings.add(g1);

        Greeting g2 = new Greeting();
        g2.setId(BigInteger.valueOf(2));
        g2.setText("Hola Mundo!");
        greetings.add(g2);

        return new ResponseEntity<List<Greeting>>(greetings, HttpStatus.OK);
    }

}
