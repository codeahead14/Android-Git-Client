package com.example.gaurav.gitfetchapp.Events.IssueCommentPayload;

import com.example.gaurav.gitfetchapp.Events.Payload;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueCommentPayload extends Payload{

    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("issue")
    @Expose
    private Issue issue;
    @SerializedName("comment")
    @Expose
    private Comment comment;

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

    /**
     *
     * @return
     * The comment
     */
    public Comment getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     * The comment
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

}