package com.example.gaurav.gitfetchapp.Events.GollumEventPayload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page {

    @SerializedName("page_name")
    @Expose
    private String pageName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("summary")
    @Expose
    private Object summary;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("sha")
    @Expose
    private String sha;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;

    /**
     *
     * @return
     * The pageName
     */
    public String getPageName() {
        return pageName;
    }

    /**
     *
     * @param pageName
     * The page_name
     */
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The summary
     */
    public Object getSummary() {
        return summary;
    }

    /**
     *
     * @param summary
     * The summary
     */
    public void setSummary(Object summary) {
        this.summary = summary;
    }

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

}