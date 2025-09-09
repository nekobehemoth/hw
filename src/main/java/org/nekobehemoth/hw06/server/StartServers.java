package org.nekobehemoth.hw06.server;

import org.nekobehemoth.hw06.executor.CustomExecutorService;

import java.io.IOException;

public class StartServers {
    public static void main(String[] args) throws IOException, InterruptedException {

        // Test with virtual threads
        CustomWebServer virtualServer = new CustomWebServer(8080, 100, true);

        // Test with platform threads
        CustomWebServer platformServer = new CustomWebServer(8081, 100, false);

        try {

            virtualServer.start();
            platformServer.start();

            System.out.println("Servers started:");
            System.out.println("Virtual thread server: http://localhost:8080");
            System.out.println("Platform thread server: http://localhost:8081");

            // Keep servers running
            Thread.sleep(60000); // Run for 1 minute
            System.out.println("Going to stop servers.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            virtualServer.stop();
            platformServer.stop();
            System.out.println("Servers are stopped");
        }

    }
}
