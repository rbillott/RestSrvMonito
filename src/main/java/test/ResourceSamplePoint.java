package test;

public class ResourceSamplePoint {
    private int count = 0;
    private ResourceCheckpoint avg;
    private ResourceCheckpoint max;

    public ResourceSamplePoint() {
        this.avg = new ResourceCheckpoint();
        this.max = new ResourceCheckpoint();
    }

    public ResourceSamplePoint(ResourceCheckpoint point) {
        count = 1;
        this.avg = point;
        this.max = point;
    }

    public ResourceCheckpoint getMax() {
        return max;
    }

    public ResourceCheckpoint getAvg() {
        return avg;
    }

    public void add(ResourceCheckpoint other) {
        ++count;

        this.avg.setInstant(other.getInstant());
        this.avg.setThreadCount(this.avg.getThreadCount() + other.getThreadCount());
        this.avg.setDaemonThreadCount(this.avg.getDaemonThreadCount() + other.getDaemonThreadCount());
        this.avg.setProcessCpuLoad(this.avg.getProcessCpuLoad() + other.getProcessCpuLoad());
        this.avg.setSystemCpuLoad(this.avg.getSystemCpuLoad() + other.getSystemCpuLoad());
        this.avg.setUsedNonHeap(this.avg.getUsedNonHeap() + other.getUsedNonHeap());
        this.avg.setUsedHeap(this.avg.getUsedHeap() + other.getUsedHeap());

        this.max.setInstant(other.getInstant());
        if(other.getThreadCount() > this.max.getThreadCount())
            this.max.setThreadCount(other.getThreadCount());

        if(other.getDaemonThreadCount() > this.max.getDaemonThreadCount())
            this.max.setDaemonThreadCount(other.getDaemonThreadCount());

        if(other.getProcessCpuLoad() > this.max.getProcessCpuLoad())
            this.max.setProcessCpuLoad(other.getProcessCpuLoad());

        if(other.getSystemCpuLoad() > this.max.getSystemCpuLoad())
            this.max.setSystemCpuLoad(other.getSystemCpuLoad());

        if(other.getUsedNonHeap() > this.max.getUsedNonHeap())
            this.max.setUsedNonHeap(other.getUsedNonHeap());

        if(other.getUsedHeap() > this.max.getUsedHeap())
            this.max.setUsedHeap(other.getUsedHeap());
    }

    public void calculateAverage() {
        this.avg.setDaemonThreadCount(avg.getDaemonThreadCount()/count);
        this.avg.setProcessCpuLoad(avg.getProcessCpuLoad()/count);
        this.avg.setSystemCpuLoad(avg.getSystemCpuLoad()/count);
        this.avg.setThreadCount(avg.getThreadCount()/count);
        this.avg.setUsedHeap(avg.getUsedHeap()/count);
        this.avg.setUsedNonHeap(avg.getUsedNonHeap()/count);
    }
}
