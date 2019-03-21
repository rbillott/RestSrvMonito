package test;

import java.time.Instant;

public class ResourceCheckpoint {

    private Instant instant = Instant.now();

    private long usedHeap = 0;
    private long usedNonHeap = 0;

    private double processCpuLoad = 0;
    private double systemCpuLoad = 0;

    private int daemonThreadCount = 0;
    private int threadCount = 0;

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public Instant getInstant() {
        return instant;
    }

    public long getUsedHeap() {
        return usedHeap;
    }

    public void setUsedHeap(long usedHeap) {
        this.usedHeap = usedHeap;
    }

    public long getUsedNonHeap() {
        return usedNonHeap;
    }

    public void setUsedNonHeap(long usedNonHeap) {
        this.usedNonHeap = usedNonHeap;
    }

    public double getProcessCpuLoad() {
        return processCpuLoad;
    }

    public void setProcessCpuLoad(double processCpuLoad) {
        this.processCpuLoad = processCpuLoad;
    }

    public double getSystemCpuLoad() {
        return systemCpuLoad;
    }

    public void setSystemCpuLoad(double systemCpuLoad) {
        this.systemCpuLoad = systemCpuLoad;
    }

    public int getDaemonThreadCount() {
        return daemonThreadCount;
    }

    public void setDaemonThreadCount(int daemonThreadCount) {
        this.daemonThreadCount = daemonThreadCount;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

}
