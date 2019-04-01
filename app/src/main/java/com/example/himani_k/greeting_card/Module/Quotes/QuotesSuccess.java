package com.example.himani_k.greeting_card.Module.Quotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class QuotesSuccess {
    @Expose
    @SerializedName("Quotes")
    private List<QuotesData> Quotes;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("success")
    private boolean success;

    public List<QuotesData> getQuotes() {
        return Quotes;
    }

    public void setQuotes(List<QuotesData> Quotes) {
        this.Quotes = Quotes;
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
