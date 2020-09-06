package com.jiridusek.restapitester;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

/**
 * HttpDeleteNoAuth class sends HTTP request DELETE with invalid authorization token
 *
 * @author  Jiri Dusek
 * @version 1.0
 * @since   2020-09-06
 */
public class HttpDeleteNoAuth implements HttpInterface {

    /**
     * This method sends the HTTP request w/o payload
     * and invalid auth token
     *
     * @param client OkHttpClient Instance of HTTP client
     * @param url String Destination URL
     * @param body String Payload (JSON)
     * @return Response Response object from the server
     * @throws IOException Throws IOException
     */
    @Override
    public Response send(OkHttpClient client, String url, String body) throws IOException {
        // create simple request with authorization token
        Request request = new Request.Builder()
                .header("Authorization", "invalid_token")
                .url(url)
                .delete()
                .build();

        return client.newCall(request).execute();
    }
}
