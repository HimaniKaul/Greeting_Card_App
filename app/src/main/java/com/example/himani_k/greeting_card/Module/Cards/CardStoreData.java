package com.example.himani_k.greeting_card.Module.Cards;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class CardStoreData {
    @Expose
    @SerializedName("Cards")
    private List<CardsData> Cards;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("success")
    private boolean success;

    public List<CardsData> getCards() {
        return Cards;
    }

    public void setCards(List<CardsData> Cards) {
        this.Cards = Cards;
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
