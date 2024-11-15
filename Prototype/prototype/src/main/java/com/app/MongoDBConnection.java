package com.app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    private static final String CONNECTION_STRING =
        "mongodb+srv://ostilo22" + ":Juliette627212@mavillecluster.wnlyl" +
            ".mongodb.net/?retryWrites=true&w=majority&appName=MaVilleCluster";
    private static final String DATABASE_NAME = "MaVilleDataBase";

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    static {
        try {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase(DATABASE_NAME);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static MongoDatabase getDatabase() {
        return database;
    }
}
