package com.example.gaurav.gitfetchapp.Events.IssueEventPayload;

import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.Issue;
import com.example.gaurav.gitfetchapp.Events.Payload;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueEventPayload extends Payload {

    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("issue")
    @Expose
    private Issue issue;

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
     * The issue
     */
    public Issue getIssue() {
        return issue;
    }

    /**
     *
     * @param issue
     * The issue
     */
    public void setIssue(Issue issue) {
        this.issue = issue;
    }

}