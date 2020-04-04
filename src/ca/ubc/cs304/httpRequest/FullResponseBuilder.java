package ca.ubc.cs304.httpRequest;

import java.io.IOException;
import java.net.HttpURLConnection;

public class FullResponseBuilder {
    public static String getFullResponse(HttpURLConnection con) throws IOException {
        StringBuilder fullResponseBuilder = new StringBuilder();

        // read status and message

        // read headers

        // read response content

        return fullResponseBuilder.toString();
    }
}
