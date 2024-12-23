package com.app.utils;

import java.util.UUID;

public class GenerateurId {

    public static String RandomIDGenerator() {
        return UUID.randomUUID().toString();
    }
}
