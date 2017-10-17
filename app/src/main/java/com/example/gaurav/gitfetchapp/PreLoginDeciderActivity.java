package com.example.gaurav.gitfetchapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.GooglePlayServices.TrackerApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

/**
 * Created by GAURAV on 23-08-2016.
 */
public class PreLoginDeciderActivity extends AppCompatActivity {
    public static final String ACCESS_TOKEN_KEY = "access_token";
    public static final String USERNAME_KEY = "username";
    private static final String TAG = PreLoginDeciderActivity.class.getName();
    public static String loginName = null;
    SharedPreferences sharedPreferences;

    @Nullable @BindView(R.id.app_name_text) TextView appNameText;
    private Tracker mTracker;
    @BindView(R.id.title_text_img)
    ImageView appTitle_img;

    public static String getLoginName() {
        return loginName;
    }

    public static void setLoginName(String username) {
        loginName = username;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Screen" + TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);
        ButterKnife.bind(this);

        Typeface tf_1 = Typeface.createFromAsset(getResources().getAssets(),"font/Roboto-Bold.ttf");
        appNameText.setTypeface(tf_1);
        TrackerApplication application = (TrackerApplication) getApplication();
        mTracker = application.getDefaultTracker();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        appNameText.startAnimation(fadeIn);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (sharedPreferences.contains(ACCESS_TOKEN_KEY) && sharedPreferences.contains(USERNAME_KEY)) {
                            AccessToken.getInstance().setAccessToken(sharedPreferences.getString(ACCESS_TOKEN_KEY, null));
                            setLoginName(sharedPreferences.getString(USERNAME_KEY, null));

                            Intent intent = new Intent(PreLoginDeciderActivity.this, PostLoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(PreLoginDeciderActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                },2000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        Typeface tf_1 = Typeface.createFromAsset(getResources().getAssets(),"font/Roboto-Medium.ttf");
//        Typeface tf_2 = Typeface.createFromAsset(getResources().getAssets(),"font/Roboto-Light.ttf");
        //appNameText.setTypeface(tf_1);
    }
}
