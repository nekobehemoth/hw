package org.nekobehemoth.hw05.decorators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.nekobehemoth.hw01.CustomList;
import org.nekobehemoth.hw01.TestTiming;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(TestTiming.class)
class CustomListDecoratorsTest {

    ExecutorService executorService;

    private final static int elementsAmounts = 1_000_000;

    private static Stream<Arguments> treadSafeCustomListImpl() {
        return Stream.of(
                Arguments.of("SynchronizedCustomList", new SynchronizedCustomListDecorator<>(new CustomList<>())),
                Arguments.of("LockedCustomList", new LockedCustomListDecorator<>(new CustomList<>()))
        );
    }

    @BeforeEach
    void beforeEach() {
        executorService = Executors.newVirtualThreadPerTaskExecutor();
    }

    @ParameterizedTest(name = "{0} should return twice of elementsAmounts when two thread adding")
    @MethodSource("treadSafeCustomListImpl")
    void addMillionElementsWithTwoThread(String implName, List<Integer> listImpl ) {

        Future<?> t1 = executorService.submit(fillCustomList(listImpl));
        Future<?> t2 = executorService.submit(fillCustomList(listImpl));

        try {
            t1.get();
            t2.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        assertEquals(elementsAmounts * 2, listImpl.size());
    }

    @ParameterizedTest(name = "{0} should return twice of elementsAmounts when two thread adding")
    @MethodSource("treadSafeCustomListImpl")
    void addMillionElementsOneHundredTimeWithTwoThread(String implName, List<Integer> listImpl ) {

        for (int i = 0; i < 10; i++) {
            Future<?> t1 = executorService.submit(fillCustomList(listImpl));
            Future<?> t2 = executorService.submit(fillCustomList(listImpl));

            try {
                t1.get();
                t2.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        assertEquals(elementsAmounts * 20, listImpl.size());
    }


    private Runnable fillCustomList(List<Integer> list) {
        return () -> {
            for(int i = 0; i < elementsAmounts; i++) {
                list.add(i);
            }
        };
    }

}