package com.example.gaurav.gitfetchapp.Repositories.TreeDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileContentJson {

    @SerializedName("sha")
    @Expose
    private String sha;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("encoding")
    @Expose
    private String encoding;

    /**
     * @return The sha
     */
    public String getSha() {
        return sha;
    }

    /**
     * @param sha The sha
     */
    public void setSha(String sha) {
        this.sha = sha;
    }

    /**
     * @return The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return The encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding The encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}