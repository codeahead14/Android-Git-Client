package com.example.gaurav.gitfetchapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.GooglePlayServices.TrackerApplication;
import com.example.gaurav.gitfetchapp.Repositories.StarredRepoJson;
import com.example.gaurav.gitfetchapp.UserInfo.PublicUserRepoActivity;
import com.example.gaurav.gitfetchapp.UserInfo.User;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {
    private static final String TAG = UserInfoActivity.class.getName();
    private User userItem;
    private final static String BASE_API_URL = "https://api.github.com/";
    private final static String SAVE_STARRED = "save_starred";
    public final static String REPO_URL = "repo_url";
    private static int num_starred = 0;
    private Tracker mTracker;
    private InterstitialAd mInterstitialAd;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.user_login_name) TextView user_login_textView;
    @BindView(R.id.user_image) ImageView userAvatarImage;
    @BindView(R.id.user_bio) TextView user_bio_textView;
    @BindView(R.id.user_company) TextView user_company_textView;
    @BindView(R.id.user_location) TextView user_location_textView;
    @BindView(R.id.user_mail) TextView user_mail_textView;
    @BindView(R.id.user_blog) TextView user_blog_textView;
    @BindView(R.id.user_joined) TextView user_joined_textView;
    @BindView(R.id.user_followers) TextView user_followers_textView;
    @BindView(R.id.user_starred) TextView user_starred_textView;
    @BindView(R.id.user_following) TextView user_following_textView;

    @OnClick(R.id.fab) void onClick(View view){
        if(mInterstitialAd.isLoaded()){
            Log.v(TAG,"Interstitial Ready");
            mInterstitialAd.show();
        }
        Intent intent = new Intent(this, PublicUserRepoActivity.class);
        intent.putExtra(REPO_URL,userItem.getReposUrl());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Screen"+TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user_info);
        setSupportActionBar(toolbar);
        TrackerApplication application = (TrackerApplication) getApplication();
        mTracker = application.getDefaultTracker();
        //toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        ButterKnife.bind(this);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();

        GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(PostLoginActivity.USER_DETAILS)) {
            userItem = intent.getParcelableExtra(PostLoginActivity.USER_DETAILS);

            if(savedInstanceState != null) {
                num_starred = savedInstanceState.getInt(SAVE_STARRED);
                user_starred_textView.setText(Integer.toString(num_starred));
            }else {
                Call<ArrayList<StarredRepoJson>> call = gitHubEndpointInterface.getUserStarredRepos(
                        BASE_API_URL + "users/" + userItem.getLogin() + "/starred");
                call.enqueue(new Callback<ArrayList<StarredRepoJson>>() {
                    @Override
                    public void onResponse(Call<ArrayList<StarredRepoJson>> call, Response<ArrayList<StarredRepoJson>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<StarredRepoJson> item = response.body();
                            num_starred = item.size();
                            user_starred_textView.setText(Integer.toString(num_starred));
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<StarredRepoJson>> call, Throwable t) {

                    }
                });
            }
            Picasso.with(this)
                    .load(userItem.getAvatarUrl())
                    .transform(new CircleTransform())
                    .into(userAvatarImage);
            setUpViews(userItem);
        }
    }

    private void requestNewInterstitial() {
        Log.v(TAG,"requesting new Ad");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("23673")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_STARRED,num_starred);
    }

    private void setUpViews(User item){
        Typeface tf_1 = Typeface.createFromAsset(getResources().getAssets(),"font/RobotoCondensed-Regular.ttf");
        Typeface tf_2 = Typeface.createFromAsset(getResources().getAssets(),"font/Roboto-Light.ttf");
        Typeface tf_3 = Typeface.createFromAsset(getResources().getAssets(),"font/Roboto-Medium.ttf");
        user_bio_textView.setTypeface(tf_2);
        user_login_textView.setTypeface(tf_1);
        user_company_textView.setTypeface(tf_3);
        user_location_textView.setTypeface(tf_3);
        user_joined_textView.setTypeface(tf_3);

        user_login_textView.setText(item.getLogin());
        if(item.getBio() != null)
            user_bio_textView.setText(item.getBio());
        else
            user_bio_textView.setVisibility(View.GONE);
        if(item.getCompany() != null)
            user_company_textView.setText(item.getCompany());
        else
            user_company_textView.setVisibility(View.GONE);
        if(item.getLocation() != null)
            user_location_textView.setText(item.getLocation());
        else
            user_location_textView.setVisibility(View.GONE);
        if(item.getEmail() != null)
            user_mail_textView.setText(item.getEmail());
        else
            user_mail_textView.setVisibility(View.GONE);
        if(item.getBlog() != null)
            user_blog_textView.setText(item.getBlog());
        else
            user_blog_textView.setVisibility(View.GONE);

        Spanned joinedText = Html.fromHtml("Joined on "+"<b>"+Utility.formatDateString(item.getCreatedAt())+"</b>");
        user_joined_textView.setText(joinedText);
        //Log.v(TAG,"joined on "+item.getFollowers());
        //TextView user_followers_textView = (TextView) findViewById(R.id.user_followers);
        //TextView user_following_textView = (TextView) findViewById(R.id.user_following);

        user_followers_textView.setText(item.getFollowers().toString());
        user_following_textView.setText(item.getFollowing().toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        return true;
    }

}
