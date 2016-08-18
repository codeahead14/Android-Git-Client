package com.example.gaurav.gitfetchapp.Events.CreateCommentPayload;

import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("position")
    @Expose
    private Object position;
    @SerializedName("line")
    @Expose
    private Object line;
    @SerializedName("path")
    @Expose
    private Object path;
    @SerializedName("commit_id")
    @Expose
    private String commitId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("body")
    @Expose
    private String body;

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The htmlUrl
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     *
     * @param htmlUrl
     * The html_url
     */
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The position
     */
    public Object getPosition() {
        return position;
    }

    /**
     *
     * @param position
     * The position
     */
    public void setPosition(Object position) {
        this.position = position;
    }

    /**
     *
     * @return
     * The line
     */
    public Object getLine() {
        return line;
    }

    /**
     *
     * @param line
     * The line
     */
    public void setLine(Object line) {
        this.line = line;
    }

    /**
     *
     * @return
     * The path
     */
    public Object getPath() {
        return path;
    }

    /**
     *
     * @param path
     * The path
     */
    public void setPath(Object path) {
        this.path = path;
    }

    /**
     *
     * @return
     * The commitId
     */
    public String getCommitId() {
        return commitId;
    }

    /**
     *
     * @param commitId
     * The commit_id
     */
    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The body
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @param body
     * The body
     */
    public void setBody(String body) {
        this.body = body;
    }

}