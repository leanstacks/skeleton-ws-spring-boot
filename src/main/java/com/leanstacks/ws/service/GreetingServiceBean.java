package com.leanstacks.ws.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leanstacks.ws.model.Greeting;
import com.leanstacks.ws.repository.GreetingRepository;

/**
 * The GreetingServiceBean encapsulates all business behaviors operating on the
 * Greeting entity model.
 * 
 * @author Matt Warman
 */
@Service
public class GreetingServiceBean implements GreetingService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingRepository greetingRepository;

    @Override
    public Collection<Greeting> findAll() {
        logger.info("> findAll");

        Collection<Greeting> greetings = greetingRepository.findAll();

        logger.info("< findAll");
        return greetings;
    }

    @Override
    public Greeting findOne(Long id) {
        logger.info("> findOne {}", id);

        Greeting greeting = greetingRepository.findOne(id);

        logger.info("< findOne {}", id);
        return greeting;
    }

    @Transactional
    @Override
    public Greeting create(Greeting greeting) {
        logger.info("> create");

        // Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
        if (greeting.getId() != null) {
            logger.error("Attempted to create a Greeting, but id attribute was not null.");
            logger.info("< create");
            return null;
        }

        Greeting savedGreeting = greetingRepository.save(greeting);

        logger.info("< create");
        return savedGreeting;
    }

    @Transactional
    @Override
    public Greeting update(Greeting greeting) {
        logger.info("> update {}", greeting.getId());

        // Ensure the entity object to be updated exists in the repository to
        // prevent the default behavior of save() which will persist a new
        // entity if the entity matching the id does not exist
        Greeting greetingToUpdate = findOne(greeting.getId());
        if (greetingToUpdate == null) {
            logger.error("Attempted to update a Greeting, but the entity does not exist.");
            logger.info("< update {}", greeting.getId());
            return null;
        }

        Greeting updatedGreeting = greetingRepository.save(greeting);

        logger.info("< update {}", greeting.getId());
        return updatedGreeting;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        logger.info("> delete {}", id);

        greetingRepository.delete(id);

        logger.info("< delete {}", id);
    }

}
