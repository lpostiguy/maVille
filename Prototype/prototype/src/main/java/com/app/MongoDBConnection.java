package com.app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;

public class MongoDBConnection {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String CONNECTION_STRING = dotenv.get("MONGODB_URI");
    private static final String DATABASE_NAME = dotenv.get("MONGODB_DB");

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    static {
        try {
            if (CONNECTION_STRING == null || DATABASE_NAME == null) {
                throw new RuntimeException("Missing environment variables for MongoDB connection.");
            }
            mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase(DATABASE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MongoDatabase getDatabase() {
        return database;
    }
}
