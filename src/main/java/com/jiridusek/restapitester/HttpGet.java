package com.jiridusek.restapitester;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

/**
 * HttpGet class sends HTTP request GET
 *
 * @author  Jiri Dusek
 * @version 1.0
 * @since   2020-09-06
 */
class HttpGet implements HttpInterface {

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
        // create simple request
        Request request = new Request.Builder().url(url).build();

        return client.newCall(request).execute();
    }
}
