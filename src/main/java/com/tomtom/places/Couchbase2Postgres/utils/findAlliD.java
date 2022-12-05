package com.tomtom.places.Couchbase2Postgres.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.google.gson.Gson;
import com.tomtom.places.model.ClusterState;
import com.tomtom.places.optimus.datastore.DataStore;
import com.tomtom.places.optimus.datastore.config.DataStoreConfiguration;
import com.tomtom.places.optimus.datastore.dto.ComplexViewResult;
import com.tomtom.places.optimus.datastore.dto.ViewProperties;
import com.tomtom.places.optimus.datastore.services.DataStoreFactory;
import org.lightcouch.Page;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class findAlliD {

    private static List<String> FailedPlaces=new ArrayList<>();
    public static void main(String args[]) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger("com.couchbase");
        rootLogger.setLevel(Level.WARN);

        findAlliD couchbasePuller = new findAlliD();
        couchbasePuller.pullData();
        System.out.println("These docs failed:::::::::::");
        FailedPlaces.forEach(place -> System.out.println(place+" failed"));
        System.out.println(":::::::::"+FailedPlaces.size()+" number of docs failed:::::::::::");


    }

    public void pullData() {
//        DataStore sourceDataStore = getDatastore("http://ifbtest2-784-001.maps-pu-poi-prod.amiefarm.com:8091"
//            , 8091, "places", "places", "places");
//        DataStore sourceDataStore = getDatastore("http://tritest1-813-003.maps-pu-poi-prod.amiefarm.com:8091"
//                , 8091, "places", "places", "places");
        DataStore sourceDataStore = getDatastore("http://fuse-production-001.maps-pu-poi-prod.amiefarm.com:8091"
                , 8091, "places", "tempuser", "tempuser");

//        DataStore destinationDataStore = getDatastore("localhost"
//            , 9000, "places", "places", "places");


//        DataStore sourceDataStore = getDatastore("127.0.0.1"
//            , 8091, "places", "admin", "postgres");

        ViewProperties viewProperties=ViewProperties.builder().viewName("search/documents_by_locality")
                .startKey(DaoUtil.createKey(new Object[]{"IND","CLUSTER_STATE"}))
                .endKey(DaoUtil.createKeyWithEmptyArrayAsLastElement(new Object[]{"IND","CLUSTER_STATE"}))
                .pageSize(1000000).includeDocs(false).reduce(false).group(false).build();

        String pageParam = null;
        Page<ComplexViewResult> placePage = null;
        int inserted = 0;
        do {
            placePage = sourceDataStore.getComplexViewPaginatedResults(viewProperties, ComplexViewResult.class);
            postgresInsert(placePage.getResultList());
            viewProperties.setPageParam(placePage.getNextParam());
            inserted += viewProperties.getPageSize();
            System.out.println("Inserted :" + inserted);
//            if(inserted>400)
//                break;

        } while (placePage.isHasNext());

        //   placePage.getResultList().forEach(place -> System.out.println(place.get_id()));





    }

    private DataStore getDatastore(String host, int port, String bucket, String username, String password) {
        DataStoreConfiguration configuration = new DataStoreConfiguration(bucket, host, port, username, password);
        return new DataStoreFactory(configuration, true).getDataStore();
    }

    private void postgresInsert(List<ComplexViewResult> docs)
    {   LoggerContext loggerContext2 = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger rootLogger2 = loggerContext2.getLogger("org.springframework.web");
        rootLogger2.setLevel(Level.WARN); // or INFO or whatever


        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "200");
        docs.parallelStream().forEach(doc -> {


                // FailedPlaces.add(e.getMessage());
            //System.out.println(doc.getId());
             //   FailedPlaces.add(doc.getId());

                try{

                   // FileWriter writer = new FileWriter("failed-ind-cluster-state.txt",true);
                        FileWriter writer = new FileWriter("C:\\Users\\sajilal\\intern\\Couchbase2Postgres\\completed-cluster-state.txt",true);

                    BufferedWriter buffer = new BufferedWriter(writer);
                    buffer.write(doc.getId());
                    buffer.newLine();
                    //buffer.write(e.getMessage());
                    //buffer.newLine();
                    buffer.close();

                }catch(Exception y)
                {System.out.println(y);}






    });
}
}
