package com.example.gaurav.gitfetchapp.Events.PullRequestPayload;

import com.example.gaurav.gitfetchapp.Events.Payload;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PullRequestPayload extends Payload {

    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("pull_request")
    @Expose
    private PullRequest pullRequest;

    /**
     *
     * @return
     * The action
     */
    public String getAction() {
        return action;
    }

    /**
     *
     * @param action
     * The action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     *
     * @return
     * The number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     *
     * @param number
     * The number
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     *
     * @return
     * The pullRequest
     */
    public PullRequest getPullRequest() {
        return pullRequest;
    }

    /**
     *
     * @param pullRequest
     * The pull_request
     */
    public void setPullRequest(PullRequest pullRequest) {
        this.pullRequest = pullRequest;
    }

}