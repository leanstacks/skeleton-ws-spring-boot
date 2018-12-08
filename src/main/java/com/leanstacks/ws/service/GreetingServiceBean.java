package com.leanstacks.ws.service;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leanstacks.ws.Application;
import com.leanstacks.ws.model.Greeting;
import com.leanstacks.ws.repository.GreetingRepository;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * The GreetingServiceBean encapsulates all business behaviors operating on the Greeting entity model.
 * 
 * @author Matt Warman
 */
@Service
public class GreetingServiceBean implements GreetingService {

    /**
     * The Logger for this Class.
     */
    private static final Logger logger = LoggerFactory.getLogger(GreetingServiceBean.class);

    /**
     * Metric Counter for findAll method invocations.
     */
    private final transient Counter findAllMethodInvocationCounter;
    /**
     * Metric Counter for findOne method invocations.
     */
    private final transient Counter findOneMethodInvocationCounter;
    /**
     * Metric Counter for create method invocations.
     */
    private final transient Counter createMethodInvocationCounter;
    /**
     * Metric Counter for update method invocations.
     */
    private final transient Counter updateMethodInvocationCounter;
    /**
     * Metric Counter for delete method invocations.
     */
    private final transient Counter deleteMethodInvocationCounter;
    /**
     * Metric Counter for evictCache method invocations.
     */
    private final transient Counter evictCacheMethodInvocationCounter;

    /**
     * The Spring Data repository for Greeting entities.
     */
    private final transient GreetingRepository greetingRepository;

    /**
     * Construct a GreetingServiceBean.
     * 
     * @param greetingRepository A GreetingRepository.
     * @param meterRegistry A MeterRegistry.
     */
    @Autowired
    public GreetingServiceBean(final GreetingRepository greetingRepository, final MeterRegistry meterRegistry) {
        this.greetingRepository = greetingRepository;
        this.findAllMethodInvocationCounter = meterRegistry.counter("method.invoked.greetingServiceBean.findAll");
        this.findOneMethodInvocationCounter = meterRegistry.counter("method.invoked.greetingServiceBean.findOne");
        this.createMethodInvocationCounter = meterRegistry.counter("method.invoked.greetingServiceBean.create");
        this.updateMethodInvocationCounter = meterRegistry.counter("method.invoked.greetingServiceBean.update");
        this.deleteMethodInvocationCounter = meterRegistry.counter("method.invoked.greetingServiceBean.delete");
        this.evictCacheMethodInvocationCounter = meterRegistry.counter("method.invoked.greetingServiceBean.evictCache");
    }

    @Override
    public Collection<Greeting> findAll() {
        logger.info("> findAll");

        findAllMethodInvocationCounter.increment();

        final Collection<Greeting> greetings = greetingRepository.findAll();

        logger.info("< findAll");
        return greetings;
    }

    @Cacheable(value = Application.CACHE_GREETINGS,
            key = "#id")
    @Override
    public Greeting findOne(final Long id) {
        logger.info("> findOne {}", id);

        findOneMethodInvocationCounter.increment();

        final Optional<Greeting> result = greetingRepository.findById(id);

        logger.info("< findOne {}", id);
        return result.isPresent() ? result.get() : null;
    }

    @CachePut(value = Application.CACHE_GREETINGS,
            key = "#result.id")
    @Transactional
    @Override
    public Greeting create(final Greeting greeting) {
        logger.info("> create");

        createMethodInvocationCounter.increment();

        // Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
        if (greeting.getId() != null) {
            logger.error("Attempted to create a Greeting, but id attribute was not null.");
            logger.info("< create");
            throw new EntityExistsException(
                    "Cannot create new Greeting with supplied id.  The id attribute must be null to create an entity.");
        }

        final Greeting savedGreeting = greetingRepository.save(greeting);

        logger.info("< create");
        return savedGreeting;
    }

    @CachePut(value = Application.CACHE_GREETINGS,
            key = "#greeting.id")
    @Transactional
    @Override
    public Greeting update(final Greeting greeting) {
        logger.info("> update {}", greeting.getId());

        updateMethodInvocationCounter.increment();

        // Ensure the entity object to be updated exists in the repository to
        // prevent the default behavior of save() which will persist a new
        // entity if the entity matching the id does not exist
        final Greeting greetingToUpdate = findOne(greeting.getId());
        if (greetingToUpdate == null) {
            logger.error("Attempted to update a Greeting, but the entity does not exist.");
            logger.info("< update {}", greeting.getId());
            throw new NoResultException("Requested Greeting not found.");
        }

        greetingToUpdate.setText(greeting.getText());
        final Greeting updatedGreeting = greetingRepository.save(greetingToUpdate);

        logger.info("< update {}", greeting.getId());
        return updatedGreeting;
    }

    @CacheEvict(value = Application.CACHE_GREETINGS,
            key = "#id")
    @Transactional
    @Override
    public void delete(final Long id) {
        logger.info("> delete {}", id);

        deleteMethodInvocationCounter.increment();

        greetingRepository.deleteById(id);

        logger.info("< delete {}", id);
    }

    @CacheEvict(value = Application.CACHE_GREETINGS,
            allEntries = true)
    @Override
    public void evictCache() {
        logger.info("> evictCache");

        evictCacheMethodInvocationCounter.increment();

        logger.info("< evictCache");
    }

}
