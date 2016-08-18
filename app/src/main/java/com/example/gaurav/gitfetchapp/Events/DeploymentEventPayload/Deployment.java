package com.example.gaurav.gitfetchapp.Events.DeploymentEventPayload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deployment {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sha")
    @Expose
    private String sha;
    @SerializedName("ref")
    @Expose
    private String ref;
    @SerializedName("task")
    @Expose
    private String task;
    @SerializedName("payload")
    @Expose
    private Payload payload;
    @SerializedName("environment")
    @Expose
    private String environment;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("creator")
    @Expose
    private Creator creator;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("statuses_url")
    @Expose
    private String statusesUrl;
    @SerializedName("repository_url")
    @Expose
    private String repositoryUrl;

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
     * The task
     */
    public String getTask() {
        return task;
    }

    /**
     *
     * @param task
     * The task
     */
    public void setTask(String task) {
        this.task = task;
    }

    /**
     *
     * @return
     * The payload
     */
    public Payload getPayload() {
        return payload;
    }

    /**
     *
     * @param payload
     * The payload
     */
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    /**
     *
     * @return
     * The environment
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     *
     * @param environment
     * The environment
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     *
     * @return
     * The description
     */
    public Object getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(Object description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The creator
     */
    public Creator getCreator() {
        return creator;
    }

    /**
     *
     * @param creator
     * The creator
     */
    public void setCreator(Creator creator) {
        this.creator = creator;
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
     * The statusesUrl
     */
    public String getStatusesUrl() {
        return statusesUrl;
    }

    /**
     *
     * @param statusesUrl
     * The statuses_url
     */
    public void setStatusesUrl(String statusesUrl) {
        this.statusesUrl = statusesUrl;
    }

    /**
     *
     * @return
     * The repositoryUrl
     */
    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    /**
     *
     * @param repositoryUrl
     * The repository_url
     */
    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

}