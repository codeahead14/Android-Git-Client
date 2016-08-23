package com.example.gaurav.gitfetchapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by GAURAV on 23-08-2016.
 */
public class PreLoginDeciderActivity extends AppCompatActivity {
    private static final String TAG = PreLoginDeciderActivity.class.getName();
    public static final String ACCESS_TOKEN_KEY = "access_token";
    public static final String USERNAME_KEY = "username";
    SharedPreferences sharedPreferences;

    public static String loginName = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.contains(ACCESS_TOKEN_KEY) && sharedPreferences.contains(USERNAME_KEY)) {
            AccessToken.getInstance().setAccessToken(sharedPreferences.getString(ACCESS_TOKEN_KEY, null));
            Log.v(TAG, sharedPreferences.getString(ACCESS_TOKEN_KEY, null));
            Log.v(TAG, sharedPreferences.getString(USERNAME_KEY, null));
            setLoginName(sharedPreferences.getString(USERNAME_KEY, null));

            Intent intent = new Intent(this, PostLoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
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
