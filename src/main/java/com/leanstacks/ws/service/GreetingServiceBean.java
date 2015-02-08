package com.leanstacks.ws.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leanstacks.ws.model.Greeting;

/**
 * The GreetingServiceBean encapsulates all business behaviors operating on the
 * Greeting entity model.
 * 
 * @author Matt Warman
 */
@Service
public class GreetingServiceBean implements GreetingService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static BigInteger nextId;
    private static Map<BigInteger, Greeting> greetingMap;

    private static Greeting saveGreeting(Greeting greeting) {
        if (greetingMap == null) {
            greetingMap = new HashMap<BigInteger, Greeting>();
            nextId = BigInteger.ONE;
        }
        // If Update...
        if (greeting.getId() != null) {
            Greeting oldGreeting = greetingMap.get(greeting.getId());
            if (oldGreeting == null) {
                return null;
            }
            greetingMap.remove(greeting.getId());
            greetingMap.put(greeting.getId(), greeting);
            return greeting;
        }
        // If Create
        greeting.setId(nextId);
        nextId = nextId.add(BigInteger.ONE);
        greetingMap.put(greeting.getId(), greeting);
        return greeting;
    }

    private static boolean deleteGreeting(BigInteger id) {
        Greeting deletedGreeting = greetingMap.remove(id);
        if (deletedGreeting == null) {
            return false;
        }
        return true;
    }

    static {
        Greeting g1 = new Greeting();
        g1.setText("Hello World!");
        saveGreeting(g1);

        Greeting g2 = new Greeting();
        g2.setText("Hola Mundo!");
        saveGreeting(g2);
    }

    @Override
    public Collection<Greeting> findAll() {
        logger.info("> findAll");

        Collection<Greeting> greetings = greetingMap.values();

        logger.info("< findAll");
        return greetings;
    }

    @Override
    public Greeting findOne(BigInteger id) {
        logger.info("> findOne {}", id);

        Greeting greeting = greetingMap.get(id);

        logger.info("< findOne {}", id);
        return greeting;
    }

    @Override
    public Greeting create(Greeting greeting) {
        logger.info("> create");

        Greeting savedGreeting = saveGreeting(greeting);

        logger.info("< create");
        return savedGreeting;
    }

    @Override
    public Greeting update(Greeting greeting) {
        logger.info("> update {}", greeting.getId());

        Greeting updatedGreeting = saveGreeting(greeting);

        logger.info("< update {}", greeting.getId());
        return updatedGreeting;
    }

    @Override
    public boolean delete(BigInteger id) {
        logger.info("> delete {}", id);

        boolean deleted = deleteGreeting(id);

        logger.info("< delete {}", id);
        return deleted;
    }

}
