package com.leanstacks.ws.util;

import java.util.Scanner;

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
     * Uses a BCryptPasswordEncoder to hash the clear text value.
     * 
     * @param clearText A String of clear text to be encrypted.
     * @return The encrypted (hashed) value.
     */
    public static String encode(String clearText) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(clearText);
    }

    /**
     * Facilitates gathering user input and invoking the class behavior.
     * 
     * @param args An array of command line input values. (not used)
     */
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        try {

            System.out.println("Enter value to be encrypted:");
            String input = console.nextLine();

            System.out.println("clear text:" + input);
            System.out.println("   encoded:" + BCryptPasswordEncoderUtil.encode(input));

        } catch (Exception ex) {
            System.err.println("Exception occurred. " + ex.getMessage());
            ex.printStackTrace(System.err);
        } finally {
            console.close();
        }

    }

}
