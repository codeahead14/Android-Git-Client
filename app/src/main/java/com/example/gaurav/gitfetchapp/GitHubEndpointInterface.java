package com.example.gaurav.gitfetchapp;

import com.example.gaurav.gitfetchapp.Feeds.FeedsJson;
import com.example.gaurav.gitfetchapp.Feeds.TimelineJson.Feed;
import com.example.gaurav.gitfetchapp.Gists.GistsJson;
import com.example.gaurav.gitfetchapp.Issues.IssueCommentsJson;
import com.example.gaurav.gitfetchapp.Issues.IssueEventsJson;
import com.example.gaurav.gitfetchapp.Issues.IssueItem;
import com.example.gaurav.gitfetchapp.Issues.IssuesJson;
import com.example.gaurav.gitfetchapp.Repositories.BranchDetails.BranchDetailJson;
import com.example.gaurav.gitfetchapp.Repositories.BranchesJson;
import com.example.gaurav.gitfetchapp.Events.EventsJson;
import com.example.gaurav.gitfetchapp.Repositories.CollaboratorsJson;
import com.example.gaurav.gitfetchapp.Repositories.Commits.CommitsRepoJson;
import com.example.gaurav.gitfetchapp.Repositories.Owner;
import com.example.gaurav.gitfetchapp.Repositories.ReadMe.ReadMeJson;
import com.example.gaurav.gitfetchapp.Repositories.StarredRepoJson;
import com.example.gaurav.gitfetchapp.Repositories.TreeDetails.RepoContentsJson;
import com.example.gaurav.gitfetchapp.Repositories.TreeDetails.TreeDetailsJson;
import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;
import com.example.gaurav.gitfetchapp.SearchGit.SearchGitJson;
import com.example.gaurav.gitfetchapp.UserInfo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

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

    @GET("users/{username}/following")
    Call<List<Owner>> getFollowers(@Path("username") String userName);

    @PUT("user/following/{username}")
    Call<ResponseBody> putFollowing(@Path("username") String userName);

    @DELETE("user/following/{username}")
    Call<ResponseBody> deleteFollowing(@Path("username") String userName);

    @GET("/user/repos")
    Call<ArrayList<UserRepoJson>> getUserRepositories(@Query("sort") String sort,
                                                      @Query("type") String type,
                                                      @Query("direction") String direction);

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

    @GET("/repos/{owner}/{repo}/readme")
    Call<ReadMeJson> getReadMe(@Path("owner") String owner,
                               @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/git/{type}/{sha}")
    Call<TreeDetailsJson> getRepoTree(@Path("owner") String owner,
                                      @Path("repo") String repo,
                                      @Path("type") String type,
                                      @Path("sha") String sha);

    @GET
    Call<UserRepoJson> getRepoContentsWithUrl(@Url String dynamicUrl);

    @GET
    Call<ArrayList<IssueEventsJson>> getIssueEventsWithUrl(@Url String dynamicUrl);

    @GET
    Call<ArrayList<IssueCommentsJson>> getIssueCommentsWithUrl(@Url String dynamicUrl);

    @GET
    Observable<ArrayList<IssueEventsJson>> getIssueEventsWithUrlRx(@Url String dynamicUrl);

    @GET
    Observable<ArrayList<IssueCommentsJson>> getIssueCommentsWithUrlRx(@Url String dynamicUrl);

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
    @Headers("Accept: application/vnd.github.VERSION.html")
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    // For fetching user repository issues
    @GET ("/repos/{owner}/{repo}/issues")
    Call<ArrayList<IssueItem>> getRepoIssues(@Path("owner") String owner,
                                             @Path("repo") String repo);

    // Fetching issues.- Using QueryMap to map optional queries
    @GET ("/search/issues")
    Call<IssuesJson> getIssues(@Query(value = "q",encoded = true) String options,
                               @Query("page") int pageNumber,
                               @Query("per_page") int numberPerAge);

    // For accessing Public User Feeds
    @GET("/feeds")
    Call<FeedsJson> getUserFeeds();

    // For accessing Personal User Events
    @GET ("users/{user}/received_events")
    Call<ArrayList<EventsJson>> getPrivateEvents(@Path("user") String userName);

    // For accessing Timeline
    @GET
    Call<Feed> getTimeline(@Url String url);

    @GET ("/timeline")
    Call<Feed> getTimeline2();

    // For fetching Starred Repo details
    @GET
    Call<ArrayList<StarredRepoJson>> getUserStarredRepos(@Url String url);

    // For accessing Private Gists
    @GET("/users/{username}/gists")
    Call<ArrayList<GistsJson>> getPrivateGists(@Path("username") String username);

    // For accessing Public Gists
    @GET("/gists/public")
    Call<ArrayList<GistsJson>> getPublicGists();

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
