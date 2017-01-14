package com.example.gaurav.gitfetchapp.Repositories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.DataBus.BusProvider;
import com.example.gaurav.gitfetchapp.DataBus.UserInteractionEvent;
import com.example.gaurav.gitfetchapp.DividerItemDecoration;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.Utility;
import com.squareup.otto.Subscribe;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 20th November, 2016
 * Transporting branchdialogactivity functionalities to a BranchDialogFragment
 * Refer: https://guides.codepath.com/android/Using-DialogFragment
 * **/

public class BranchDialogActivity extends AppCompatActivity {
    private static final String TAG = BranchDialogActivity.class.getName();
    private BranchRecyclerAdapter branchRecyclerAdapter;
    private GitHubEndpointInterface gitHubEndpointInterface;

    @BindView(R.id.dialog_branch_recycler) RecyclerView dialog_branch_recyclerview;
    @BindView(R.id.avi) AVLoadingIndicatorView avLoadingIndicatorView;
    //@BindView(R.id.branch_cancel_button) Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_dialog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String[] intentValues = intent.getExtras().getStringArray(Intent.EXTRA_TEXT);
        ButterKnife.bind(this);

        branchRecyclerAdapter = new BranchRecyclerAdapter(this, new ArrayList<BranchesJson>(),
                intentValues[2]);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dialog_branch_recyclerview.setLayoutManager(layoutManager);
        //RecyclerView.ItemDecoration itemDecoration = new
          //    DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        //dialog_branch_recyclerview.addItemDecoration(itemDecoration);
        dialog_branch_recyclerview.setAdapter(branchRecyclerAdapter);

        gitHubEndpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class);
        Call<ArrayList<BranchesJson>> call = gitHubEndpointInterface.getUserBranches(
                intentValues[0], intentValues[1]);
        if(Utility.hasConnection(this)) {
            //avLoadingIndicatorView.show();
            call.enqueue(new Callback<ArrayList<BranchesJson>>() {
                @Override
                public void onResponse(Call<ArrayList<BranchesJson>> call, Response<ArrayList<BranchesJson>> response) {
                    ArrayList<BranchesJson> item = response.body();
                    //branchRecyclerAdapter.clear();
                    for (BranchesJson elem : item) {
                        branchRecyclerAdapter.addItem(elem);
                    }
                    branchRecyclerAdapter.notifyDataSetChanged();
                    //avLoadingIndicatorView.hide();
                }

                @Override
                public void onFailure(Call<ArrayList<BranchesJson>> call, Throwable t) {
                    //avLoadingIndicatorView.hide();
                }
            });
        }else
            Toast.makeText(BranchDialogActivity.this, R.string.notOnline, Toast.LENGTH_SHORT).show();
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG,"registering bus");
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG,"unregistering bus");
        BusProvider.getInstance().unregister(this);
    }*/

    @OnClick(R.id.branch_cancel_button) void cancelActivity(){
        finish();
    }

    @Subscribe
    public void onBranchSelected(UserInteractionEvent event){
        Log.v(TAG,"Bus data received");
        if(event.getResult()){
            Log.v(TAG,"Result on Bus: "+event.getName());
            Intent returnIntent = new Intent();
            returnIntent.putExtra(Intent.EXTRA_TEXT,event.getName());
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
    }
}
