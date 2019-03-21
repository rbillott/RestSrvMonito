package test;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

public class CorsFilter implements Filter {

    @Override
    public void handle(Request request, Response response) throws Exception {

        response.header("Access-Control-Allow-Methods", "GET");
        response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        response.header("Access-Control-Allow-Credentials", "true");
    }

    public static void apply() {

        Spark.after(new CorsFilter());
    }
}
