package com.jiridusek.restapitester;

/**
 * Util class BearerToken returns hard-coded token for public REST API
 *
 * @author  Jiri Dusek
 * @version 1.0
 * @since   2020-09-06
 */
class BearerToken {
    private static final String TOKEN = "Bearer 81ce153978194bd073b0851f146c6a84f8d88f6106d9633d614474495fbbe604";

    private BearerToken() {}

    /**
     * Static method to get Bearer Token constant
     *
     * @return String Bearer Token
     */
    public static String getToken() {
        return TOKEN;
    }
}
