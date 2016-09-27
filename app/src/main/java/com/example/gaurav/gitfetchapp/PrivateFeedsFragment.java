package com.example.gaurav.gitfetchapp;

import android.content.SharedPreferences;
import android.net.Credentials;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.Events.EventsAsyncTask;
import com.example.gaurav.gitfetchapp.Events.EventsJson;
import com.example.gaurav.gitfetchapp.Events.PublicEventsRecyclerAdapter;
import com.example.gaurav.gitfetchapp.Feeds.FeedsJson;
import com.example.gaurav.gitfetchapp.Feeds.TimelineJson.Feed;
import com.example.gaurav.gitfetchapp.Repositories.EventsRecyclerAdapter;
import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;
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
    private boolean loading = false;

    @BindView(R.id.privatefeeds_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.privatefeeds_progress_bar)
    MaterialProgressBar materialProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccessToken = AccessToken.getInstance();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userName = prefs.getString(PreLoginDeciderActivity.USERNAME_KEY,null);

        eventsRecyclerAdapter = new PublicEventsRecyclerAdapter(getContext(),new ArrayList<EventsJson>());
        PAGE_NUM = 1;
        /*
            Using HTTP Async Task
         */
        if(Utility.hasConnection(getContext())) {
            String url = "https://api.github.com/users/"+userName+"/received_events?page="+PAGE_NUM;
            Log.v(TAG, url);
            EventsAsyncTask eventsAsyncTask = new EventsAsyncTask(eventsRecyclerAdapter,this);
            eventsAsyncTask.execute(url);
            PAGE_NUM += 1;
        } else
            Toast.makeText(getContext(),R.string.notOnline,Toast.LENGTH_LONG).show();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_private_feeds,container,false);
        ButterKnife.bind(this,rootView);
        materialProgressBar.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition, itemCount, threshold = 5, previousCount;
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                itemCount = linearLayoutManager.getItemCount();
                if(dy > 0 && itemCount - lastVisibleItemPosition < threshold) {
                    Log.v(TAG,"itemcount and lastVisibleItemPosition"+itemCount+" "+lastVisibleItemPosition);
                    if(!loading) {
                        loading = true;
                        String url = "https://api.github.com/users/"+userName+"/received_events?page="+PAGE_NUM;
                        EventsAsyncTask eventsAsyncTask = new EventsAsyncTask(eventsRecyclerAdapter,PrivateFeedsFragment.this);
                        eventsAsyncTask.execute(url);
                        PAGE_NUM += 1;
                        Toast.makeText(getContext(), "Approaching end", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        /*recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.v(TAG,"On Load More");
                String url = "https://api.github.com/users/"+userName+"/received_events?page="+PAGE_NUM;
                EventsAsyncTask eventsAsyncTask = new EventsAsyncTask(eventsRecyclerAdapter,PrivateFeedsFragment.this);
                eventsAsyncTask.execute(url);
                PAGE_NUM += 1;
            }
        });*/

        recyclerView.setAdapter( eventsRecyclerAdapter);

        return rootView;
    }

    @Override
    public void onDataFetchFinishedCallback() {
        loading = false;
        materialProgressBar.setVisibility(View.GONE);
    }
}
