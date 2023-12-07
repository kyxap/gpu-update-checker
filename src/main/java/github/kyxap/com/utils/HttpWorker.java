package github.kyxap.com.utils;

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpWorker {
    public static String get(final String uri) {
        try {
            final URL url = new URL(uri);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            final String htmlResponse = Buffer.readStream(connection.getInputStream());

            // Read the response
            //Logger.log("Response: " + htmlResponse);

            connection.disconnect();

            return htmlResponse;

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

