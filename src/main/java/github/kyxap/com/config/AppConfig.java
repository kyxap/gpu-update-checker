package github.kyxap.com.config;

import github.kyxap.com.utils.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class AppConfig {

    private static final String PROPERTIES_FILE = "config.properties";

    private static final Properties properties = new Properties();

    public static void loadProperties() throws IOException {
        try (final InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(input);
        } catch (final FileNotFoundException e) {
            // Handle the case when the file doesn't exist
            // You may want to create a default configuration here
            Logger.log("Default config does not exist, creating new one...");
            createDefaultProperties();
        }
    }

    private static void checkForUpdates() throws IOException {
        final long lastModified = propertiesFileLastModified();
        if (lastModified > 0 && lastModified > propertiesFileLastLoaded) {
            // Reload the properties if the file has been modified
            Logger.log("Reloading properties...");
            loadProperties();
            propertiesFileLastLoaded = lastModified;
        }
    }

    private static long propertiesFileLastModified() {

        final File file = new File(PROPERTIES_FILE);
        return file.lastModified();
    }

    private static long propertiesFileLastLoaded = System.currentTimeMillis();

    private static void createDefaultProperties() {
        // Create and set default properties

        properties.setProperty("uri", "https://www.nvidia.com/Download/processFind.aspx?psid=127&pfid=995&osid=135&lid=1&whql=1&lang=en-us&ctk=0&qnfslb=00&dtcid=1");
        properties.setProperty("debug", "true");
        properties.setProperty("dev-output", "true");

        // Save the default properties to the file
        saveProperties();
    }

    private static void saveProperties() {
        try (final OutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(output, "Application Configuration");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    // Accessor methods for getting and setting properties
    public static String getProperty(final String key) {
        return properties.getProperty(key);
    }

    public static void setProperty(final String key, final String value) {
        properties.setProperty(key, value);
        // Save the properties whenever a change is made
        saveProperties();
    }
}
