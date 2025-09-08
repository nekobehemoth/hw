package org.nekobehemoth.hw06.server.httpmethodhandlers;


import org.nekobehemoth.hw06.server.http.HttpRequest;
import org.nekobehemoth.hw06.server.http.HttpResponse;

import java.io.IOException;

@FunctionalInterface
public interface RequestHandler {
    void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
