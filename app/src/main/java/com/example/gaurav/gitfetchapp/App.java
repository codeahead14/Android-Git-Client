package com.example.gaurav.gitfetchapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class App {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("client_id")
    @Expose
    private String clientId;

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
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
     * The clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     *
     * @param clientId
     * The client_id
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}