package com.leanstacks.ws.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>
 * The BCryptPasswordEncoderUtil class assists engineers during application construction. It is not intended for use in
 * a 'live' application.
 * </p>
 * <p>
 * The class uses a BCryptPasswordEncoder to encrypt clear text values using it's native hashing algorithm. This utility
 * may be used to create encrypted password values in a database initialization script used for unit testing or local
 * machine development.
 * </p>
 * 
 * @author Matt Warman
 *
 */
public class BCryptPasswordEncoderUtil {

    /**
     * The Logger for this Class.
     */
    private static final Logger logger = LoggerFactory.getLogger(BCryptPasswordEncoderUtil.class);

    /**
     * The format for encoder messages.
     */
    private static final String ENCODED_FORMAT = "Argument: %s \tEncoded: %s \n";

    /**
     * A Writer for printing messages to the console.
     */
    private transient Writer writer;

    /**
     * Uses a BCryptPasswordEncoder to hash the clear text value.
     * 
     * @param clearText A String of clear text to be encrypted.
     * @return The encrypted (hashed) value.
     */
    public String encode(final String clearText) {
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(clearText);
    }

    /**
     * Facilitates gathering user input and invoking the class behavior.
     * 
     * @param args An array of command line input values. (not used)
     */
    public static void main(final String... args) {

        final BCryptPasswordEncoderUtil encoderUtil = new BCryptPasswordEncoderUtil();

        for (final String arg : args) {
            final String encodedText = encoderUtil.encode(arg);
            final String message = String.format(ENCODED_FORMAT, arg, encodedText);
            encoderUtil.write(message);
        }

        encoderUtil.close();

    }

    /**
     * Writes a message to the console.
     * 
     * @param str A String message value.
     */
    private void write(final String str) {

        try {
            if (writer == null) {
                writer = new OutputStreamWriter(System.out);
            }
            writer.write(str);
        } catch (IOException ioe) {
            logger.error("Writer cannot write.", ioe);
            System.exit(1);
        }
    }

    /**
     * Closes all system resources and prepares for application termination.
     */
    private void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ioe) {
            logger.error("Problem closing resources.", ioe);
            System.exit(1);
        }
    }

}
