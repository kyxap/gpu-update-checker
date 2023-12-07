package github.kyxap.com.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static github.kyxap.com.utils.DateAndTime.getCurDateTime;

public class Logger {

    private static final String LOG_FILE_PATH = "debug.log";
    private static boolean debug = true;
    private static boolean devOutput = true;

    public static void log(final String message) {
        if (debug) {
            try (final FileWriter f = new FileWriter(LOG_FILE_PATH, true);
                 final BufferedWriter b = new BufferedWriter(f);
                 final PrintWriter p = new PrintWriter(b)) {

                p.println(formatted(message));

            } catch (final IOException i) {
                i.printStackTrace();
            }
        }

        if (devOutput) {
            System.out.println(formatted(message));
        }
    }

    private static String formatted(final String msg) {
        return String.format("[%s] %s", getCurDateTime(), msg);
    }

    // not the best way to do it, but ok for simple stuff
    public static void setLoggerOutput(final boolean debugChange, final boolean devOutputChange) {
        debug = debugChange;
        devOutput = devOutputChange;
    }
}

