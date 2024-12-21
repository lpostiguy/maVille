package com.app.utils;

import com.app.models.Entrave;
import com.app.models.Travail;
import com.google.gson.annotations.SerializedName;

import javax.xml.transform.Result;
import java.util.List;

public class ReponseApi {

    private boolean success;

    @SerializedName("result")
    private Object result;

    // Sous-classe pour les entraves
    public static class ResultatEntraves {

        @SerializedName("records")
        private List<Entrave> entraveRecords;

        public List<Entrave> getEntraveRecords() {
            return entraveRecords;
        }

        public void setEntraveRecords(List<Entrave> entraveRecords) {
            this.entraveRecords = entraveRecords;
        }
    }

    // Sous-classe pour les travaux
    public static class ResultatTravaux {

        @SerializedName("records")
        private List<Travail> TravauxRecords;

        public List<Travail> getTravauxRecords() {
            return TravauxRecords;
        }

        public void setTravauxRecords(List<Travail> travauxRecords) {
            this.TravauxRecords = travauxRecords;
        }
    }

    // Getters et setters pour la classe principale
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(ResultatEntraves result) {
        this.result = result;
    }
}
