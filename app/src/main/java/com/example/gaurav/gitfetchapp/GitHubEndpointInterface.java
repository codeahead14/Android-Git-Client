package com.example.gaurav.gitfetchapp;

import com.example.gaurav.gitfetchapp.Feeds.FeedsJson;
import com.example.gaurav.gitfetchapp.Feeds.TimelineJson.Feed;
import com.example.gaurav.gitfetchapp.Gists.GistsJson;
import com.example.gaurav.gitfetchapp.Issues.IssuesJson;
import com.example.gaurav.gitfetchapp.Repositories.BranchDetails.BranchDetailJson;
import com.example.gaurav.gitfetchapp.Repositories.BranchesJson;
import com.example.gaurav.gitfetchapp.Events.EventsJson;
import com.example.gaurav.gitfetchapp.Repositories.CollaboratorsJson;
import com.example.gaurav.gitfetchapp.Repositories.Commits.CommitsRepoJson;
import com.example.gaurav.gitfetchapp.Repositories.StarredRepoJson;
import com.example.gaurav.gitfetchapp.Repositories.TreeDetails.RepoContentsJson;
import com.example.gaurav.gitfetchapp.Repositories.TreeDetails.TreeDetailsJson;
import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;
import com.example.gaurav.gitfetchapp.SearchGit.SearchGitJson;
import com.example.gaurav.gitfetchapp.UserInfo.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by GAURAV on 21-07-2016.
 */

public interface GitHubEndpointInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @POST("/authorizations")
    Call<LoginJson> getLoginCode(@Body LoginPost post);

    @GET("/users/{username}")
    Call<User> getUserDetails(@Path("username") String username);

    @GET("/user/repos")
    Call<ArrayList<UserRepoJson>> getUserRepositories();

    @GET("/repos/{owner}/{repo}/branches")
    Call<ArrayList<BranchesJson>> getUserBranches(@Path("owner") String owner,
                                                  @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/commits")
    Call<ArrayList<CommitsRepoJson>> getRepoCommits(@Path("owner") String owner,
                                         @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/collaborators")
    Call<ArrayList<CollaboratorsJson>> getRepoCollaborators(@Path("owner") String owner,
                                                            @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/branches/{branch}")
    Call<BranchDetailJson> getBranchDetails(@Path("owner") String owner,
                                            @Path("repo") String repo,
                                            @Path("branch") String branch);

    @GET("/repos/{owner}/{repo}/git/{type}/{sha}")
    Call<TreeDetailsJson> getRepoTree(@Path("owner") String owner,
                                      @Path("repo") String repo,
                                      @Path("type") String type,
                                      @Path("sha") String sha);

    @GET("/repos/{owner}/{repo}/contents/{path}")
    Call<ArrayList<RepoContentsJson>> getRepoContents(@Path("owner") String owner,
                                                      @Path("repo") String repo,
                                                      @Path("path") String path,
                                                      @Query("ref") String branch);

    @GET("/repos/{owner}/{repo}/events")
    Call<ArrayList<EventsJson>> getRepoEvents(@Path("owner") String owner,
                                              @Path("repo") String repo);

    @GET("/events")
    Call<ArrayList<EventsJson>> getPublicEvents();

    // For downloading file contents from the server
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    // For fetching user repository issues
    @GET ("/repos/{owner}/{repo}/issues")
    Call<ArrayList<IssuesJson>> getRepoIssues(@Path("owner") String owner,
                                   @Path("repo") String repo);

    // Fore fetching public issues
    @GET ("/user/issues")
    Call<ArrayList<IssuesJson>> getPublicIssues();

    // For accessing Private User Feeds
    @GET("/feeds")
    Call<FeedsJson> getUserFeeds();

    // For accessing Timeline
    @GET
    Call<Feed> getTimeline(@Url String url);

    @GET ("/timeline")
    Call<Feed> getTimeline2();

    // For fetching Starred Repo details
    @GET
    Call<ArrayList<StarredRepoJson>> getUserStarredRepos(@Url String url);

    // For accessing Gists
    @GET("/users/{username}/gists")
    Call<ArrayList<GistsJson>> getPrivateGists(@Path("username") String username);

    @FormUrlEncoded
    @POST("login/oauth/access_token")
    //AccessToken getAccessToken(@Field("code") String code);
    Call<AccessToken> getAccessToken(@Query("client_id") String id,
                               @Query("client_secret") String client_secret,
                               @Query("code") String code);

    // For fetching search Queries
    @GET("search/users")
    Call<SearchGitJson> getSearchResults(@Query("q") String query);

    // For fetching public user repositories
    @GET
    Call<ArrayList<UserRepoJson>> getPublicRepos(@Url String url);
}
