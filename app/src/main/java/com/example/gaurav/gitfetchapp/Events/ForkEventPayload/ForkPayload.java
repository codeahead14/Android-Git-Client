package com.example.gaurav.gitfetchapp.Events.ForkEventPayload;

import com.example.gaurav.gitfetchapp.Events.Payload;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForkPayload extends Payload{

    @SerializedName("forkee")
    @Expose
    private Forkee forkee;

    /**
     *
     * @return
     * The forkee
     */
    public Forkee getForkee() {
        return forkee;
    }

    /**
     *
     * @param forkee
     * The forkee
     */
    public void setForkee(Forkee forkee) {
        this.forkee = forkee;
    }

}