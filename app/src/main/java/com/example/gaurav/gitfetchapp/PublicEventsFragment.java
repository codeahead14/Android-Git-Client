package com.example.gaurav.gitfetchapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.Events.EventsAsyncTask;
import com.example.gaurav.gitfetchapp.Events.EventsJson;
import com.example.gaurav.gitfetchapp.Events.Payload;
import com.example.gaurav.gitfetchapp.Events.PublicEventsRecyclerAdapter;
import com.example.gaurav.gitfetchapp.GooglePlayServices.TrackerApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicEventsFragment extends Fragment implements OnDataFetchFinished {
    private static final String TAG = PublicEventsFragment.class.getName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int PAGE_NUM = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Tracker mTracker;
    BroadcastReceiver broadcastReceiver;
    private boolean connectionLostFlag;
    private boolean viewLoaded;
    private View rootView;

    private PublicEventsRecyclerAdapter publicEventsRecyclerAdapter;
    @BindView(R.id.public_feeds_networkLayout)
    RelativeLayout networkLayout;
    @BindView(R.id.public_feeds_networkButton)
    Button networkSettings;
    @BindView(R.id.public_events_recyclerview)
    RecyclerView public_events_recyclerview;
    @BindView(R.id.events_progress_bar)
    MaterialProgressBar materialProgressBar;

    public PublicEventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublicEventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PublicEventsFragment newInstance(String param1, String param2) {
        PublicEventsFragment fragment = new PublicEventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
                            ViewGroup vg = (ViewGroup) rootView.findViewById(R.id.public_feeds_framelayout);
                            //if (vg != null)
                                vg.invalidate();
                        }
                        if (materialProgressBar != null) {
                            materialProgressBar.setVisibility(View.VISIBLE);
                        }
                        String url = "https://api.github.com/events?page="+PAGE_NUM;
                        EventsAsyncTask eventsAsyncTask = new EventsAsyncTask(publicEventsRecyclerAdapter,
                                    PublicEventsFragment.this);
                        eventsAsyncTask.execute(url);
                        PAGE_NUM += 1;
                    }
                }
            }
        };

        getActivity().registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(
          //      new ColorDrawable(getResources().getColor(R.color.deepPurple500)));
                //new ColorDrawable(getResources().getColor(R.color.red600)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Public Events");

        Window window = getActivity().getWindow();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //window.setStatusBarColor(getResources().getColor(R.color.red900));
        //window.setStatusBarColor(getResources().getColor(R.color.deepPurple800));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        PAGE_NUM = 1;
        viewLoaded = false;
        publicEventsRecyclerAdapter = new PublicEventsRecyclerAdapter(getContext(),new ArrayList<EventsJson>());
        TrackerApplication application = (TrackerApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        /*
            Using HTTP Async Task
         */
        if(Utility.hasConnection(getContext())) {
            String url = "https://api.github.com/events?page="+PAGE_NUM;
            EventsAsyncTask eventsAsyncTask = new EventsAsyncTask(publicEventsRecyclerAdapter,this);
            eventsAsyncTask.execute(url);
            PAGE_NUM += 1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_public_events,container,false);
        ButterKnife.bind(this,rootView);
        materialProgressBar.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        public_events_recyclerview.setLayoutManager(layoutManager);
        public_events_recyclerview.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, boolean loading) {
                String url = "https://api.github.com/events?page="+PAGE_NUM;
                EventsAsyncTask eventsAsyncTask = new EventsAsyncTask(publicEventsRecyclerAdapter,
                        PublicEventsFragment.this);
                eventsAsyncTask.execute(url);
                PAGE_NUM += 1;
            }
        });
        public_events_recyclerview.setAdapter( publicEventsRecyclerAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnPublicEventsFragmentInteractionListener) {
            mListener = (OnPublicEventsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDataFetchFinishedCallback() {
        viewLoaded = true;
        materialProgressBar.setVisibility(View.GONE);
    }
}
