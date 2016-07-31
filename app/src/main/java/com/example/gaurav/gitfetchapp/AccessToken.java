package com.example.gaurav.gitfetchapp;

/**
 * Created by GAURAV on 22-07-2016.
 */
public class AccessToken {

    private static AccessToken tokenObj = new AccessToken();

    private String accessToken;
    private String tokenType;

    public static AccessToken getInstance(){
        return tokenObj;
    }

    public void setAccessToken(String token){
        this.accessToken = token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        // OAuth requires uppercase Authorization HTTP header value for token type
        if ( ! Character.isUpperCase(tokenType.charAt(0))) {
            tokenType =
                    Character
                            .toString(tokenType.charAt(0))
                            .toUpperCase() + tokenType.substring(1);
        }

        return tokenType;
    }
}