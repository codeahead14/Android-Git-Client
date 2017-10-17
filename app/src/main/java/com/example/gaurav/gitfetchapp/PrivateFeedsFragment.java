package com.example.gaurav.gitfetchapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Credentials;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.Events.EventsAsyncTask;
import com.example.gaurav.gitfetchapp.Events.EventsJson;
import com.example.gaurav.gitfetchapp.Events.PublicEventsRecyclerAdapter;
import com.example.gaurav.gitfetchapp.Feeds.FeedsJson;
import com.example.gaurav.gitfetchapp.Feeds.TimelineJson.Feed;
import com.example.gaurav.gitfetchapp.Repositories.EventsRecyclerAdapter;
import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;
import com.google.android.gms.analytics.HitBuilders;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by GAURAV on 09-08-2016.
 */
public class PrivateFeedsFragment extends Fragment implements OnDataFetchFinished{
    private static final String TAG = FeedsFragment.class.getName();
    private View rootView;
    private AccessToken mAccessToken;
    private static final String API_BASE_URL = "https://api.github.com";
    private PublicEventsRecyclerAdapter eventsRecyclerAdapter;
    private static int PAGE_NUM = 1;
    private String userName;
    public static int loadingEvents = 0;
    private BroadcastReceiver broadcastReceiver;
    private boolean viewLoaded = false;;
    private boolean connectionLostFlag;

    @BindView(R.id.privatefeeds_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.privatefeeds_progress_bar)
    MaterialProgressBar materialProgressBar;
    @BindView(R.id.pvt_feeds_networkButton)
    Button networkButton;
    @BindView(R.id.pvt_feeds_networkLayout)
    RelativeLayout networkLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccessToken = AccessToken.getInstance();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userName = prefs.getString(PreLoginDeciderActivity.USERNAME_KEY,null);
        //loadingEvents = false;
        eventsRecyclerAdapter = new PublicEventsRecyclerAdapter(getContext(),new ArrayList<EventsJson>());
        PAGE_NUM = 1;
        viewLoaded = false;
        /*
            Using HTTP Async Task
         */
        if(Utility.hasConnection(getContext())) {
            String url = "https://api.github.com/users/"+userName+"/received_events?page="+PAGE_NUM;
            EventsAsyncTask eventsAsyncTask = new EventsAsyncTask(eventsRecyclerAdapter,this);
            eventsAsyncTask.execute(url);
            //loadingEvents = 1;
            PAGE_NUM += 1;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(
        //      new ColorDrawable(getResources().getColor(R.color.deepPurple500)));
        //new ColorDrawable(getResources().getColor(R.color.red600)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Private Feeds");
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

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                if(!Utility.hasConnection(context) && !viewLoaded){
                    connectionLostFlag = true;
                    materialProgressBar.setVisibility(View.GONE);
                    networkLayout.setVisibility(View.GONE);
                } else if(Utility.hasConnection(context)) {
                    if( connectionLostFlag) {
                        connectionLostFlag = false;
                        networkLayout.setVisibility(View.GONE);
                        if(rootView != null) {
                            ViewGroup vg = (ViewGroup) rootView.findViewById(R.id.pvt_feeds_framelayout);
                            //if (vg != null)
                                vg.invalidate();
                        }
                        if (materialProgressBar != null) {
                            materialProgressBar.setVisibility(View.VISIBLE);
                        }

                        /*
                        Using HTTP Async Task
                        */
                        String url = "https://api.github.com/users/" + userName + "/received_events?page=" + PAGE_NUM;
                        EventsAsyncTask eventsAsyncTask = new EventsAsyncTask(eventsRecyclerAdapter,
                                PrivateFeedsFragment.this);
                        eventsAsyncTask.execute(url);
                        //loadingEvents = 1;
                        PAGE_NUM += 1;
                    }
                }
            }
        };

        getActivity().registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_private_feeds,container,false);
        ButterKnife.bind(this,rootView);

        materialProgressBar.setVisibility(View.VISIBLE);
        networkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, boolean loading) {
                loadingEvents = 0;
                String url = "https://api.github.com/users/"+userName+"/received_events?page="+PAGE_NUM;
                EventsAsyncTask eventsAsyncTask = new EventsAsyncTask(eventsRecyclerAdapter,PrivateFeedsFragment.this);
                eventsAsyncTask.execute(url);
                PAGE_NUM += 1;
            }
        });

        recyclerView.setAdapter( eventsRecyclerAdapter);

        return rootView;
    }

    @Override
    public void onDataFetchFinishedCallback() {
        loadingEvents = 1;
        viewLoaded = true;
        materialProgressBar.setVisibility(View.GONE);
    }
}
