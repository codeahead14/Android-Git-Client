package com.example.gaurav.gitfetchapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.GooglePlayServices.TrackerApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GAURAV on 23-08-2016.
 */
public class PreLoginDeciderActivity extends AppCompatActivity {
    private static final String TAG = PreLoginDeciderActivity.class.getName();
    public static final String ACCESS_TOKEN_KEY = "access_token";
    public static final String USERNAME_KEY = "username";
    SharedPreferences sharedPreferences;
    private Tracker mTracker;
    public static String loginName = null;

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Screen"+TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pre_login);
        //ButterKnife.bind(this);

        Typeface tf_1 = Typeface.createFromAsset(getResources().getAssets(),"font/Roboto-Medium.ttf");
        Typeface tf_2 = Typeface.createFromAsset(getResources().getAssets(),"font/Roboto-Light.ttf");
        //appNameText.setTypeface(tf_1);

        TrackerApplication application = (TrackerApplication) getApplication();
        mTracker = application.getDefaultTracker();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.contains(ACCESS_TOKEN_KEY) && sharedPreferences.contains(USERNAME_KEY)) {
            AccessToken.getInstance().setAccessToken(sharedPreferences.getString(ACCESS_TOKEN_KEY, null));
            setLoginName(sharedPreferences.getString(USERNAME_KEY, null));

            Intent intent = new Intent(this, PostLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public static void setLoginName(String username){
        loginName = username;
    }

    public static String getLoginName(){
        return loginName;
    }
}
