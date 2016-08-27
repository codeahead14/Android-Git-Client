package com.example.gaurav.gitfetchapp.Repositories.ReadMe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("git")
    @Expose
    private String git;
    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("html")
    @Expose
    private String html;

    /**
     *
     * @return
     * The git
     */
    public String getGit() {
        return git;
    }

    /**
     *
     * @param git
     * The git
     */
    public void setGit(String git) {
        this.git = git;
    }

    /**
     *
     * @return
     * The self
     */
    public String getSelf() {
        return self;
    }

    /**
     *
     * @param self
     * The self
     */
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     *
     * @return
     * The html
     */
    public String getHtml() {
        return html;
    }

    /**
     *
     * @param html
     * The html
     */
    public void setHtml(String html) {
        this.html = html;
    }

}