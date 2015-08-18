package com.leanstacks.ws.util;

import java.util.Scanner;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderUtil {

    public static String encode(String clearText) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(clearText);
    }

    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        try {

            System.out.println("Enter value to be encrypted:");
            String input = console.nextLine();

            System.out.println("clear text:" + input);
            System.out.println(
                    "   encoded:" + BCryptPasswordEncoderUtil.encode(input));

        } catch (Exception e) {
            System.err.println("Exception occurred. " + e.getMessage());
            e.printStackTrace(System.err);
        } finally {
            console.close();
        }

    }

}
