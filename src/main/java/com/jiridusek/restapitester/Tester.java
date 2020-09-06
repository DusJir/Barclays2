package com.jiridusek.restapitester;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static java.text.MessageFormat.format;

/**
 * Tester class is main class with various tests for public REST API.
 * It contains a few of them, not all of them possible.
 *
 * @author  Jiri Dusek
 * @version 1.0
 * @since   2020-09-06
 */
class Tester {
    private static final OkHttpClient client = new OkHttpClient();
    private static final Random random = new Random();

    /**
     * Method test() declares and calls various positive and negative tests to public REST API.
     *
     * @throws IOException Throws IOException
     */
    void test() throws IOException {

        final String BASE_URL = "https://gorest.co.in/public-api/";
        final HttpGet httpGet = new HttpGet();
        final HttpPost httpPost = new HttpPost();
        final HttpPut httpPut = new HttpPut();
        final HttpDelete httpDelete = new HttpDelete();
        final HttpDeleteNoAuth httpDeleteNoAuth= new HttpDeleteNoAuth();
        String json;
        String responseBody;

        // POSITIVE TESTS BLOCK
        output("**************************");
        output("***** POSITIVE TESTS *****");
        output("**************************");

        // GET test
        testCase("Positive resource test - get users", "GET",
                BASE_URL + "users", null, httpGet);

        // POST test
        // prepare new user
        json = "{" +
                "\"name\":\"" + randomStringGenerator() + "\"," +
                "\"email\":\"" + randomStringGenerator() + "@xyz.com\"," +
                "\"gender\":\"Male\"," +
                "\"status\":\"Active\"" +
                "}";
        responseBody = testCase("Positive resource test - add new user", "POST",
                BASE_URL + "users", json, httpPost);
        String id = parseIdFromBody(responseBody);

        // PUT test
        // prepare existing user update
        json = "{" +
                "\"name\":\"" + randomStringGenerator() + "\"," +
                "\"email\":\"" + randomStringGenerator() + "@xyz.com\"," +
                "\"gender\":\"Female\"," +
                "\"status\":\"Inactive\"" +
                "}";
        testCase("Positive resource test - update user", "PUT",
                BASE_URL + "users/" + id, json, httpPut);

        testCase("Positive resource test - update user (idempotency test)", "PUT",
                BASE_URL + "users/" + id, json, httpPut);

        // GET test with params
        testCase("Positive resource test - get updated user", "GET",
                BASE_URL + "users/" + id, null, httpGet);

        // GET find test
        testCase("Positive resource test - find user by ID", "GET",
                BASE_URL + "users?id=" + id, null, httpGet);

        // nested POST test
        // prepare new user's post
        json = "{" +
                "\"user_id\":\"" + id + "\"," +
                "\"body\":\"This is body\"," +
                "\"title\":\"This is title\"" +
                "}";
        responseBody = testCase("Positive resource test - add new user's post", "POST",
                BASE_URL + "posts", json, httpPost);
        String postId = parseIdFromBody(responseBody);

        // nested GET test
        testCase("Positive resource test - get user's posts", "GET",
                BASE_URL + "users/" + id + "/posts", null, httpGet);

        // nested DELETE test
        testCase("Positive resource test - delete user's post", "DELETE",
                BASE_URL + "posts/" + postId, null, httpDelete);

        // basic DELETE test
        testCase("Positive resource test - delete user", "DELETE",
                BASE_URL + "users/" + id, null, httpDelete);

        // NEGATIVE TESTS BLOCK
        output("**************************");
        output("***** NEGATIVE TESTS *****");
        output("**************************");

        // GET test - invalid url
        testCase("Negative resource test - get non-existing user", "GET",
                BASE_URL + "users/9999999999", null, httpGet);

        // POST test - invalid structure (nome field instead of name)
        // prepare new user
        json = "{" +
                "\"nome\":\"" + randomStringGenerator() + "\"," +
                "\"email\":\"" + randomStringGenerator() + "@xyz.com\"," +
                "\"gender\":\"Male\"," +
                "\"status\":\"Active\"" +
                "}";
        testCase("Negative resource test - add new user with invalid structure", "POST",
                BASE_URL + "users", json, httpPost);

        // basic DELETE test - user does not exists
        testCase("Negative resource test - delete non-existing user", "DELETE",
                BASE_URL + "users/123abc", null, httpDelete);

        // basic DELETE test - user does with no authorization
        testCase("Negative resource test - delete user with no auth token", "DELETE",
                BASE_URL + "users/1", null, httpDeleteNoAuth);

    }

    /**
     * This method calls HttpInterface implementations based on parameters.
     * It also processes server's response and notify user of the results.
     *
     * @param testName String Test description
     * @param method String Used HTTP verb
     * @param url String URL
     * @param body String Payload
     * @param httpInterface HttpInterface An implementation of HttpInterface used for this test case
     * @return String Server's response
     * @throws IOException Throws IOException
     */
    private String testCase(String testName, String method, String url,
                               String body, HttpInterface httpInterface) throws IOException {
        // write test name
        output(testName);
        // write test description
        output("Sending {0} request to {1}...", method, url);
        Response response = httpInterface.send(client, url, body);
        String responseBody = Objects.requireNonNull(response.body()).string();
        // write response
        output("Server response is {0}", responseBody);
        // write response code
        output("Response code is {0}\n", parseCodeFromBody(responseBody));

        return responseBody;
    }

    /**
     * Helper method to generate unique string.
     * It is required f.e. for email which is required by public REST API being unique.
     *
     * @return String 15 chars length string
     */
    private String randomStringGenerator() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Helper method to parse response code out of response.
     * For some reason I get code 200 using response.code() method even if it f.e. 201.
     * This is more complicated but more exact than use response.code();
     *
     * @param body String String representation of Response object
     * @return String Response code
     */
    private String parseCodeFromBody(String body) {
        return StringUtils.substringBetween(body, "\"code\":", ",");
    }

    /**
     * Helper method to parse id out of response.
     *
     * @param body String String representation of Response object
     * @return String Resource ID
     */
    private String parseIdFromBody(String body) {
        return StringUtils.substringBetween(body, "\"id\":", ",");
    }

    /**
     * Helper method to format console output.
     *
     * @param line String Output string with arg placeholders.
     * @param args String Placeholder values (optional)
     */
    private void output(String line, Object...args) {
        System.out.println(format(line, args));
    }
}
