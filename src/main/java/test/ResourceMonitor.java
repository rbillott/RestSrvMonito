package test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.management.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ResourceMonitor implements Runnable {

    protected static final Logger logger = LogManager.getLogger(ResourceMonitor.class);

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    List<ResourceCheckpoint> resourceCheckpointList = new ArrayList<ResourceCheckpoint>();


    private long heapMemoryMax;
    private long nonHeapMemoryMax;

    public ResourceMonitor() {
        scheduler.scheduleAtFixedRate(this, 5, 15, TimeUnit.SECONDS);

        MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
        heapMemoryMax = mbean.getHeapMemoryUsage().getMax();
        nonHeapMemoryMax = mbean.getNonHeapMemoryUsage().getMax();

        com.sun.management.OperatingSystemMXBean sbean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

       sbean.getProcessCpuLoad();
        sbean.getSystemCpuLoad();
    }

    public long getHeapMemoryMax() {
        return heapMemoryMax;
    }

    public long getNonHeapMemoryMax() {
        return nonHeapMemoryMax;
    }

    @Override
    public void run() {

        ResourceCheckpoint sd = new ResourceCheckpoint();

        MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
        com.sun.management.OperatingSystemMXBean sbean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        sd.setUsedHeap(mbean.getHeapMemoryUsage().getUsed());
        sd.setUsedNonHeap(mbean.getNonHeapMemoryUsage().getUsed());

        sd.setProcessCpuLoad(sbean.getProcessCpuLoad());
        sd.setSystemCpuLoad(sbean.getSystemCpuLoad());

        sd.setThreadCount(threadMXBean.getThreadCount());
        sd.setDaemonThreadCount(threadMXBean.getDaemonThreadCount());


        StringBuilder sb1 = new StringBuilder();

        sb1.append("Heap.Used: ").append(sd.getUsedHeap() / 1024000).append("MB");
        sb1.append(" NonHeap.Used: ").append(sd.getUsedNonHeap() / 1024000).append("MB");

        sb1.append(" ProcessCpu:").append(String.format("%.2f", sd.getProcessCpuLoad()*100,2)).append("%");
        sb1.append(" SystemCpu:").append(String.format("%.2f",sd.getSystemCpuLoad()*100,2)).append("%");
        sb1.append(" ThreadCount:").append(sd.getThreadCount() + sd.getDaemonThreadCount());

        synchronized (resourceCheckpointList){
            resourceCheckpointList.add(sd);
        }

        System.out.println(sb1);
    }

    public List<ResourceSamplePoint> getResourceCheckpointList(int maxPoint) {

        List<ResourceSamplePoint> samplingPointList = new ArrayList<>(maxPoint+1);

        int currentListSize= 0;
        synchronized (resourceCheckpointList){
            currentListSize = resourceCheckpointList.size()-1;

            if(currentListSize <= maxPoint){
                for(ResourceCheckpoint point : resourceCheckpointList)
                    samplingPointList.add(new ResourceSamplePoint(point));
                return samplingPointList;
            }
        }

        double inc = ((double) maxPoint) / currentListSize;
        double samplingProgress = inc;

        ResourceSamplePoint samplePoint = new ResourceSamplePoint();
        for(int i = 0;i<currentListSize; ++i){
            samplingProgress += inc;
            samplePoint.add(resourceCheckpointList.get(i));
            if(samplingProgress > samplingPointList.size()){
                samplePoint.calculateAverage();
                samplingPointList.add(samplePoint);
                samplePoint = new ResourceSamplePoint();
            }
        }

        ResourceCheckpoint last;
        synchronized (resourceCheckpointList){
            last = resourceCheckpointList.get(resourceCheckpointList.size()-1);
        }
        samplingPointList.add(new ResourceSamplePoint(last));

        logger.info("Sampled list from {} to {} ", currentListSize, samplingPointList.size());

        return samplingPointList;
    }
}
