package com.leanstacks.ws.service;

import java.util.List;
import java.util.Optional;

import com.leanstacks.ws.model.Greeting;

/**
 * <p>
 * The GreetingService interface defines all public business behaviors for operations on the Greeting entity model.
 * </p>
 * <p>
 * This interface should be injected into GreetingService clients, not the implementation bean.
 * </p>
 * 
 * @author Matt Warman
 */
public interface GreetingService {

    /**
     * Find all Greeting entities.
     * 
     * @return A List of Greeting objects.
     */
    List<Greeting> findAll();

    /**
     * Find a single Greeting entity by primary key identifier. Returns an Optional wrapped Greeting.
     * 
     * @param id A Long primary key identifier.
     * @return A Optional Greeting
     */
    Optional<Greeting> findOne(Long id);

    /**
     * Persists a Greeting entity in the data store.
     * 
     * @param greeting A Greeting object to be persisted.
     * @return A persisted Greeting object or <code>null</code> if a problem occurred.
     */
    Greeting create(Greeting greeting);

    /**
     * Updates a previously persisted Greeting entity in the data store.
     * 
     * @param greeting A Greeting object to be updated.
     * @return An updated Greeting object or <code>null</code> if a problem occurred.
     */
    Greeting update(Greeting greeting);

    /**
     * Removes a previously persisted Greeting entity from the data store.
     * 
     * @param id A Long primary key identifier.
     */
    void delete(Long id);

    /**
     * Evicts all members of the "greetings" cache.
     */
    void evictCache();

}
