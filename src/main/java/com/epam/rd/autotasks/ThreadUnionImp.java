package com.epam.rd.autotasks;

import java.util.ArrayList;
import java.util.List;

public class ThreadUnionImp implements ThreadUnion{
    private String name;
    private int totalSize;
    private boolean isShutdown;
    private List<Thread> thread=new ArrayList<>();
    protected List<FinishedThreadResult> finished = new ArrayList<>();

    public ThreadUnionImp(String name){
        this.name=name;
    }
    @Override
    public int totalSize() {
        return thread.size();
    }

    @Override
    public int activeSize() {
        return (int) thread.stream().filter(Thread::isAlive).count();
    }

    @Override
    public void shutdown() {
        thread.forEach(Thread::interrupt);
        isShutdown=true;
    }

    @Override
    public boolean isShutdown() {
        return isShutdown;
    }

    @Override
    public void awaitTermination() {
        for (Thread threads : thread) {
            try {
                if (threads.isAlive()) {
                    threads.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return isShutdown&&activeSize()==0;
    }

    @Override
    public List<FinishedThreadResult> results() {
        return finished;
    }

    @Override
    public synchronized Thread newThread(Runnable r) {
        if (isShutdown) {
            throw new IllegalStateException("Thread Union is shutdown");
        }
        Thread thread1 = new Thread(r) {
            @Override
            public void run() {
                super.run();
                finished.add(new FinishedThreadResult(this.getName()));
            }
        };
        thread1.setName(String.format("%s-worker-%d", name, totalSize++));
        thread1.setUncaughtExceptionHandler((t, e) -> finished.add(new FinishedThreadResult(t.getName(), e)));
        thread.add(thread1);
        return thread1;
    }
}
