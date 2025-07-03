package org.nekobehemoth.hw02.testrunner;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class TestRunner {


    private static final String BEFORE_EACH = "BeforeEach";
    private static final String AFTER_EACH = "AfterEach";

    public static void main(String[] args) throws ClassNotFoundException {

        runTestRunner("org.nekobehemoth.hw02.testrunner");
    }
    @SneakyThrows
    public static void runTestRunner(String packageName) throws ClassNotFoundException {
        List<Class<?>> result = findClassesByPackageName(packageName);
        for (Class<?> c : result) {
            Method[] methods = c.getDeclaredMethods();
            Map<String, Method> eachAnnotations = getEachAnnotations(methods);
            for (Method method : methods) {
                if (eachAnnotations.containsKey(BEFORE_EACH)) processMethod(c, eachAnnotations.get(BEFORE_EACH));
                annotationHandler(c, method);
                if (eachAnnotations.containsKey(AFTER_EACH)) processMethod(c, eachAnnotations.get(AFTER_EACH));
            }
        }
    }

    private static Map<String, Method> getEachAnnotations(Method[] methods) {
        Map<String, Method> mapping = new HashMap<>();
        for(Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            if (annotations.length == 0) continue;
            List<String> annotationsNames = new ArrayList<>();
            for (Annotation annotation : annotations) {
                String annotationName = getAnnotationName(annotation.toString());
                if (annotationName.equalsIgnoreCase(BEFORE_EACH)
                        || annotationName.equalsIgnoreCase(AFTER_EACH)) {
                    mapping.put(annotationName, method);
                }
            }
        }
        return mapping;
    }


    private static List<Class<?>> findClassesByPackageName(String packageName) throws ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        File packageDir = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());

        if (packageDir.exists() && packageDir.isDirectory()) {
            File[] classes = packageDir.listFiles(file -> file.getName().endsWith("class"));
            if (classes != null) {
                for (File classFile : classes) {
                    String className = classFile.getName().replace(".class", "");
                    String fullClassName = packageName + "." + className;
                    classList.add(Class.forName(fullClassName));
                }
            }

        }
        return classList;
    }

    private static String getAnnotationName(String annotation) {
        String cleanedAnnotation = annotation.replaceAll("\\(.*\\)", "");
        return cleanedAnnotation.substring(cleanedAnnotation.lastIndexOf(".") + 1);
    }

    private static List<String> getAnnotations(Method method) {
        List<String> annotationsName = new ArrayList<>();
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            annotationsName.add(getAnnotationName(annotation.toString()));
        }
        return annotationsName;
    }

    private static void processMethod(Class<?> cl, Method method, Object... methodArgs) throws Exception {
        int methodArgsCount = method.getParameters().length;
        if (methodArgsCount != 0 && methodArgs.length == 0)
            throw new Exception(String.format("Method %s require %d arguments.", method.getName(), methodArgsCount));
        try {
            Constructor<?> constructor = cl.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object classInstance = constructor.newInstance(methodArgs);
            method.setAccessible(true);
            method.invoke(classInstance);
            System.out.println("✓ " + method.getName());
        }  catch (AssertionError | IllegalAccessException | InvocationTargetException e) {
            System.out.println("x " + method.getName() + " : " + e.getCause().getMessage());
        } catch (NoSuchMethodException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void annotationHandler(Class<?> cl, Method method, Object... methodArgs) throws Exception {
        List<String> methodAnnotations = getAnnotations(method);
        for (String annotation : methodAnnotations) {
            switch (annotation.toLowerCase()) {
                case "test":
                    processMethod(cl, method);
                    break;
                case "parameterizedtest":
                    processMethod(cl, method, methodArgs);
            }
        }
    }
}