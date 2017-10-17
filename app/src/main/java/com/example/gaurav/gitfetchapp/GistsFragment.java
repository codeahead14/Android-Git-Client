package com.example.gaurav.gitfetchapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.Gists.GistsJson;
import com.example.gaurav.gitfetchapp.Gists.GistsRecyclerAdapter;
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
 * {@link GistsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GistsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GistsFragment extends Fragment {
    private static final String TAG = GistsFragment.class.getName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Tracker mTracker;
    BroadcastReceiver broadcastReceiver;
    @BindView(R.id.gists_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.networkLayout)
    RelativeLayout networkLayout;
    @BindView(R.id.networkButton)
    Button networkSettings;
    @BindView(R.id.gists_progress_bar)
    MaterialProgressBar materialProgressBar;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;
    private boolean connectionLostFlag;
    private Snackbar connectionSnackBar;
    private OnFragmentInteractionListener mListener;
    private GistsRecyclerAdapter gistsRecyclerAdapter;
    private String owner;
    private boolean viewLoaded;

    public GistsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GistsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GistsFragment newInstance(String param1, String param2) {
        GistsFragment fragment = new GistsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        Log.v(TAG,"param1 "+param1);
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
                if(!Utility.hasConnection(context) && !viewLoaded){
                    connectionLostFlag = true;
                    materialProgressBar.setVisibility(View.GONE);
                    networkLayout.setVisibility(View.GONE);
                } else if(Utility.hasConnection(context)) {
                    if( connectionLostFlag) {
                        connectionLostFlag = false;
                        networkLayout.setVisibility(View.GONE);
                        if(rootView != null) {
                            ViewGroup vg = (ViewGroup) rootView.findViewById(R.id.gists_fragment_framelayout);
//                            if (vg != null) {
                                vg.invalidate();
                        }
                        if(materialProgressBar!=null){
                            materialProgressBar.setVisibility(View.VISIBLE);
                        }
                        getGists();
                    }
                }
            }
        };

        getActivity().registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        viewLoaded = false;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        owner = prefs.getString(PreLoginDeciderActivity.USERNAME_KEY,null);
        gistsRecyclerAdapter = new GistsRecyclerAdapter(getContext(), new ArrayList<GistsJson>());
        getGists();

    }

    private void getGists(){
        if(Utility.hasConnection(getContext())) {
            GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                    GitHubEndpointInterface.class);
            Call<ArrayList<GistsJson>> call = gitHubEndpointInterface.getPrivateGists(owner);
            //PreLoginDeciderActivity.getLoginName());
            call.enqueue(new Callback<ArrayList<GistsJson>>() {
                @Override
                public void onResponse(Call<ArrayList<GistsJson>> call, Response<ArrayList<GistsJson>> response) {
                    ArrayList<GistsJson> gists = response.body();
                    gistsRecyclerAdapter.clear();
                    for (GistsJson elem : gists)
                        gistsRecyclerAdapter.addItem(elem);
                    Log.v(TAG,"item added");
                    gistsRecyclerAdapter.notifyDataSetChanged();
                    viewLoaded = true;
                    materialProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ArrayList<GistsJson>> call, Throwable t) {
                    Log.v(TAG, "Failed Miserably in Gists" + t.getMessage());
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Gists");

        Window window = getActivity().getWindow();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //window.setStatusBarColor(getResources().getColor(R.color.red900));
        //window.setStatusBarColor(getResources().getColor(R.color.deepPurple800));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gists, container, false);
        ButterKnife.bind(this,rootView);
        networkSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
            }
        });

        TrackerApplication application = (TrackerApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        /*RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);*/
        recyclerView.setAdapter(gistsRecyclerAdapter);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
