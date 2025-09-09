package org.nekobehemoth.hw06.server.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    public int status = 200;
    public Map<String, String> headers = new HashMap<>();
    public InputStream streamBody;
    public byte[] body;

    public final String HTTP_VERSION = "HTTP/1.1";
    private final String LINE_DELIMITER = "\r\n";


    public HttpResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public HttpResponse addHeader(String header, String value) {
        headers.put(header, value);
        return this;
    }

    public HttpResponse setBody(byte[] body) {
        this.body = body;
        addHeader("Content-Length", String.valueOf(body.length));
        return this;
    }

    public HttpResponse setBody(String body) {
        this.body = body.getBytes();
        addHeader("Content-Length", String.valueOf(body.length()));
        return this;
    }

    public HttpResponse setBody(InputStream inputStream, String contentLength) throws IOException {
        this.body = new byte[0];
        addHeader("Content-Length", contentLength);
        this.streamBody = inputStream;
        return this;
    }

    public void send(OutputStream outputStream) throws IOException {
        String fistHeaderLine = HTTP_VERSION + " " + status + " " + getStatusMessage(status) + LINE_DELIMITER;
        outputStream.write(fistHeaderLine.getBytes());

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String headerLine = entry.getKey() + ": " + entry.getValue() + LINE_DELIMITER;
            outputStream.write(headerLine.getBytes());
        }

        outputStream.write(LINE_DELIMITER.getBytes());
        if (body != null) outputStream.write(body);
        if (body == null && streamBody != null) {
            streamBody.transferTo(outputStream);
        }
        outputStream.flush();
    }

    public HttpResponse setContentType(String url) {
        String[] urlParts = url.split("\\.");
        String extension = urlParts[urlParts.length - 1];
        String contentType = switch (extension) {
            case "html" -> "text/html";
            case "css" -> "text/css";
            case "js" -> "application/javascript";
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            default -> "text/plain";
        };
        addHeader("Content-Type", contentType);
        return this;

    }

    private String getStatusMessage(int status) {
        return switch (status) {
            case 200 -> "OK";
            case 201 -> "Created";
            case 302 -> "Found";
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Not Found";
            case 500 -> "Internal Server Error";
            default -> "";
        };

    }
}

