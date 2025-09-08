package org.nekobehemoth.hw06.server.httpmethodhandlers;

import org.nekobehemoth.hw06.server.http.HttpRequest;
import org.nekobehemoth.hw06.server.http.HttpResponse;

public interface HttpMethodHandler {

    void addRoute(String path, RequestHandler requestHandler);
    void handleHttpMethod(HttpRequest request, HttpResponse response);
}
