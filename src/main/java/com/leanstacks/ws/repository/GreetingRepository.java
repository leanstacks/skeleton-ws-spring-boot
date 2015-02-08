package com.leanstacks.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leanstacks.ws.model.Greeting;

@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long> {

}
