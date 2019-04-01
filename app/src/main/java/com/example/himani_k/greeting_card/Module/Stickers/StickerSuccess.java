package com.example.himani_k.greeting_card.Module.Stickers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class StickerSuccess {
    @Expose
    @SerializedName("Stickers")
    private List<StickerData> Stickers;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("success")
    private boolean success;

    public List<StickerData> getStickers() {
        return Stickers;
    }

    public void setStickers(List<StickerData> Stickers) {
        this.Stickers = Stickers;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
