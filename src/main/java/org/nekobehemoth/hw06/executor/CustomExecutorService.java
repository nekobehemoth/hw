package org.nekobehemoth.hw06.executor;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class CustomExecutorService implements ExecutorService {

    BlockingQueue<Runnable> queue;
    private final Set<Worker> workers = new HashSet<>();
    private final AtomicInteger activeTasks;
    private final AtomicInteger completedTasks;
    private boolean isShutdown;
    private boolean useVirtualThreads;
    private final ReentrantLock lock = new ReentrantLock();

    private CustomExecutorService(int corePoolSize) {
        activeTasks = new AtomicInteger();
        completedTasks = new AtomicInteger();
        queue = new LinkedBlockingQueue<>();
        ThreadFactory threadFactory = Thread.ofPlatform().factory();
        for (int i = 0; i < corePoolSize; i++) {
            Worker worker = new Worker(threadFactory);
            workers.add(worker);
            worker.getThread().start();
        }
    }

    private CustomExecutorService(boolean useVirtualThreads) {
        this.useVirtualThreads = useVirtualThreads;
        activeTasks = new AtomicInteger();
        completedTasks = new AtomicInteger();
        queue = new LinkedBlockingQueue<>();
        ThreadFactory threadFactory = Thread.ofVirtual().factory();
        Worker worker = new Worker(threadFactory);
        worker.getThread().start();
    }

    public static CustomExecutorService newFixedThreadPool(int corePoolSize) {
        return new CustomExecutorService(corePoolSize);
    }

    public static CustomExecutorService newVirtualThreadPerTaskExecutor() {
        return new CustomExecutorService(true);
    }

    @Override
    public void shutdown() {
        isShutdown = true;
    }

    @Override
    public List<Runnable> shutdownNow() {
        return List.of();
    }

    @Override
    public boolean isShutdown() {
        return isShutdown;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long convertToNano = unit.toNanos(timeout);
        long timeoutOver = System.nanoTime() + convertToNano;
        while (System.nanoTime() < timeoutOver) {
            if (activeTasks.get() == 0 && queue.isEmpty()) {
                return true;
            }
            Thread.sleep(10);
        }
        return false;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return null;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return new FutureTask<T>(task, result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return new FutureTask<Object>(task, null);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return List.of();
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return List.of();
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public void execute(Runnable command) {
        if (isShutdown && activeTasks.get() > 0) throw new RejectedExecutionException("Shutting down, pool size = "
                + queue.size()
                + ", active threads = " + activeTasks);
        queue.add(command);
    }


    private class Worker implements Runnable {

        private ThreadFactory threadFactory;

        @Getter
        private final Thread thread;

        Worker(ThreadFactory threadFactory) {
            this.threadFactory = threadFactory;
            this.thread = threadFactory.newThread(this);
        }

        @Override
        public void run() {
            while (true) {
                Runnable task = null;
                lock.lock();
                try {
                    if (isShutdown() && queue.isEmpty()) break;
                    task = queue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
                activeTasks.incrementAndGet();
                try {
                    if (useVirtualThreads) {
                        Runnable finalTask = task;
                        threadFactory.newThread(() -> {
                            try {
                                finalTask.run();
                            } finally {
                                completedTasks.incrementAndGet();
                                activeTasks.decrementAndGet();
                            }
                        }).start();
                    } else {
                        task.run();
                    }
                } finally {
                    if (!useVirtualThreads) {
                        completedTasks.incrementAndGet();
                        activeTasks.decrementAndGet();
                    }
                }
            }
        }
    }
}
