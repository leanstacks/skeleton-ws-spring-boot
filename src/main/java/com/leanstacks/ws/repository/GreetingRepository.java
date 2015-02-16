package com.leanstacks.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leanstacks.ws.model.Greeting;

/**
 * The GreetingRepository interface is a Spring Data JPA data repository for
 * Greeting entities. The GreetingRepository provides all the data access
 * behaviors exposed by <code>JpaRepository</code> and additional custom
 * behaviors may be defined in this interface.
 * 
 * @author Matt Warman
 */
@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long> {

}
