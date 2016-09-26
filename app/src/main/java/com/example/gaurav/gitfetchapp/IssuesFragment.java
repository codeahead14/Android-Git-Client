package com.example.gaurav.gitfetchapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.Issue;
import com.example.gaurav.gitfetchapp.Gists.GistsJson;
import com.example.gaurav.gitfetchapp.GooglePlayServices.TrackerApplication;
import com.example.gaurav.gitfetchapp.Issues.IssueItem;
import com.example.gaurav.gitfetchapp.Issues.IssuesJson;
import com.example.gaurav.gitfetchapp.Issues.IssuesRecyclerAdapter;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GAURAV on 25-08-2016.
 */
public class IssuesFragment extends Fragment implements RecyclerViewScrollListener {
    @BindView(R.id.fragment_issues_recyclerview)
    RecyclerView issuesRecyclerView;
    //@BindView(R.id.issues_empty_cardView)
    //CardView empty_cardView;
    @BindView(R.id.networkLayout)
    RelativeLayout networkLayout;
    @BindView(R.id.networkButton)
    Button networkSettings;
    @BindView(R.id.issues_progress_bar)
    MaterialProgressBar materialProgressBar;

    private CardView empty_cardView;
    private static final String TAG = IssuesFragment.class.getName();
    private LinearLayoutManager layoutManager;
    private IssuesRecyclerAdapter issuesRecyclerAdapter;
    Tracker mTracker;
    BroadcastReceiver broadcastReceiver;
    private boolean connectionLostFlag;
    private static int Tot_Num_issues;
    public static final int PER_PAGE = 100;
    public static int PAGE_COUNT = 1;
    private static String AUTHOR = "author";
    private static String AUTHOR_NAME = "null";
    private FrameLayout.LayoutParams layoutParams;

    public static int loadingIndicator = 0;

    public IssuesFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PAGE_COUNT=1;
        issuesRecyclerAdapter = new IssuesRecyclerAdapter(getContext(), new ArrayList<IssueItem>());
        fetchIssues();
    }

    void fetchIssues(){
                /*
        Adding Default author parameter to fetch query
         */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        AUTHOR_NAME = prefs.getString(PreLoginDeciderActivity.USERNAME_KEY,null);
        String author_params = String.format("%s:%s",AUTHOR,"hemanth");

        if (Utility.hasConnection(getContext())) {
            //avLoadingIndicatorView.show();
            GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                    GitHubEndpointInterface.class, AccessToken.getInstance());
            Call<IssuesJson> call = gitHubEndpointInterface.getIssues(author_params,PAGE_COUNT,30);
            PAGE_COUNT++;
            call.enqueue(new Callback<IssuesJson>() {
                @Override
                public void onResponse(Call<IssuesJson> call, Response<IssuesJson> response) {
                    if (response.isSuccessful()) {
                        loadingIndicator = 1;
                        IssuesJson issuesResponse = response.body();
                        if(issuesResponse.getTotalCount() <= 0)
                            Tot_Num_issues = 0;
                        List<IssueItem> item = issuesResponse.getItems();
                        for (IssueItem elem : item)
                            issuesRecyclerAdapter.addItem(elem);
                        issuesRecyclerAdapter.notifyDataSetChanged();
                        materialProgressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<IssuesJson> call, Throwable t) {
                    Log.v(TAG,"Issues Fetch Failed: "+t.getMessage());
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

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
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
            public void onReceive(Context context, Intent intent) {
                if (!Utility.hasConnection(context)) {
                    connectionLostFlag = true;
                    //networkLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(context, R.string.notOnline, Toast.LENGTH_SHORT).show();
                } else if (Utility.hasConnection(context)) {
                    if (connectionLostFlag) {
                        connectionLostFlag = false;
                        //networkLayout.setVisibility(View.GONE);
                        Toast.makeText(context, R.string.online, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.BOTTOM;
        View rootview = inflater.inflate(R.layout.fragment_issues, container, false);
        ButterKnife.bind(this, rootview);


        //if(Tot_Num_issues <= 0)
          //  empty_cardView.setVisibility(View.VISIBLE);
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
        issuesRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                //materialProgressBar.setVisibility(View.VISIBLE);
                loadingIndicator = 0;
                fetchIssues();
            }
        });
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void OnLoadMore() {

    }
}
