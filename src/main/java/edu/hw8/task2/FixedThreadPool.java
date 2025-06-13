package edu.hw8.task2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import lombok.Getter;

public class FixedThreadPool implements ThreadPool {
    @Getter private final int threadsCount;
    private final Thread[] threads;
    private final BlockingQueue<Runnable> taskQueue;
    private volatile boolean isRunning = true;

    public FixedThreadPool(int threadsCount) {
        this.threadsCount = threadsCount;
        this.threads = new Thread[threadsCount];
        this.taskQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void start() {
        for (int i = 0; i < getThreadsCount(); i++) {
            threads[i] = new Thread(() -> {
                while (isRunning() || !taskQueue.isEmpty()) {
                    try {
                        Runnable task = taskQueue.take();
                        task.run();
                    } catch (InterruptedException e) {
                        if (!isRunning()) {
                            break;
                        }
                    }
                }
            });
            threads[i].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (!taskQueue.offer(runnable)) {
            throw new RejectedExecutionException("Task queue is full");
        }
    }


    @Override
    public void close() throws Exception {
        setRunning(false);
        for (Thread thread : threads) {
            thread.interrupt();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

}
