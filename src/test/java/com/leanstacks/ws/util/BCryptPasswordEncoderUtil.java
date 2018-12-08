package com.leanstacks.ws.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

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
     * @throws IOException Thrown if performing IO operations fails.
     */
    public static void main(final String... args) throws IOException {

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
     * @throws IOException Thrown if writing output fails.
     */
    private void write(final String str) throws IOException {

        if (writer == null) {
            writer = new OutputStreamWriter(System.out);
        }
        writer.write(str);

    }

    /**
     * Closes all system resources and prepares for application termination.
     * 
     * @throws IOException Thrown if closing the output stream fails.
     */
    private void close() throws IOException {

        if (writer != null) {
            writer.close();
        }

    }

}
