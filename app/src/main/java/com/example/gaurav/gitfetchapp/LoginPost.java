package com.example.gaurav.gitfetchapp;

/**
 * Created by GAURAV on 27-07-2016.
 */
public class LoginPost {
    String[] scopes;
    String note;
    String note_url;
    String client_id;
    String client_secret;
    String fingerprint;

    public void setScopes(String[] params){
        this.scopes = params;
    }

    public void setNote(String note){
        this.note = note;
    }

    public void setNote_url(String note_url){
        this.note_url = note_url;
    }

    public void setClient_id(String id){
        this.client_id = id;
    }

    public void setClient_secret(String secret){
        this.client_secret = secret;
    }
}
