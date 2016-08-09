package com.example.gaurav.gitfetchapp.Repositories;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilesJson {

    @SerializedName("sha")
    @Expose
    private String sha;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("tree")
    @Expose
    private List<Tree> tree = new ArrayList<Tree>();
    @SerializedName("truncated")
    @Expose
    private Boolean truncated;

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
     * The tree
     */
    public List<Tree> getTree() {
        return tree;
    }

    /**
     *
     * @param tree
     * The tree
     */
    public void setTree(List<Tree> tree) {
        this.tree = tree;
    }

    /**
     *
     * @return
     * The truncated
     */
    public Boolean getTruncated() {
        return truncated;
    }

    /**
     *
     * @param truncated
     * The truncated
     */
    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

}