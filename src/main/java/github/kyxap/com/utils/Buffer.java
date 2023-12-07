package github.kyxap.com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Buffer {

    public static String readStream(final InputStream inputStream) throws IOException {
        final StringBuilder result = new StringBuilder();
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        }
        return result.toString();
    }
}
