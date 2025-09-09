package org.nekobehemoth.hw06.server;

import org.nekobehemoth.hw06.executor.CustomExecutorService;
import org.nekobehemoth.hw06.server.http.HttpRequest;
import org.nekobehemoth.hw06.server.http.HttpResponse;
import org.nekobehemoth.hw06.server.httpmethodhandlers.HttpMethodHandler;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CustomWebServer {
    private Thread mainThread;
    private final int port;
    private final CustomExecutorService executorService;
    private volatile boolean running = false;
    Map<String, HttpMethodHandler> methodHandlersMap;
    private ServerSocket serverSocket;
    public ServerStatistics serverStatistics;


    public CustomWebServer(int port, int threadPoolSize, boolean useVirtualThreads){
        this.port = port;
        this.executorService = new CustomExecutorService(threadPoolSize, useVirtualThreads);
    }

    public void start() throws IOException {
        if (running) return;
        running = true;
        serverSocket = new ServerSocket(port, 1000);
        serverStatistics = new ServerStatistics();
        ControllerScan controllerScan = new ControllerScan(serverStatistics);
        methodHandlersMap = controllerScan.initializeMapping();

        mainThread = new Thread(() -> {

            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    serverStatistics.incrementTcpConnectionsCounter();
                    InputStream inputStream = clientSocket.getInputStream();
                    OutputStream outputStream = clientSocket.getOutputStream();

                    executorService.execute(() -> {
                        try {
                            HttpRequest httpRequest = HttpRequest.parseRequest(inputStream);
                            if (httpRequest == null) return;
                            HttpResponse httpResponse = new HttpResponse();
                            handleRequest(httpRequest, httpResponse);
                            httpResponse.send(outputStream);
                            serverStatistics.incrementHandledRequestsCount();
                        } catch (IOException e) {
                            System.out.println(e. getMessage());
                        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                                 InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } finally {
                            try {
                                clientSocket.close();
                                inputStream.close();
                                outputStream.close();
                                serverStatistics.decrementTcpConnectionsCounter();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } catch (IOException e) {
                    if (running) System.out.println("Error during stopping: " + e.getMessage());
                }
            }
        });
        mainThread.start();
    }

    public void stop() throws IOException, InterruptedException {
        running = false;
        serverSocket.close();
        executorService.shutdown();
        boolean terminated = executorService.awaitTermination(30, TimeUnit.SECONDS);
        mainThread.interrupt();
        if (terminated) System.out.println("Web server on " + port + " is stopped.");
    }

    private void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        HttpMethodHandler httpMethodHandler = methodHandlersMap.get(httpRequest.getMethod().toUpperCase());

        if (httpMethodHandler != null) {
            httpMethodHandler.handleHttpMethod(httpRequest, httpResponse);
        } else {
            httpResponse.setStatus(405).addHeader("Content-Type", "plain/text").setBody("Method Not Allowed");
        }
    }
}
