package com.spikes;

public class Clock {

    private long start;

    public Clock() {
        start = System.nanoTime();
    }

    public long getTime() {
        return System.nanoTime() - start;
    }

    public double getTimeInSeconds() {
        return (double)getTime() / 1000000000.0;
    }

    public void restart() {
        start = System.nanoTime();
    }

}
