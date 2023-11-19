package github.kyxap.com.utils;

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpWorker {
    public static String get(String uri) {
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            String htmlResponse = Buffer.readStream(connection.getInputStream());

            // Read the response
            //Logger.log("Response: " + htmlResponse);

            connection.disconnect();

            return htmlResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

