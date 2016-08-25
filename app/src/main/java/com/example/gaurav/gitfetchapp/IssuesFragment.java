package com.example.gaurav.gitfetchapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.Issue;
import com.example.gaurav.gitfetchapp.Gists.GistsJson;
import com.example.gaurav.gitfetchapp.GooglePlayServices.TrackerApplication;
import com.example.gaurav.gitfetchapp.Issues.IssuesJson;
import com.example.gaurav.gitfetchapp.Issues.IssuesRecyclerAdapter;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GAURAV on 25-08-2016.
 */
public class IssuesFragment extends Fragment {
    @BindView(R.id.fragment_issues_recyclerview)
    RecyclerView issuesRecyclerView;
    @BindView(R.id.issues_empty_cardView)
    CardView empty_cardView;
    @BindView(R.id.networkLayout)
    RelativeLayout networkLayout;
    @BindView(R.id.networkButton)
    Button networkSettings;
    @BindView(R.id.avi) AVLoadingIndicatorView avLoadingIndicatorView;

    private static final String TAG = IssuesFragment.class.getName();
    private LinearLayoutManager layoutManager;
    private IssuesRecyclerAdapter issuesRecyclerAdapter;
    Tracker mTracker;
    BroadcastReceiver broadcastReceiver;
    private boolean connectionLostFlag;

    public IssuesFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        issuesRecyclerAdapter = new IssuesRecyclerAdapter(getContext(), new ArrayList<IssuesJson>());

        if(Utility.hasConnection(getContext())) {
            //avLoadingIndicatorView.show();
            GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                    GitHubEndpointInterface.class, AccessToken.getInstance());
            Call<ArrayList<IssuesJson>> call = gitHubEndpointInterface.getPublicIssues();

            call.enqueue(new Callback<ArrayList<IssuesJson>>() {
                @Override
                public void onResponse(Call<ArrayList<IssuesJson>> call, Response<ArrayList<IssuesJson>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<IssuesJson> item = response.body();
                        if (item.size() > 0)
                            empty_cardView.setVisibility(View.GONE);
                        for (IssuesJson elem : item)
                            issuesRecyclerAdapter.addItem(elem);
                        issuesRecyclerAdapter.notifyDataSetChanged();
                        //avLoadingIndicatorView.hide();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<IssuesJson>> call, Throwable t) {
                    Log.v(TAG, "failed to fetch data");
                    //avLoadingIndicatorView.hide();
                }

            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(
          //      new ColorDrawable(getResources().getColor(R.color.deepPurple500)));
        //new ColorDrawable(getResources().getColor(R.color.red600)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Issues");

        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.setStatusBarColor(getResources().getColor(R.color.red900));
        //window.setStatusBarColor(getResources().getColor(R.color.deepPurple800));
    }

    @Override
    public void onResume() {
        super.onResume();
        mTracker.setScreenName("Image~" + TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                if(!Utility.hasConnection(context)){
                    connectionLostFlag = true;
                    //networkLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(context,R.string.notOnline,Toast.LENGTH_SHORT).show();
                } else if(Utility.hasConnection(context)) {
                    if( connectionLostFlag) {
                        connectionLostFlag = false;
                        //networkLayout.setVisibility(View.GONE);
                        Toast.makeText(context,R.string.online,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        getActivity().registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_issues,container,false);
        ButterKnife.bind(this,rootview);
        networkSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
            }
        });
        TrackerApplication application = (TrackerApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        issuesRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        issuesRecyclerView.addItemDecoration(itemDecoration);
        issuesRecyclerView.setAdapter(issuesRecyclerAdapter);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
