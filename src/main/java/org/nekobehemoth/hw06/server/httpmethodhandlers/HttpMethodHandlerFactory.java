package org.nekobehemoth.hw06.server.httpmethodhandlers;

public class HttpMethodHandlerFactory {
    public static HttpMethodHandler createHttpMethodHandler(String method) {
        String httpMethod = method.toUpperCase();

        return switch (httpMethod) {
            case "POST" -> new PostMethodHandler();
            case "GET" -> new GetMethodHandler();
            default -> throw new IllegalStateException(httpMethod + " method is not supported.");
        };
    }
}
