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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PublicEventsFragment.OnPublicEventsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PublicEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    private PublicEventsRecyclerAdapter publicEventsRecyclerAdapter;
    @BindView(R.id.networkLayout)
    RelativeLayout networkLayout;
    @BindView(R.id.networkButton)
    Button networkSettings;
    @BindView(R.id.public_events_recyclerview)
    RecyclerView public_events_recyclerview;
    @BindView(R.id.events_progress_bar)
    MaterialProgressBar materialProgressBar;

    private OnPublicEventsFragmentInteractionListener mListener;

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
        mTracker.setScreenName("Image~" + TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                if(!Utility.hasConnection(context)){
                    connectionLostFlag = true;
                    networkLayout.setVisibility(View.VISIBLE);
                } else if(Utility.hasConnection(context)) {
                    if( connectionLostFlag) {
                        connectionLostFlag = false;
                        networkLayout.setVisibility(View.GONE);
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
        } else
            Toast.makeText(getContext(),R.string.notOnline,Toast.LENGTH_LONG).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_public_events,container,false);
        ButterKnife.bind(this,rootView);
        Log.v(TAG,"on Create View");

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.OnPublicEventsFragmentListener(uri);
        }
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
        mListener = null;
    }

    @Override
    public void onDataFetchFinishedCallback() {
        materialProgressBar.setVisibility(View.GONE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPublicEventsFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnPublicEventsFragmentListener(ProgressBar progressBar);
    }
}
