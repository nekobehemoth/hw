package org.nekobehemoth.hw06.server.controller;

import lombok.Getter;
import org.nekobehemoth.hw06.server.ServerStatistics;
import org.nekobehemoth.hw06.server.annotations.Controller;
import org.nekobehemoth.hw06.server.annotations.GetMapping;
import org.nekobehemoth.hw06.server.annotations.PostMapping;
import org.nekobehemoth.hw06.server.http.HttpRequest;
import org.nekobehemoth.hw06.server.http.HttpResponse;
import org.nekobehemoth.hw06.server.httpmethodhandlers.GetMethodHandler;
import org.nekobehemoth.hw06.server.httpmethodhandlers.HttpMethodHandler;
import org.nekobehemoth.hw06.server.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MyController {
    @Getter
    public final Map<String, HttpMethodHandler> methodHandlerMap = new HashMap<>();

    ServerStatistics serverStatistics;

    public MyController() {

    }

    public MyController(ServerStatistics serverStatistics) {
        this.serverStatistics = serverStatistics;
    }


    @GetMapping("/")
    public void getHomePage(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.setStatus(200)
                .addHeader("Content-Type", "text/html")
                .setBody(Utils.getMappedUrlContent(httpRequest.getUrl()));
    }

    @PostMapping("/api/echo")
    public void postEchoApi(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setStatus(200)
                .addHeader("Content-Type", httpRequest.getHeaders().get("Content-Type"))
                .setBody(httpRequest.getBody());
    }


    @GetMapping("/api/stats")
    public void getServerStatistics(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {

        String serverStatJSON = """
                {
                    "startUpTime": "%s",
                    "UpTime:" : "%s",
                    "RequestsHandled": %d,
                    "TCPConnectionCount": %d
                }
                """;

        String statistic = String.format(serverStatJSON
                , serverStatistics.getStartUpTime(),
                serverStatistics.getUpTime(),
                serverStatistics.getHandledRequestsCount().get(),
                serverStatistics.getTcpConnectionsCounter().get());

        httpResponse.setStatus(200)
                .addHeader("Content-Type", "text/json")
                .setBody(statistic.getBytes());
    }


}
