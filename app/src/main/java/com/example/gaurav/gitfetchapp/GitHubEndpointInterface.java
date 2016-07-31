package com.example.gaurav.gitfetchapp;

import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by GAURAV on 21-07-2016.
 */

public interface GitHubEndpointInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @POST("/authorizations")
    Call<LoginJson> getLoginCode(@Body LoginPost post);

    @GET("/user/repos")
    Call<ArrayList<UserRepoJson>> getUserRepositories();

    @FormUrlEncoded
    @POST("login/oauth/access_token")
    //AccessToken getAccessToken(@Field("code") String code);
    Call<AccessToken> getAccessToken(@Query("client_id") String id,
                               @Query("client_secret") String client_secret,
                               @Query("code") String code);
}
