package com.leanstacks.ws.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * The <code>CounterService</code> captures metrics for Spring Actuator.
     */
    @Autowired
    private CounterService counterService;

    /**
     * The Spring Data repository for Greeting entities.
     */
    @Autowired
    private GreetingRepository greetingRepository;

    @Override
    public Collection<Greeting> findAll() {
        logger.info("> findAll");

        counterService.increment("method.invoked.greetingServiceBean.findAll");

        Collection<Greeting> greetings = greetingRepository.findAll();

        logger.info("< findAll");
        return greetings;
    }

    @Cacheable(
            value = "greetings",
            key = "#id")
    @Override
    public Greeting findOne(Long id) {
        logger.info("> findOne {}", id);

        counterService.increment("method.invoked.greetingServiceBean.findOne");

        Greeting greeting = greetingRepository.findOne(id);

        logger.info("< findOne {}", id);
        return greeting;
    }

    @CachePut(
            value = "greetings",
            key = "#result.id")
    @Transactional
    @Override
    public Greeting create(Greeting greeting) {
        logger.info("> create");

        counterService.increment("method.invoked.greetingServiceBean.create");

        // Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
        if (greeting.getId() != null) {
            logger.error(
                    "Attempted to create a Greeting, but id attribute was not null.");
            logger.info("< create");
            throw new EntityExistsException(
                    "Cannot create new Greeting with supplied id.  The id attribute must be null to create an entity.");
        }

        Greeting savedGreeting = greetingRepository.save(greeting);

        logger.info("< create");
        return savedGreeting;
    }

    @CachePut(
            value = "greetings",
            key = "#greeting.id")
    @Transactional
    @Override
    public Greeting update(Greeting greeting) {
        logger.info("> update {}", greeting.getId());

        counterService.increment("method.invoked.greetingServiceBean.update");

        // Ensure the entity object to be updated exists in the repository to
        // prevent the default behavior of save() which will persist a new
        // entity if the entity matching the id does not exist
        Greeting greetingToUpdate = findOne(greeting.getId());
        if (greetingToUpdate == null) {
            logger.error(
                    "Attempted to update a Greeting, but the entity does not exist.");
            logger.info("< update {}", greeting.getId());
            throw new NoResultException("Requested Greeting not found.");
        }

        greetingToUpdate.setText(greeting.getText());
        Greeting updatedGreeting = greetingRepository.save(greetingToUpdate);

        logger.info("< update {}", greeting.getId());
        return updatedGreeting;
    }

    @CacheEvict(
            value = "greetings",
            key = "#id")
    @Transactional
    @Override
    public void delete(Long id) {
        logger.info("> delete {}", id);

        counterService.increment("method.invoked.greetingServiceBean.delete");

        greetingRepository.delete(id);

        logger.info("< delete {}", id);
    }

    @CacheEvict(
            value = "greetings",
            allEntries = true)
    @Override
    public void evictCache() {
        logger.info("> evictCache");

        counterService
                .increment("method.invoked.greetingServiceBean.evictCache");

        logger.info("< evictCache");
    }

}
