package github.kyxap.com.utils;

import static github.kyxap.com.utils.DateAndTime.getCurDateTime;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {

    private static final String LOG_FILE_PATH = "debug.log";
    private static boolean debug = true;
    private static boolean devOutput = true;

    public static void log(String message) {
        if (debug) {
            try (FileWriter f = new FileWriter(LOG_FILE_PATH, true);
                 BufferedWriter b = new BufferedWriter(f);
                 PrintWriter p = new PrintWriter(b)) {

                p.println(formatted(message));

            } catch (IOException i) {
                i.printStackTrace();
            }
        }

        if (devOutput) {
            System.out.println(formatted(message));
        }
    }

    private static String formatted(String msg) {
        return String.format("[%s] %s", getCurDateTime(), msg);
    }

    // not the best way to do it, but ok for simple stuff
    public static void setLoggerOutput(boolean debugChange, boolean devOutputChange) {
        debug = debugChange;
        devOutput = devOutputChange;
    }
}

