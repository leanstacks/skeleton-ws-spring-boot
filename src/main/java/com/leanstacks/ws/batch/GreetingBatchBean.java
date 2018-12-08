package com.leanstacks.ws.batch;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.leanstacks.ws.model.Greeting;
import com.leanstacks.ws.service.GreetingService;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * The GreetingBatchBean contains <code>@Scheduled</code> methods operating on Greeting entities to perform batch
 * operations.
 * 
 * @author Matt Warman
 */
@Component
@Profile("batch")
public class GreetingBatchBean {

    /**
     * The Logger for this Class.
     */
    private static final Logger logger = LoggerFactory.getLogger(GreetingBatchBean.class);

    /**
     * Format for printed messages.
     */
    private static final String MESSAGE_FORMAT = "There are {} greetings in the data store.";

    /**
     * Metric Counter for cron method invocations.
     */
    private final transient Counter cronMethodCounter;
    /**
     * Metric Counter for fixed rate method invocations.
     */
    private final transient Counter fixedRateMethodCounter;
    /**
     * Metric Counter for fixed rate initial delay method invocations.
     */
    private final transient Counter fixedRateInitialDelayMethodCounter;
    /**
     * Metric Counter for fixed delay method invocations.
     */
    private final transient Counter fixedDelayMethodCounter;
    /**
     * Metric Counter for fixed delay initial delay method invocations.
     */
    private final transient Counter fixedDelayInitialDelayMethodCounter;

    /**
     * The GreetingService business service.
     */
    private final transient GreetingService greetingService;

    /**
     * Construct a GreetingBatchBean with supplied dependencies.
     * 
     * @param greetingService A GreetingService.
     * @param meterRegistry A MeterRegistry.
     */
    @Autowired
    public GreetingBatchBean(final GreetingService greetingService, final MeterRegistry meterRegistry) {
        this.greetingService = greetingService;
        this.cronMethodCounter = meterRegistry.counter("method.invoked.greetingBatchBean.cronJob");
        this.fixedRateMethodCounter = meterRegistry.counter("method.invoked.greetingBatchBean.fixedRateJob");
        this.fixedRateInitialDelayMethodCounter = meterRegistry
                .counter("method.invoked.greetingBatchBean.fixedRateJobWithInitialDelay");
        this.fixedDelayMethodCounter = meterRegistry.counter("method.invoked.greetingBatchBean.fixedDelayJob");
        this.fixedDelayInitialDelayMethodCounter = meterRegistry
                .counter("method.invoked.greetingBatchBean.fixedDelayJobWithInitialDelay");
    }

    /**
     * Use a cron expression to execute logic on a schedule. Expression: second minute hour day-of-month month weekday
     * 
     * @see http ://docs.spring.io/spring/docs/current/javadoc-api/org/ springframework
     *      /scheduling/support/CronSequenceGenerator.html
     */
    @Scheduled(cron = "${batch.greeting.cron}")
    public void cronJob() {
        logger.info("> cronJob");

        cronMethodCounter.increment();

        // Add scheduled logic here

        final Collection<Greeting> greetings = greetingService.findAll();
        logger.info(MESSAGE_FORMAT, greetings.size());

        logger.info("< cronJob");
    }

    /**
     * Execute logic beginning at fixed intervals. Use the <code>fixedRate</code> element to indicate how frequently the
     * method is to be invoked.
     */
    @Scheduled(fixedRateString = "${batch.greeting.fixedrate}")
    public void fixedRateJob() {
        logger.info("> fixedRateJob");

        fixedRateMethodCounter.increment();

        // Add scheduled logic here

        final Collection<Greeting> greetings = greetingService.findAll();
        logger.info(MESSAGE_FORMAT, greetings.size());

        logger.info("< fixedRateJob");
    }

    /**
     * Execute logic beginning at fixed intervals with a delay after the application starts. Use the
     * <code>fixedRate</code> element to indicate how frequently the method is to be invoked. Use the
     * <code>initialDelay</code> element to indicate how long to wait after application startup to schedule the first
     * execution.
     */
    @Scheduled(initialDelayString = "${batch.greeting.initialdelay}",
            fixedRateString = "${batch.greeting.fixedrate}")
    public void fixedRateJobWithInitialDelay() {
        logger.info("> fixedRateJobWithInitialDelay");

        fixedRateInitialDelayMethodCounter.increment();

        // Add scheduled logic here

        final Collection<Greeting> greetings = greetingService.findAll();
        logger.info(MESSAGE_FORMAT, greetings.size());

        logger.info("< fixedRateJobWithInitialDelay");
    }

    /**
     * Execute logic with a delay between the end of the last execution and the beginning of the next. Use the
     * <code>fixedDelay</code> element to indicate the time to wait between executions.
     */
    @Scheduled(fixedDelayString = "${batch.greeting.fixeddelay}")
    public void fixedDelayJob() {
        logger.info("> fixedDelayJob");

        fixedDelayMethodCounter.increment();

        // Add scheduled logic here

        final Collection<Greeting> greetings = greetingService.findAll();
        logger.info(MESSAGE_FORMAT, greetings.size());

        logger.info("< fixedDelayJob");
    }

    /**
     * Execute logic with a delay between the end of the last execution and the beginning of the next. Use the
     * <code>fixedDelay</code> element to indicate the time to wait between executions. Use the
     * <code>initialDelay</code> element to indicate how long to wait after application startup to schedule the first
     * execution.
     */
    @Scheduled(initialDelayString = "${batch.greeting.initialdelay}",
            fixedDelayString = "${batch.greeting.fixeddelay}")
    public void fixedDelayJobWithInitialDelay() {
        logger.info("> fixedDelayJobWithInitialDelay");

        fixedDelayInitialDelayMethodCounter.increment();

        // Add scheduled logic here

        final Collection<Greeting> greetings = greetingService.findAll();
        logger.info(MESSAGE_FORMAT, greetings.size());

        logger.info("< fixedDelayJobWithInitialDelay");
    }

}
