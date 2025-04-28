package pl.marcinzygmunt.infrastucture.util;


public class SortingTimer {
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public long getElapsedTimeMillis() {
        return (endTime - startTime) / 1_000_000;
    }

    public long getElapsedTimeNanos() {
        return (endTime - startTime);
    }
}