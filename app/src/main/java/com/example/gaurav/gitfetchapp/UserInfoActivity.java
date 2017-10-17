package com.example.gaurav.gitfetchapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private static final String TAG = UserInfoActivity.class.getName();
    private User userItem;
    private final static String BASE_API_URL = "https://api.github.com/";
    private final static String SAVE_STARRED = "save_starred";
    public final static String REPO_URL = "repo_url";
    public static final String dateFormat_2 = "dd MMM yyyy";
    private static int num_starred = 0;
    private Tracker mTracker;
    private GitHubEndpointInterface gitHubEndpointInterface;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private InterstitialAd mInterstitialAd;
    private Set<String> following;
    private String userName;
    private Snackbar connectionSnackbar;

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
    //@BindView(R.menu.user_info_menu) Menu menu;

    @OnClick(R.id.user_activity_fab) void onClick(View view){
        if(mInterstitialAd.isLoaded()){
            Log.v(TAG,"Interstitial Ready");
        }

        if(Utility.hasConnection(UserInfoActivity.this)) {
            Intent intent = new Intent(this, PublicUserRepoActivity.class);
            intent.putExtra(REPO_URL, userItem.getReposUrl());
            startActivity(intent);
        }else{
            Toast.makeText(this, getResources().getString(R.string.notOnline), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.more_vert_button) void showPopUp(View v){
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        inflater.inflate(R.menu.user_info_menu, menu);
        MenuItem item1 = menu.findItem(R.id.action_follow);
        MenuItem item2 = menu.findItem(R.id.action_unfollow);
        if(following.contains(userName)){
            item1.setVisible(false);
        }else if(!following.contains(userName))
            item2.setVisible(false);
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        editor = preferences.edit();
        switch (item.getItemId()) {
            case R.id.action_follow:
                Call<ResponseBody> call = gitHubEndpointInterface.putFollowing(userName);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        following.add(userName);
                        Toast.makeText(UserInfoActivity.this, "Following "+userName, Toast.LENGTH_SHORT).show();
                        editor.remove(PostLoginActivity.USER_FOLLOWING);
                        editor.putStringSet(PostLoginActivity.USER_FOLLOWING,following);
                        editor.apply();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                return true;
            case R.id.action_unfollow:
                Call<ResponseBody> unfollowCall = gitHubEndpointInterface.deleteFollowing(userName);
                unfollowCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(UserInfoActivity.this, "Unfollowed "+userName, Toast.LENGTH_SHORT).show();
                        following.remove(userName);
                        editor.remove(PostLoginActivity.USER_FOLLOWING);
                        editor.putStringSet(PostLoginActivity.USER_FOLLOWING,following);
                        editor.apply();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                return true;
            default:
                return false;
        }
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

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.contains(PostLoginActivity.USER_FOLLOWING)) {
            following = preferences.getStringSet(PostLoginActivity.USER_FOLLOWING, null);
        }

        gitHubEndpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(PostLoginActivity.USER_DETAILS)) {
            userItem = intent.getParcelableExtra(PostLoginActivity.USER_DETAILS);
            if (userItem != null )
                userName = userItem.getLogin();
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
        Typeface tf_4 = Typeface.createFromAsset(getResources().getAssets(),"font/Roboto-Regular.ttf");
        user_bio_textView.setTypeface(tf_2);
        user_login_textView.setTypeface(tf_4);
        user_company_textView.setTypeface(tf_4);
        user_location_textView.setTypeface(tf_4);
        user_joined_textView.setTypeface(tf_4);

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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(dateFormat_2, Locale.ENGLISH);
        String joined_time;
        try {
            joined_time = outputFormat.format(sdf.parse(item.getCreatedAt()));
            //return outputFormat.format(sdf.parse(input));
        } catch (ParseException e) {
            joined_time = e.getMessage();
        }
        Spanned joinedText = Html.fromHtml("Joined on "+"<b>"+joined_time+"</b>");
        user_joined_textView.setText(joinedText);
        //Log.v(TAG,"joined on "+item.getFollowers());
        //TextView user_followers_textView = (TextView) findViewById(R.id.user_followers);
        //TextView user_following_textView = (TextView) findViewById(R.id.user_following);

        user_followers_textView.setText(item.getFollowers().toString());
        user_following_textView.setText(item.getFollowing().toString());

    }

}
