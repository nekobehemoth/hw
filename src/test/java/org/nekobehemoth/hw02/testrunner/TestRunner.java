package org.nekobehemoth.hw02.testrunner;

import lombok.SneakyThrows;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


public class TestRunner {

    private static int classCount = 0;
    private static int testMethodCount = 0;
    private static int successMethodCount = 0;
    private static int errorMethodCount = 0;

    public static void main(String[] args) throws ClassNotFoundException {
        runTestRunner("org.nekobehemoth.hw02.testrunner");
    }
    @SneakyThrows
    public static void runTestRunner(String packageName) throws ClassNotFoundException {
        System.out.println("==Custom test runner results==");
        List<Class<?>> result = findClassesByPackageName(packageName);
        classCount = result.size();
        System.out.println("Package: " + packageName);
        System.out.println("Classes scanned: " + classCount);
        System.out.println("______________");
        Map<Class<?>, Map<Method, List<SupportedAnnotations>>> classMethodMap  = new HashMap<>();
        for (Class<?> c : result) {
            Method[] methods = c.getDeclaredMethods();
            Map<Method, List<SupportedAnnotations>> annotationsMethodMap = getMethodAnnotationsMap(methods);
            if (!annotationsMethodMap.isEmpty()) classMethodMap.put(c, annotationsMethodMap);
        }
        Long startTime = System.currentTimeMillis();
        if (!classMethodMap.isEmpty()) annotationHandler(classMethodMap);
        Long endTime = System.currentTimeMillis();
        double duration = endTime - startTime;
        System.out.println("______________");
        System.out.println("Tests results: ");
        System.out.println("Total tests: " + testMethodCount);
        System.out.println("Passed: " + successMethodCount);
        System.out.println("Failed: " + errorMethodCount);
        System.out.println("Execution time: " + duration);
        System.out.printf("Success rate: %.2f %%",  ((double) successMethodCount / testMethodCount) * 100);
    }

    private static Map<Method, List<SupportedAnnotations>> getMethodAnnotationsMap(Method[] methods) {
        Map<Method, List<SupportedAnnotations>> mapping = new HashMap<>();
        for(Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            List<SupportedAnnotations> methodAnnotations = new ArrayList<>();
            for (Annotation annotation : annotations) {
                SupportedAnnotations supportedAnnotations = getSupportedAnnotation(annotation);
                methodAnnotations.add(supportedAnnotations);
            }
            if (!methodAnnotations.isEmpty()) mapping.put(method, methodAnnotations);
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

    private static SupportedAnnotations getSupportedAnnotation(Annotation annotation) {
        String annotationName = getAnnotationName(annotation.toString());
        for (SupportedAnnotations suppAnnotation : SupportedAnnotations.values()) {
            if (annotationName.equals(suppAnnotation.getAnnotation())) return suppAnnotation;
        }
        return null;
    }

    @SneakyThrows
    private static void processMethod(Class<?> cl, Method method, Object... methodArgs) {
        int methodArgsCount = method.getParameters().length;
        if (methodArgsCount != 0 && methodArgs.length == 0)
            throw new Exception(String.format("Method %s require %d arguments.", method.getName(), methodArgsCount));
        Constructor<?> constructor = cl.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object classInstance = constructor.newInstance(methodArgs);
        method.setAccessible(true);
        method.invoke(classInstance);
    }

    private static void checkExecution(Runnable task, String methodName){
        try {
            task.run();
            System.out.println("✓ " + methodName);
            successMethodCount += 1;
        } catch (Throwable e) {
            String errorMessage = e.getMessage() != null ? e.getMessage() : e.getCause().getMessage();
            System.out.println("x " + methodName + " - " + errorMessage);
            errorMethodCount +=1;
        }
    }

    private static Set<Method> getMethodWithAnnotation(Map<Method, List<SupportedAnnotations>> annotationsMethodMap, SupportedAnnotations annotations) {
        return annotationsMethodMap.entrySet().stream()
                .filter(methodListEntry ->
                        methodListEntry.getValue().contains(annotations))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private static void annotationHandler(Map<Class<?>, Map<Method, List<SupportedAnnotations>>> classMethodMap) {
        classMethodMap.forEach((cl, annotationsMethodMap) -> {
            Set<Method> beforeEachMethods = getMethodWithAnnotation(annotationsMethodMap, SupportedAnnotations.BEFORE_EACH);
            Set<Method> testMethods = getMethodWithAnnotation(annotationsMethodMap, SupportedAnnotations.TEST);
            Set<Method> afterEachMethods = getMethodWithAnnotation(annotationsMethodMap, SupportedAnnotations.AFTER_EACH);
            for (Method testMethod : testMethods) {
                beforeEachMethods.forEach((method) -> processMethod(cl,method));
                checkExecution(() -> processMethod(cl,testMethod), testMethod.getName());
                afterEachMethods.forEach((method) -> processMethod(cl,method));
            }
            testMethodCount += testMethods.size();
        });
    }
}