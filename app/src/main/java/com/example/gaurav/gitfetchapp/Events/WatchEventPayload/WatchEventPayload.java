package com.example.gaurav.gitfetchapp.Events.WatchEventPayload;

import com.example.gaurav.gitfetchapp.Events.Payload;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 14-08-2016.
 */
public class WatchEventPayload extends Payload {

    @SerializedName("action")
    @Expose
    private String action;

    public String getAction(){
        return this.action;
    }

    public void setAction(String action){
        this.action = action;
    }
}
