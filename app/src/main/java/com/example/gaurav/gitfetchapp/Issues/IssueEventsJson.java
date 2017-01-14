package com.example.gaurav.gitfetchapp.Issues;

import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.Label;
import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueEventsJson {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("actor")
    @Expose
    private User actor;
    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("commit_id")
    @Expose
    private Object commitId;
    @SerializedName("commit_url")
    @Expose
    private Object commitUrl;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("label")
    @Expose
    private Label label;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Object getCommitId() {
        return commitId;
    }

    public void setCommitId(Object commitId) {
        this.commitId = commitId;
    }

    public Object getCommitUrl() {
        return commitUrl;
    }

    public void setCommitUrl(Object commitUrl) {
        this.commitUrl = commitUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

}