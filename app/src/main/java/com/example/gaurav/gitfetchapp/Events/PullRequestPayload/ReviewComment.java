package com.example.gaurav.gitfetchapp.Events.PullRequestPayload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewComment {

    @SerializedName("href")
    @Expose
    private String href;

    /**
     *
     * @return
     * The href
     */
    public String getHref() {
        return href;
    }

    /**
     *
     * @param href
     * The href
     */
    public void setHref(String href) {
        this.href = href;
    }

}