package org.nekobehemoth.hw01;

import lombok.Getter;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.*;

public class TestTiming implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final String START_TIME = "start time";
    private static final String START_MEMORY = "start memory";
    private final Runtime runtime = Runtime.getRuntime();

    @Getter
    private static final Map<String, Map<String, List<Double>>> report =
            new HashMap<>();


    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        getStore(extensionContext).put(START_TIME, System.currentTimeMillis());
        getStore(extensionContext).put(START_MEMORY, runtime.totalMemory() - runtime.freeMemory());

    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        Method testMethod = extensionContext.getRequiredTestMethod();
        long starTime = getStore(extensionContext).remove(START_TIME, long.class);
        double duration = System.currentTimeMillis() - starTime;

        long initialMemory = getStore(extensionContext).remove(START_MEMORY, long.class);
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        double diffMemory = (double) ((usedMemory) - initialMemory) / (1024 * 1024);

        String implementationName = extensionContext.getDisplayName().split(" ")[0];

        report.computeIfAbsent(testMethod.getName(), k -> new HashMap<>())
                .computeIfAbsent(implementationName, k -> new ArrayList<>())
                .addAll(List.of((double) duration, (double) diffMemory));

        System.out.printf("Method [%s] took %s ms.%n",testMethod.getName(), duration);
        System.out.printf("Method [%s] used %s MB of memory.%n",testMethod.getName(), diffMemory);


    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
    }

}
