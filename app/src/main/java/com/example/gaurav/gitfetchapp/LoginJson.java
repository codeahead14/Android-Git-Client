package com.example.gaurav.gitfetchapp;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginJson {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("app")
    @Expose
    private App app;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("hashed_token")
    @Expose
    private String hashedToken;
    @SerializedName("token_last_eight")
    @Expose
    private String tokenLastEight;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("note_url")
    @Expose
    private Object noteUrl;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("scopes")
    @Expose
    private List<Object> scopes = new ArrayList<Object>();
    @SerializedName("fingerprint")
    @Expose
    private Object fingerprint;

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
     * The app
     */
    public App getApp() {
        return app;
    }

    /**
     *
     * @param app
     * The app
     */
    public void setApp(App app) {
        this.app = app;
    }

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     * The hashedToken
     */
    public String getHashedToken() {
        return hashedToken;
    }

    /**
     *
     * @param hashedToken
     * The hashed_token
     */
    public void setHashedToken(String hashedToken) {
        this.hashedToken = hashedToken;
    }

    /**
     *
     * @return
     * The tokenLastEight
     */
    public String getTokenLastEight() {
        return tokenLastEight;
    }

    /**
     *
     * @param tokenLastEight
     * The token_last_eight
     */
    public void setTokenLastEight(String tokenLastEight) {
        this.tokenLastEight = tokenLastEight;
    }

    /**
     *
     * @return
     * The note
     */
    public String getNote() {
        return note;
    }

    /**
     *
     * @param note
     * The note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     *
     * @return
     * The noteUrl
     */
    public Object getNoteUrl() {
        return noteUrl;
    }

    /**
     *
     * @param noteUrl
     * The note_url
     */
    public void setNoteUrl(Object noteUrl) {
        this.noteUrl = noteUrl;
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
     * The scopes
     */
    public List<Object> getScopes() {
        return scopes;
    }

    /**
     *
     * @param scopes
     * The scopes
     */
    public void setScopes(List<Object> scopes) {
        this.scopes = scopes;
    }

    /**
     *
     * @return
     * The fingerprint
     */
    public Object getFingerprint() {
        return fingerprint;
    }

    /**
     *
     * @param fingerprint
     * The fingerprint
     */
    public void setFingerprint(Object fingerprint) {
        this.fingerprint = fingerprint;
    }

}