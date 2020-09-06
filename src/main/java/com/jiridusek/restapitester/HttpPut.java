package com.jiridusek.restapitester;

import okhttp3.*;
import java.io.IOException;

/**
 * HttpPut class sends HTTP request PUT with authorization token
 *
 * @author  Jiri Dusek
 * @version 1.0
 * @since   2020-09-06
 */
class HttpPut implements HttpInterface {

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
        // prepare request body as JSON
        RequestBody requestBody = RequestBody.create(body, JSON);
        // create simple request with authorization token and request body (JSON)
        Request request = new Request.Builder()
                .header("Authorization", BearerToken.getToken())
                .url(url)
                .put(requestBody)
                .build();

        return client.newCall(request).execute();
    }
}
