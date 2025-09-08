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
    private volatile boolean isShutdown;
    private volatile boolean terminated = false;
    private final boolean useVirtualThreads;
    private final ReentrantLock lock = new ReentrantLock();

    private static final Runnable SHUTDOWN = () -> {};

    public CustomExecutorService(int corePoolSize, boolean useVirtualThreads) {
        this.useVirtualThreads = useVirtualThreads;
        this.activeTasks = new AtomicInteger();
        this.completedTasks = new AtomicInteger();
        this.queue = new LinkedBlockingQueue<>();
        ThreadFactory threadFactory = useVirtualThreads ? Thread.ofVirtual().factory() : Thread.ofPlatform().factory();
        for (int i = 0; i < corePoolSize; i++) {
            Worker worker = new Worker(threadFactory);
            this.workers.add(worker);
            worker.getThread().start();
        }
    }

    @Override
    public void shutdown() {
        isShutdown = true;
        lock.lock();
        try {
            for (int i = 0; i < workers.size(); i++) {
                queue.offer(SHUTDOWN);
            }
        } finally {
            lock.unlock();
        }
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
        return terminated;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long convertToNano = unit.toNanos(timeout);
        long timeoutOver = System.nanoTime() + convertToNano;

        while (System.nanoTime() < timeoutOver) {
            if (activeTasks.get() == 0 && queue.isEmpty() && isTerminated()) {
                return true;
            }
            Thread.sleep(10);
        }
        return false;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null)
            throw new NullPointerException();
        FutureTask<T> futureTask = new FutureTask<>(task);
        execute(futureTask);
        return futureTask;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        if (task == null)
            throw new NullPointerException();
        FutureTask<T> futureTask = new FutureTask<>(task, result);
        execute(futureTask);
        return futureTask;
    }

    @Override
    public Future<?> submit(Runnable task) {
        if (task == null)
            throw new NullPointerException();
        FutureTask<?> futureTask = new FutureTask<>(task, null);
        execute(futureTask);
        return futureTask;
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
    public void execute(Runnable task) {
        if (task == null)
            throw new NullPointerException();
        if (isShutdown) throw new RejectedExecutionException("Shutting down, pool size = "
                + queue.size()
                + ", active threads = " + activeTasks);
        queue.add(task);
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
            try {
                while (true) {
                    Runnable task = null;
                    try {
                        if (isShutdown() && queue.isEmpty()) break;
                        task = queue.take();
                        if (task == SHUTDOWN) {
                            break;
                        }
                    } catch (InterruptedException e) {
                        continue;
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
            } finally {
                lock.lock();
                try {
                    workers.remove(this);
                    if (workers.isEmpty()) {
                        terminated = true;
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
