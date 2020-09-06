package com.jiridusek.restapitester;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import java.io.IOException;

/**
 * HttpInterface interface
 *
 * @author  Jiri Dusek
 * @version 1.0
 * @since   2020-09-06
 */
public interface HttpInterface {
    Response send(OkHttpClient client, String url, String body) throws IOException;
}
