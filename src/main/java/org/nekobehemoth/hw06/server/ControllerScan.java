package org.nekobehemoth.hw06.server;

import lombok.SneakyThrows;
import org.nekobehemoth.hw06.server.annotations.Controller;
import org.nekobehemoth.hw06.server.annotations.HttpMethod;
import org.nekobehemoth.hw06.server.httpmethodhandlers.HttpMethodHandler;
import org.nekobehemoth.hw06.server.httpmethodhandlers.HttpMethodHandlerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class ControllerScan {

    ServerStatistics serverStatistics;

    public ControllerScan() {};


    public ControllerScan(ServerStatistics serverStatistics) {
        this.serverStatistics = serverStatistics;
    }

    @SneakyThrows
    public Map<String, HttpMethodHandler>  initializeMapping()  {
        Map<String, HttpMethodHandler> methodHandlerMap = new HashMap<>();
        Set<Class<?>> controllers = findClassesAnnotatedWith(Controller.class);

        for (Class<?> cl : controllers) {
            Object controllerInstance;
            if (serverStatistics != null) {
                controllerInstance = cl.getDeclaredConstructor(ServerStatistics.class).newInstance(serverStatistics);
            } else {
                controllerInstance = cl.getDeclaredConstructor().newInstance();
            }
            for (Method method : cl.getMethods()) {
                for (Annotation annotation : method.getAnnotations()) {
                    Class<? extends Annotation> annotationCass = annotation.annotationType();
                    HttpMethod httpAnnotation = annotationCass.getAnnotation(HttpMethod.class);
                    if (httpAnnotation != null) {
                        String httpMethod = httpAnnotation.value();
                        if (method.isAnnotationPresent(annotationCass)) {
                            Annotation annotationInstance = method.getAnnotation(annotationCass);
                            Method value = annotationCass.getMethod("value");
                            String path = (String) value.invoke(annotationInstance);
                            HttpMethodHandler methodHandler = methodHandlerMap.get(httpMethod);
                            if (methodHandler == null) {
                                methodHandler = HttpMethodHandlerFactory.createHttpMethodHandler(httpMethod);
                            }
                            methodHandler.addRoute(path, (httpRequest, httpResponse) -> {
                                try {
                                    method.invoke(controllerInstance, httpRequest, httpResponse);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            methodHandlerMap.put(httpMethod, methodHandler);
                        }
                    }
                }
            }
        }
        return methodHandlerMap;
    }

    private Set<Class<?>> findClassesAnnotatedWith(Class<? extends Annotation> annotationClass) throws IOException, ClassNotFoundException {
        Set<Class<?>> controllers = new HashSet<>();
        String packageName = this.getClass().getPackageName();
        Set<Class<?>>  classes = getPackageClasses(packageName.replace(".","/"));

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                controllers.add(clazz);
            }
        }
        return controllers;
    }

    private Set<Class<?>> getPackageClasses(String packageName) throws IOException {
        Set<Class<?>> classes = new HashSet<>();
        Stack<String> stack = new Stack<>();
        stack.push(packageName);
        while (!stack.isEmpty()) {
            String currentPackage = stack.pop();
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(currentPackage);
            if (stream == null) continue;
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while((line = reader.readLine()) != null) {
                String resourceURL = currentPackage + "/" + line;
                if (resourceURL.endsWith(".class")) {
                    try {
                        Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(resourceURL.replace("/", ".").replace(".class", ""));
                        classes.add(clazz);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                URL pkg = getClass().getClassLoader().getResource(resourceURL.replace(".","/"));
                if (pkg != null) stack.push(resourceURL);
            }
        }
        return classes;
    }
}
