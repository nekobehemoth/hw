package org.nekobehemoth.hw06.server.http;

import lombok.Data;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
public class HttpRequest {
    private final String method;
    private final String url;
    private final Map<String, String> headers;
    private final byte[] body;


    public static HttpRequest parseRequest(InputStream inputStream) throws IOException {
        String method = "";
        String url = "";
        Map<String, String> headers = new HashMap<>();
        int currentByte;
        int previousByte = 0;
        int firstByte = inputStream.read();

        if (firstByte == -1) {
            return null; // пустое соединение
        }
        ByteArrayOutputStream headerByteArray = new ByteArrayOutputStream();
        headerByteArray.write(firstByte);
        while ((currentByte = inputStream.read()) != -1) {
            headerByteArray.write(currentByte);
            if (currentByte == 10 && previousByte == 13) {
                if (headerByteArray.toString().endsWith("\r\n\r\n")) break;
            }
            previousByte = currentByte;
        }

        String[] headerString = headerByteArray.toString().split("\r\n");
        if (!(headerString.length == 0)) {
            method = headerString[0].split("\\s+")[0];
            url = headerString[0].split("\\s+")[1];

            for (int i = 1; i < headerString.length; i++) {
                headers.put(headerString[i].split(":")[0], headerString[i].split(":")[1].trim());
            }
        }
        byte[] body = null;
        if (headers.get("Content-Length") != null) {
            int contentLength =  Integer.parseInt(headers.get("Content-Length"));
            int bytesRead = 0;
            body = new byte[contentLength];
            while (bytesRead < contentLength) {
                int read = inputStream.read(body, bytesRead, contentLength - bytesRead);
                if (read == -1) break;
                bytesRead += read;
            }
        }

        return new HttpRequest(method, url, headers, body);
    }
}
