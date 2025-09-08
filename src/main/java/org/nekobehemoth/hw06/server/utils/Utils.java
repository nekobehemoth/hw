package org.nekobehemoth.hw06.server.utils;

import org.nekobehemoth.hw06.server.CustomWebServer;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Utils {


    public static byte[] getUrlContent(String url) throws IOException {

        String basePath = "static/" + url.replace("^/","").replace("%20", " ");
        try(InputStream file = CustomWebServer.class.getClassLoader().getResourceAsStream(basePath)){
            if (file != null) return file.readAllBytes();
        }
        throw new FileNotFoundException("File not found");
    }

    public static byte[] getMappedUrlContent(String url) throws IOException {

        if (Objects.equals(url, "/")) url = "index.html";
        if (! url.contains("html") && !url.equals("index.html")) url = url + ".html";

        String basePath = "static/" + url.replace("^/","")
                .replace("%20", " ");
        try(InputStream file = CustomWebServer.class.getClassLoader().getResourceAsStream(basePath)){
            if (file != null) return file.readAllBytes();
        }
        throw new FileNotFoundException("File not found");
    }
}

