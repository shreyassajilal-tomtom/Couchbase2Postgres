package com.tomtom.places.Couchbase2Postgres.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.tomtom.places.model.ClusterState;
import com.tomtom.places.optimus.datastore.DataStore;
import com.tomtom.places.optimus.datastore.config.DataStoreConfiguration;
import com.tomtom.places.optimus.datastore.dto.ComplexViewResult;
import com.tomtom.places.optimus.datastore.dto.ViewProperties;
import com.tomtom.places.optimus.datastore.services.DataStoreFactory;
import org.lightcouch.Page;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class findAllIds {


    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger("com.couchbase");
        //rootLogger.setLevel(Level.WARN); // or INFO or whatever
        //findById findDocument=new findById();
//        System.out.println("Fetch a Document from Couchbase as A String");
//        System.out.println();

//        Scanner scanner=new Scanner(new BufferedInputStream(System.in));
//        String id=scanner.next();
//        scanner.close();

//        JsonObject document=findDoc(id);

//        Gson gson=new GsonBuilder().setPrettyPrinting().create();

//        System.out.println(gson.toJson(document));
        findDoc();

    }

    public static void findDoc(){

//        DataStore sourceDataStore = getDatastore("http://tritest1-813-003.maps-pu-poi-prod.amiefarm.com:8091"
//                , 8091, "places", "places", "places");

        DataStore sourceDataStore = getDatastore("http://fuse-production-001.maps-pu-poi-prod.amiefarm.com:8091"
                , 8091, "places", "tempuser", "tempuser");

        ViewProperties viewProperties=ViewProperties.builder().viewName("search/documents_by_locality")
                .startKey(DaoUtil.createKey(new Object[]{"IND","CLUSTER_STATE"}))
                .endKey(DaoUtil.createKeyWithEmptyArrayAsLastElement(new Object[]{"IND","CLUSTER_STATE"}))
                .pageParam(null)
                .includeDocs(false).reduce(false).group(false).build();

        List<String> listdocs =sourceDataStore.getViewResults(viewProperties,String.class);
        System.out.println(listdocs.size());
        System.out.println(listdocs);

        String pageParam = null;
//        do {
//            Page<ComplexViewResult> resultPage = sourceDataStore.getComplexViewPaginatedResults(viewProperties, ComplexViewResult.class);
//            pageParam = resultPage.getNextParam();
//            List<String> idsFromPage = DaoUtil.getIdsFromPage(resultPage);
//
//
//
//
//
//            System.out.println("Fetched and inserted {} documents by country from cluster"+ idsFromPage.size());
//        } while(pageParam != null);

        Page<ComplexViewResult> placePage = null;
        int inserted = 0;

            placePage = sourceDataStore.getComplexViewPaginatedResults(viewProperties, ComplexViewResult.class);
          //  postgresInsert(placePage.getResultList());
            //viewProperties.setPageParam(placePage.getNextParam());
            inserted += viewProperties.getPageSize();
            System.out.println("Inserted :" + inserted);
//            if(inserted>400)
//                break;
            List<String> idsFromPage = DaoUtil.getIdsFromPage(placePage);
            System.out.println(idsFromPage);










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
