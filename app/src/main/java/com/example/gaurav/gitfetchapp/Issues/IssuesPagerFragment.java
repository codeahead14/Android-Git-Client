package com.example.gaurav.gitfetchapp.Issues;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.AccessToken;
import com.example.gaurav.gitfetchapp.DividerItemDecoration;
import com.example.gaurav.gitfetchapp.EndlessRecyclerViewScrollListener;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.IssuesFragment;
import com.example.gaurav.gitfetchapp.PreLoginDeciderActivity;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.RecyclerViewScrollListener;
import com.example.gaurav.gitfetchapp.RepositoryPagerAdapter;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GAURAV on 04-06-2017.
 */

public class IssuesPagerFragment extends Fragment implements RecyclerViewScrollListener {
    public static final String TAG = IssuesPagerAdapter.class.getName();
    private static final String ARG_PAGE = "page_num";
    private Context context;

    private static String AUTHOR = "author";
    private static String AUTHOR_NAME = "null";
    private static String STATE = "state";
    private static int PAGE_COUNT = 1;
    private IssuesRecyclerAdapter openIssuesRecyclerAdapter,closedIssuesRecyclerAdapter;
    private LinearLayoutManager layoutManager;
    private FrameLayout.LayoutParams layoutParams;
    public String current_page_state = "open";
    private View rootview;
    private static int Tot_Num_issues;
    private boolean connectionLostFlag = false;
    private BroadcastReceiver broadcastReceiver;


    @BindView(R.id.fragment_issues_recyclerview)
    RecyclerView issuesRecyclerView;
    @BindView(R.id.issues_progress_bar)
    MaterialProgressBar materialProgressBar;
    @BindView(R.id.issues_networkLayout)
    RelativeLayout networkLayout;
    @BindView(R.id.issues_networkButton)
    Button networkSettings;

    public static IssuesPagerFragment newInstance(String state) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE, state);

        IssuesPagerFragment fragment = new IssuesPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        current_page_state = getArguments().getString(ARG_PAGE);
        PAGE_COUNT=1;
        if (current_page_state.equals("open")) {
            openIssuesRecyclerAdapter = new IssuesRecyclerAdapter(getContext(), new ArrayList<IssueItem>()
                    ,TAG,0);
            fetchIssues(current_page_state,openIssuesRecyclerAdapter);
        }else if(current_page_state.equals("closed")) {
            closedIssuesRecyclerAdapter = new IssuesRecyclerAdapter(getContext(), new ArrayList<IssueItem>()
                    ,TAG,0);
            fetchIssues(current_page_state,closedIssuesRecyclerAdapter);
        }
    }

    @Override
    public void onResume() {
        Log.v(TAG,"on resume");
        super.onResume();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Utility.hasConnection(context)) {
                    networkLayout.setVisibility(View.GONE);
                    if (rootview != null){
                        ViewGroup vg = (ViewGroup) rootview.findViewById(R.id.issues_pager_linearlayout);
                        vg.invalidate();
                    }
                    if(materialProgressBar!=null){
                        materialProgressBar.setVisibility(View.VISIBLE);
                    }
                    if (current_page_state.equals("open")) {
                        openIssuesRecyclerAdapter = new IssuesRecyclerAdapter(getContext(), new ArrayList<IssueItem>()
                                ,TAG,0);
                        fetchIssues(current_page_state,openIssuesRecyclerAdapter);
                    }else if(current_page_state.equals("closed")) {
                        closedIssuesRecyclerAdapter = new IssuesRecyclerAdapter(getContext(), new ArrayList<IssueItem>()
                                ,TAG,0);
                        fetchIssues(current_page_state,closedIssuesRecyclerAdapter);
                    }
                }
                else if (!Utility.hasConnection(context)) {
                    connectionLostFlag = true;
                    networkLayout.setVisibility(View.GONE);
                }
            }
        };

        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.BOTTOM;
        rootview = inflater.inflate(R.layout.issues_pager_fragment, container, false);
        ButterKnife.bind(this, rootview);

        if(Utility.hasConnection(getContext())) {
            materialProgressBar.setVisibility(View.VISIBLE);
        }else
            materialProgressBar.setVisibility(View.GONE);

        networkSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
            }
        });

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        issuesRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        issuesRecyclerView.addItemDecoration(itemDecoration);
        issuesRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, boolean loading) {
                materialProgressBar.setVisibility(View.VISIBLE);
                if(current_page_state.equals("open")) {
                    openIssuesRecyclerAdapter.updateState(0);
                    fetchIssues(current_page_state, openIssuesRecyclerAdapter);
                }else if(current_page_state.equals("closed")) {
                    closedIssuesRecyclerAdapter.updateState(0);
                    fetchIssues(current_page_state, closedIssuesRecyclerAdapter);
                }
            }
        });

        return rootview;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (current_page_state.equals("open")) {
            issuesRecyclerView.setAdapter(openIssuesRecyclerAdapter);
        } else if (current_page_state.equals("closed")) {
            issuesRecyclerView.setAdapter(closedIssuesRecyclerAdapter);
        }
    }

    void fetchIssues(final String page_state,final IssuesRecyclerAdapter adapter){
        /*
        Adding Default author parameter to fetch query
         */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        AUTHOR_NAME = prefs.getString(PreLoginDeciderActivity.USERNAME_KEY,null);
        String author_params = String.format("%s:%s+%s:%s",AUTHOR,AUTHOR_NAME,STATE,page_state);
        if (Utility.hasConnection(getContext())) {
            GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                    GitHubEndpointInterface.class, AccessToken.getInstance());
            Call<IssuesJson> call = gitHubEndpointInterface.getIssues(author_params,PAGE_COUNT,30);
            PAGE_COUNT++;
            call.enqueue(new Callback<IssuesJson>() {
                @Override
                public void onResponse(Call<IssuesJson> call, Response<IssuesJson> response) {
                    if (response.isSuccessful()) {
                        materialProgressBar.setVisibility(View.GONE);
                        adapter.updateState(1);
                        IssuesJson issuesResponse = response.body();
                        if(issuesResponse.getTotalCount() <= 0)
                            Tot_Num_issues = 0;
                        List<IssueItem> item = issuesResponse.getItems();
                        for (IssueItem elem : item)
                            adapter.addItem(elem);
                        adapter.notifyDataSetChanged();
                        //materialProgressBar.setVisibility(View.GONE);
                    }else if (response.code() == 408){
                    }
                }

                @Override
                public void onFailure(Call<IssuesJson> call, Throwable t) {
                    adapter.updateState(1);
                    materialProgressBar.setVisibility(View.GONE);
                }

            });
        }
    }

    @Override
    public void OnLoadMore() {

    }
}
