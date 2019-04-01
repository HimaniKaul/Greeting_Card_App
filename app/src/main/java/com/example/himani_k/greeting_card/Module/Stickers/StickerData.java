package com.example.himani_k.greeting_card.Module.Stickers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StickerData {
        @Expose
        @SerializedName("is_favourite")
        private boolean is_favourite;
        @Expose
        @SerializedName("image")
        private String image;
        @Expose
        @SerializedName("id")
        private int id;

        public boolean getIs_favourite() {
            return is_favourite;
        }

        public void setIs_favourite(boolean is_favourite) {
            this.is_favourite = is_favourite;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
