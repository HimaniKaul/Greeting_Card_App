package com.example.himani_k.greeting_card.Module.Frames;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class FrameSuccess {

    @Expose
    @SerializedName("Frames")
    private List<FrameData> Frames;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("success")
    private boolean success;

    public List<FrameData> getFrames() {
        return Frames;
    }

    public void setFrames(List<FrameData> Frames) {
        this.Frames = Frames;
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
