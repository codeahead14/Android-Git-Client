package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.IntentServices.RepositoryDetailsReceiver;
import com.example.gaurav.gitfetchapp.IntentServices.RepositoryDetailsService;
import com.example.gaurav.gitfetchapp.R;

public class RepositoryDetailActivity extends AppCompatActivity implements RepositoryDetailsReceiver.Receiver,
        RepositoryDetailsService.ServiceCallback{

    public RepositoryDetailActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_detail);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.commit();

    }

    @Override
    public void serviceCallbackListener() {
    }
}
