package org.nekobehemoth.hw06.executor;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.nekobehemoth.hw01.TestTiming;
import org.nekobehemoth.utils.PerformanceTestUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(TestTiming.class)
public class CustomExecutorServiceTest {

    private final static int corePoolSize = 100;
    private final static int[] corePoolSizes = {10, 50, 100, 500};

    public static Stream<Arguments> executorsImplementation() {
        return Stream.of(
                Arguments.of("PlatformThreadsExecutor", new CustomExecutorService(corePoolSize, false )),
                Arguments.of("VirtualThreadsExecutor", new CustomExecutorService(corePoolSize, true)),
                Arguments.of("NativeVirtualThreadsExecutor", Executors.newFixedThreadPool(corePoolSize)),
                Arguments.of("NativeVirtualThreadsExecutorWithFixedPool", Executors.newFixedThreadPool(corePoolSize, Thread.ofVirtual().factory())),
                Arguments.of("NativeVirtualThreadsExecutorPerTask", Executors.newVirtualThreadPerTaskExecutor())
        );
    }

    public static Stream<Arguments> executorsImplementationDifferentCoreSize() {
        return Stream.of(
                Arguments.of("NativeVirtualThreadsExecutorPerTask", Executors.newVirtualThreadPerTaskExecutor()),
                Arguments.of("PlatformThreadsExecutor - 10", new CustomExecutorService(10, false )),
                Arguments.of("VirtualThreadsExecutor  - 10", new CustomExecutorService(10, true)),
                Arguments.of("NativePlatformThreadsExecutor - 10", Executors.newFixedThreadPool(10)),
                Arguments.of("NativeVirtualThreadsExecutor  - 10", Executors.newFixedThreadPool(10, Thread.ofVirtual().factory())),
                Arguments.of("PlatformThreadsExecutor - 50", new CustomExecutorService(50, false )),
                Arguments.of("VirtualThreadsExecutor - 50", new CustomExecutorService(50, true)),
                Arguments.of("NativePlatformThreadsExecutor - 50", Executors.newFixedThreadPool(50)),
                Arguments.of("NativeVirtualThreadsExecutor  - 50", Executors.newFixedThreadPool(50, Thread.ofVirtual().factory())),
                Arguments.of("PlatformThreadsExecutor - 100", new CustomExecutorService(100, false )),
                Arguments.of("VirtualThreadsExecutor - 100", new CustomExecutorService(100, true)),
                Arguments.of("NativePlatformThreadsExecutor - 100", Executors.newFixedThreadPool(100)),
                Arguments.of("NativeVirtualThreadsExecutor  - 100", Executors.newFixedThreadPool(100, Thread.ofVirtual().factory())),
                Arguments.of("PlatformThreadsExecutor - 500", new CustomExecutorService(500, false )),
                Arguments.of("VirtualThreadsExecutor - 500", new CustomExecutorService(500, true)),
                Arguments.of("NativePlatformThreadsExecutor - 500", Executors.newFixedThreadPool(500)),
                Arguments.of("NativeVirtualThreadsExecutor  - 500", Executors.newFixedThreadPool(500, Thread.ofVirtual().factory()))
        );
    }


    @ParameterizedTest(name = "{0} should works")
    @MethodSource("executorsImplementationDifferentCoreSize")
    void testPerformanceComparison(String implementation, ExecutorService executorService) {
        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
        executorService.shutdown();
        try {
            boolean terminated = executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }


    @ParameterizedTest(name = "{0} should correctly works with concurrent tasks")
    @MethodSource("executorsImplementation")
    void testConcurrentExecution(String implementation, ExecutorService executorService){
        AtomicInteger count = new AtomicInteger();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(count::getAndIncrement);
        }
        executorService.shutdown();

        try {
            boolean terminated = executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(1000, count.get());
    }



}
