package github.kyxap.com.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cheduler {
    public static void schedule() {
        // Create a scheduled executor service with a single thread
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // Define the task to be scheduled (replace with your actual task)
        Runnable task = () -> {
            System.out.println("Executing your task at " + System.currentTimeMillis());
            // Replace the next line with the method you want to run
            // yourMethod();
        };

        // Schedule the task to run every 60 minute with an initial delay of 0 seconds
        scheduler.scheduleAtFixedRate(task, 0, 60, TimeUnit.MINUTES);

        // If you want the scheduler to keep running in the background, you may need to prevent the application from terminating
        // For a simple console application, you can add a delay or use a loop to keep it running
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Shutdown the scheduler when the application is about to exit
        scheduler.shutdown();
    }
}
