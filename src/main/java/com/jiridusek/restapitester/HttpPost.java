package com.jiridusek.restapitester;

import okhttp3.*;
import java.io.IOException;

/**
 * HttpGet class sends HTTP request POST
 *
 * @author  Jiri Dusek
 * @version 1.0
 * @since   2020-09-06
 */
class HttpPost implements HttpInterface {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * This method sends the HTTP request w/o payload
     *
     * @param client OkHttpClient Instance of HTTP client
     * @param url String Destination URL
     * @param body String Payload (JSON)
     * @return Response Response object from the server
     * @throws IOException Throws IOException
     */
    @Override
    public Response send(OkHttpClient client, String url, String body) throws IOException {
        // create request body as JSON
        RequestBody requestBody = RequestBody.create(body, JSON);
        // prepare request with authorization token and request body (JSON)
        Request request = new Request.Builder()
                .header("Authorization", BearerToken.getToken())
                .url(url)
                .post(requestBody)
                .build();

        return client.newCall(request).execute();
    }
}
