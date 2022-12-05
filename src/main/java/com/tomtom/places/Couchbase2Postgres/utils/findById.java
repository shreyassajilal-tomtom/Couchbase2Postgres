package com.tomtom.places.Couchbase2Postgres.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.tomtom.places.optimus.datastore.DataStore;
import com.tomtom.places.optimus.datastore.config.DataStoreConfiguration;
import com.tomtom.places.optimus.datastore.services.DataStoreFactory;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class findById {


    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger("com.couchbase");
        rootLogger.setLevel(Level.WARN); // or INFO or whatever
        //findById findDocument=new findById();

            System.out.println("Fetch a Document from Couchbase as A String");
            System.out.println();

            Scanner scanner = new Scanner(new BufferedInputStream(System.in));
            String id = scanner.next();
            scanner.close();

            JsonObject document = findDoc(id);

            Gson gson = new Gson();

            System.out.println(gson.toJson(document));


    }

    public static JsonObject findDoc(String id){

//        DataStore sourceDataStore = getDatastore("http://tritest1-813-003.maps-pu-poi-prod.amiefarm.com:8091"
//                , 8091, "places", "places", "places");

        DataStore sourceDataStore = getDatastore("http://fuse-production-001.maps-pu-poi-prod.amiefarm.com:8091"
                , 8091, "places", "tempuser", "tempuser");

        JsonObject document=new JsonObject();
        document=sourceDataStore.getDocumentById(id);
        return document;
    }
    private static DataStore getDatastore(String host, int port, String bucket, String username, String password) {
        System.out.println("*********************************************************************************");
        System.out.println("*********************************************************************************");
        System.out.println("*********************************************************************************");
        System.out.println("connecting to host url::::::"+ host);
        System.out.println("*********************************************************************************");
        System.out.println("*********************************************************************************");
        System.out.println("*********************************************************************************");

        DataStoreConfiguration configuration = new DataStoreConfiguration(bucket, host, port, username, password);
        DataStore dataStore=new DataStoreFactory(configuration, true).getDataStore();
       // dataStore.se
        return dataStore;
    }

}
