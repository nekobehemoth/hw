package org.nekobehemoth.hw06.server.httpmethodhandlers;

import org.nekobehemoth.hw06.server.http.HttpRequest;
import org.nekobehemoth.hw06.server.http.HttpResponse;
import org.nekobehemoth.hw06.server.utils.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetMethodHandler implements HttpMethodHandler{

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
                return;
            } catch (FileNotFoundException e) {
                httpResponse.setStatus(404).setBody("404 Page Not Found");
            } catch (IOException e) {
                httpResponse.setStatus(500).setBody("Internal Server Error: " + e.getMessage());
                return;
            }
        }

        String resource = httpRequest.getUrl().replaceFirst("^/", "");
        try {
            byte[] content = Utils.getUrlContent(resource);
            httpResponse.setStatus(200)
                    .setContentType(resource)
                    .setBody(content);
        } catch (IOException e) {
            httpResponse.setStatus(404).setBody("Not Found");
        }

    }

}
