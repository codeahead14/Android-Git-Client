package com.example.gaurav.gitfetchapp;

import android.net.Credentials;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.Feeds.FeedsJson;
import com.example.gaurav.gitfetchapp.Feeds.TimelineJson.Feed;
import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by GAURAV on 09-08-2016.
 */
public class FeedsFragment extends Fragment {
    private static final String TAG = FeedsFragment.class.getName();
    private View rootView;
    private AccessToken mAccessToken;
    private static final String API_BASE_URL = "https://api.github.com";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccessToken = AccessToken.getInstance();

        GitHubEndpointInterface gitInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class, mAccessToken);
        Call<FeedsJson> call = gitInterface.getUserFeeds();
        call.enqueue(new Callback<FeedsJson>() {
            @Override
            public void onResponse(Call<FeedsJson> call, Response<FeedsJson> response) {
                if(response.isSuccessful()) {
                    FeedsJson item = response.body();
                    Log.v(TAG, "feed response: " + item.getTimelineUrl());

                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(interceptor)
                            .authenticator(new Authenticator() {
                                @Override
                                public Request authenticate(Route route, okhttp3.Response response) throws IOException {
                                    String credentials  = okhttp3.Credentials.basic("","");
                                    return null;
                                }
                            })
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://github.com")
                            .client(client)
                            .addConverterFactory(SimpleXmlConverterFactory.create())
                            .build();

                    GitHubEndpointInterface gitHubEndpointInterface = retrofit.create(GitHubEndpointInterface.class);
                            //ServiceGenerator.createService(GitHubEndpointInterface.class);
                    //Call<Feed> responseBodyCall = gitHubEndpointInterface.getTimeline(
                      //      "https://github.com/timeline");
                    Call<Feed> responseBodyCall = gitHubEndpointInterface.getTimeline2();
                    responseBodyCall.enqueue(new Callback<Feed>() {
                        @Override
                        public void onResponse(Call<Feed> call, Response<Feed> response) {
                            Feed item = response.body();
                        }

                        @Override
                        public void onFailure(Call<Feed> call, Throwable t) {
                            Log.v(TAG,"error"+t.getMessage());
                        }
                    });
                    //Call<ResponseBody> responseBodyCall
                }
            }

            @Override
            public void onFailure(Call<FeedsJson> call, Throwable t) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_feeds,container);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
