package com.leanstacks.ws.actuator.health;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.leanstacks.ws.model.Greeting;
import com.leanstacks.ws.service.GreetingService;

@Component
public class GreetingHealthIndicator implements HealthIndicator {

    @Autowired
    private GreetingService greetingService;

    @Override
    public Health health() {
        Collection<Greeting> greetings = greetingService.findAll();

        if (greetings == null || greetings.size() == 0) {
            return Health.down().withDetail("count", 0).build();
        }

        return Health.up().withDetail("count", greetings.size()).build();
    }

}
