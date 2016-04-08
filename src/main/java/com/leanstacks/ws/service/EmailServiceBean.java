package com.leanstacks.ws.service;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.leanstacks.ws.model.Greeting;

/**
 * The EmailServiceBean encapsulates all business behaviors defined by the EmailService interface.
 * 
 * @author Matt Warman
 */
@Service
public class EmailServiceBean implements EmailService {

    /**
     * The Logger for this Class.
     */
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceBean.class);

    /**
     * Default Thread sleep time in milliseconds.
     */
    private static final long SLEEP_MILLIS = 5000;

    @Override
    public Boolean send(final Greeting greeting) {
        logger.info("> send");

        Boolean success;

        // Simulate method execution time
        try {
            Thread.sleep(SLEEP_MILLIS);
        } catch (InterruptedException ie) {
            logger.info("- Thread interrupted.", ie);
            // Do nothing.
        }
        logger.info("Processing time was {} seconds.", SLEEP_MILLIS / 1000);

        success = Boolean.TRUE;

        logger.info("< send");
        return success;
    }

    @Async
    @Override
    public void sendAsync(final Greeting greeting) {
        logger.info("> sendAsync");

        send(greeting);

        logger.info("< sendAsync");
    }

    @Async
    @Override
    public Future<Boolean> sendAsyncWithResult(final Greeting greeting) {
        logger.info("> sendAsyncWithResult");

        final Boolean success = send(greeting);

        logger.info("< sendAsyncWithResult");
        return new AsyncResult<Boolean>(success);
    }

}
