package com.example.himani_k.greeting_card.Module.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class CategorySuccess {
    @Expose
    @SerializedName("Category")
    public List<CategoryData> Category;
    @Expose
    @SerializedName("Message")
    public String Message;
    @Expose
    @SerializedName("success")
    public boolean success;

    public List<CategoryData> getCategory() {
        return Category;
    }

    public void setCategory(List<CategoryData> category) {
        Category = category;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}


