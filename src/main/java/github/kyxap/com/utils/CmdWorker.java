package github.kyxap.com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CmdWorker {

    public static String runCmd(final String cmdCommand) {
        try {
            // Create ProcessBuilder with the CMD command
            final ProcessBuilder processBuilder = new ProcessBuilder(cmdCommand);

            // Redirect error stream to standard output
            processBuilder.redirectErrorStream(true);

            // Start the process
            final Process process = processBuilder.start();

            // Read the process output
            final String output = getInstalledDriverVersion(process.getInputStream());

            // Wait for the process to complete
            final int exitCode = process.waitFor();

            return output;

        } catch (final IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return "Issue with your command";
    }

    private static String getInstalledDriverVersion(final InputStream inputStream) throws IOException {
        final String lineMustContain = "Driver Version";
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(lineMustContain)) {

                    final String verOnly = line.substring(line.lastIndexOf(lineMustContain) + lineMustContain.length() + 2, line.indexOf("  ", line.indexOf(lineMustContain)));
                    Logger.log(String.format("Installed version %s", verOnly));

                    return verOnly;
                }
            }
        }
        return null;
    }
}
