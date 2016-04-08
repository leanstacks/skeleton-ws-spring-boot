package com.leanstacks.ws.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * The RequestContext facilitates the storage of information for the duration of a single request (or web service
 * transaction).
 * </p>
 * <p>
 * RequestContext attributes are stored in ThreadLocal objects.
 * </p>
 * 
 * @author Matt Warman
 * 
 */
public final class RequestContext {

    /**
     * The Logger for this Class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RequestContext.class);

    /**
     * ThreadLocal storage of username Strings.
     */
    private static ThreadLocal<String> usernames = new ThreadLocal<String>();

    private RequestContext() {

    }

    /**
     * Get the username for the current thread.
     * 
     * @return A String username.
     */
    public static String getUsername() {
        return usernames.get();
    }

    /**
     * Set the username for the current thread.
     * 
     * @param username A String username.
     */
    public static void setUsername(final String username) {
        usernames.set(username);
        logger.debug("RequestContext added username {} to current thread", username);
    }

    /**
     * Initialize the ThreadLocal attributes for the current thread.
     */
    public static void init() {
        usernames.set(null);
    }

}
