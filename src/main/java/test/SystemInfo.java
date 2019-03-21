package test;

import java.time.Instant;

public class SystemInfo {

    private String appName;
    private String contactEmail;
    private Instant upDatetime;
    private long heapMemoryMax;
    private long nonHeapMemoryMax;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Instant getUpDatetime() {
        return upDatetime;
    }

    public void setUpDatetime(Instant upDatetime) {
        this.upDatetime = upDatetime;
    }

    public long getHeapMemoryMax() {
        return heapMemoryMax;
    }

    public void setHeapMemoryMax(long heapMemoryMax) {
        this.heapMemoryMax = heapMemoryMax;
    }

    public long getNonHeapMemoryMax() {
        return nonHeapMemoryMax;
    }

    public void setNonHeapMemoryMax(long nonHeapMemoryMax) {
        this.nonHeapMemoryMax = nonHeapMemoryMax;
    }
}
