package github.kyxap.com.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CmdWorker {

    public static String runCmd(String cmdCommand) {
        try {
            // Create ProcessBuilder with the CMD command
            ProcessBuilder processBuilder = new ProcessBuilder(cmdCommand);

            // Redirect error stream to standard output
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Read the process output
            String output = getInstalledDriverVersion(process.getInputStream());

            // Wait for the process to complete
            int exitCode = process.waitFor();

            // Print the output and exit code
            //System.out.println("Command Output:\n" + output);
            //System.out.println("Exit Code: " + exitCode);


            return output;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return "Issue with your command";
    }


    private static String getInstalledDriverVersion(InputStream inputStream) throws IOException {
        final String lineMustContain = "Driver Version";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(lineMustContain)) {

                    String verOnly = line.substring(line.lastIndexOf(lineMustContain) + lineMustContain.length() + 2, line.indexOf("  ", line.indexOf(lineMustContain)));
                    System.out.printf("Installed version %s\n", verOnly);

                    return verOnly;
                }
            }
        }
        return null;
    }
}
