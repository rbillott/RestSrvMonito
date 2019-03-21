package test;

import com.jsoniter.spi.JsoniterSpi;
import spark.Spark;

import java.time.Instant;
import java.time.format.DateTimeFormatter;


public class main {

    static ResourceMonitor rm = new ResourceMonitor();
    static private Instant upInstant = Instant.now();

    public static void main(String[] args) {


        JsoniterSpi.registerTypeEncoder(Instant.class,
                (obj, stream) -> stream.writeVal(DateTimeFormatter.ISO_INSTANT.format((Instant)obj)));


        for(int i = 1; i < 30; ++i) {
            new Thread(main::runThread).start();
        }
        Spark.port(8989);
        CorsFilter.apply();
        Spark.get("/SnapshotResources", (req, res) -> rm.getResourceCheckpointList(10), new JsonTransformer());
        Spark.get("/SystemData", (req, res) -> makeSystemInfo(), new JsonTransformer());
    }

    public static SystemInfo makeSystemInfo(){
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setAppName("XApp::SQ");
        systemInfo.setContactEmail("rodolphe.billottet@gmail.com");
        systemInfo.setUpDatetime(upInstant);
        systemInfo.setHeapMemoryMax(rm.getHeapMemoryMax());
        systemInfo.setNonHeapMemoryMax(rm.getNonHeapMemoryMax());

        return systemInfo;
    }

    private static void runThread(){
        System.out.println("Running on Thread " + Thread.currentThread().getId());
        int i=0;
        while(true){
            if(++i > 10000000)
                i=0;

            try {
                Thread.sleep(0,1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
