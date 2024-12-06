package com.app.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

public class JsonFormatting {

    public static String jsonToTextRequeteTravail(String jsonString) {
        Gson gson = new GsonBuilder().create();
        JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);

        StringBuilder plainText = new StringBuilder();
        if (jsonElement.isJsonArray()) {
            for (JsonElement element : jsonElement.getAsJsonArray()) {
                plainText.append(parseJsonObject(element.getAsJsonObject())).append("\n");
            }
        } else if (jsonElement.isJsonObject()) {
            plainText.append(parseJsonObject(jsonElement.getAsJsonObject()));
        }
        return plainText.toString();
    }

    private static String parseJsonObject(JsonObject jsonObject) {
        StringBuilder plainText = new StringBuilder();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            if (entry.getKey().equals("_id") || entry.getKey().equals("id") || entry.getKey().equals("demandeurRequete") || entry.getKey().equals("actif")) {
                continue;
            }
            plainText.append(entry.getKey()).append(": ");
            if (entry.getValue().isJsonPrimitive()) {
                plainText.append(entry.getValue().getAsString());
            } else if (entry.getValue().isJsonArray()) {
                plainText.append(entry.getValue().getAsJsonArray().toString().replaceAll("[\\[\\]\"]", ""));
            } else if (entry.getValue().isJsonObject()) {
                plainText.append(parseJsonObject(entry.getValue().getAsJsonObject()));
            }
            plainText.append("\n");
        }
        return plainText.toString();
    }
}
