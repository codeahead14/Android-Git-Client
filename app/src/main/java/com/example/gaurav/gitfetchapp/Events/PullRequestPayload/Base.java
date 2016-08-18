package com.example.gaurav.gitfetchapp.Events.PullRequestPayload;

import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Base {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("ref")
    @Expose
    private String ref;
    @SerializedName("sha")
    @Expose
    private String sha;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("repo")
    @Expose
    private Repo repo;

    /**
     *
     * @return
     * The label
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @param label
     * The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     *
     * @return
     * The ref
     */
    public String getRef() {
        return ref;
    }

    /**
     *
     * @param ref
     * The ref
     */
    public void setRef(String ref) {
        this.ref = ref;
    }

    /**
     *
     * @return
     * The sha
     */
    public String getSha() {
        return sha;
    }

    /**
     *
     * @param sha
     * The sha
     */
    public void setSha(String sha) {
        this.sha = sha;
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
     * The repo
     */
    public Repo getRepo() {
        return repo;
    }

    /**
     *
     * @param repo
     * The repo
     */
    public void setRepo(Repo repo) {
        this.repo = repo;
    }

}