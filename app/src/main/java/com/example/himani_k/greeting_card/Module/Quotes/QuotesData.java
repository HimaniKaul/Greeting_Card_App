package com.example.himani_k.greeting_card.Module.Quotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuotesData {
        @Expose
        @SerializedName("is_favourite")
        private boolean is_favourite;
        @Expose
        @SerializedName("quotes")
        private String quotes;
        @Expose
        @SerializedName("id")
        private int id;

        public boolean getIs_favourite() {
            return is_favourite;
        }

        public void setIs_favourite(boolean is_favourite) {
            this.is_favourite = is_favourite;
        }

        public String getQuotes() {
            return quotes;
        }

        public void setQuotes(String quotes) {
            this.quotes = quotes;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

