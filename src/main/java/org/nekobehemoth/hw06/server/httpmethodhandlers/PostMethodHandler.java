package org.nekobehemoth.hw06.server.httpmethodhandlers;

import org.nekobehemoth.hw06.server.http.HttpRequest;
import org.nekobehemoth.hw06.server.http.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PostMethodHandler implements HttpMethodHandler {

    private Map<String, RequestHandler> routes = new HashMap<>();


    public void addRoute(String path, RequestHandler requestHandler)  {
        routes.put(path, requestHandler);
    }
    @Override
    public void handleHttpMethod(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestHandler handler = routes.get(httpRequest.getUrl());
        if (handler != null) {
            try {
                handler.handleRequest(httpRequest, httpResponse);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            httpResponse.setStatus(404).addHeader("Content-Type", "text/plain").setBody("Not Found");
        }
    }
}
