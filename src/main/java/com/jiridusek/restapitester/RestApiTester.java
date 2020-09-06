package com.jiridusek.restapitester;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * This exercise is an implementation of task given by Barclays HR.
 * It simply test public REST API with few most important test cases.
 *
 * TASK description:
 * Take any public REST API (e.g. https://gorest.co.in/) you can find and implement few most important (regression)
 * test cases using Java technologies to validate functionality.
 *
 * @author  Jiri Dusek
 * @version 1.0
 * @since   2020-09-06
 */
public class RestApiTester {
    /**
     * An entry point for Rest-API tester program
     *
     * @param args Unsupported
     */
    public static void main(String[] args) {

        Tester tester = new Tester();
        try {
            tester.test();
        } catch (IOException e) {
            System.out.println(MessageFormat.format("An exception happened during testing : {0}", e.getMessage()));
        }
    }
}
