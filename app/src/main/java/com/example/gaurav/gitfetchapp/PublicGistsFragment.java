package com.example.gaurav.gitfetchapp;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.Gists.GistsJson;
import com.example.gaurav.gitfetchapp.Gists.GistsRecyclerAdapter;
import com.example.gaurav.gitfetchapp.GooglePlayServices.TrackerApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublicGistsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class PublicGistsFragment extends Fragment {
    public static final String TAG = PublicGistsFragment.class.getName();

    @BindView(R.id.gists_progress_bar)
    MaterialProgressBar materialProgressBar;
    Tracker mTracker;
    BroadcastReceiver broadcastReceiver;
    @BindView(R.id.gists_recycler_view)
    RecyclerView recyclerView;

    private GistsRecyclerAdapter gistsRecyclerAdapter;

    public static PublicGistsFragment newInstance(String param1, String param2) {
        PublicGistsFragment fragment = new PublicGistsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public PublicGistsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(
        //      new ColorDrawable(getResources().getColor(R.color.deepPurple500)));
        //new ColorDrawable(getResources().getColor(R.color.red600)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Public Gists");

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
        gistsRecyclerAdapter = new GistsRecyclerAdapter(getContext(), new ArrayList<GistsJson>());

        if(Utility.hasConnection(getContext())) {
            GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                    GitHubEndpointInterface.class);
            Call<ArrayList<GistsJson>> call = gitHubEndpointInterface.getPublicGists();
            call.enqueue(new Callback<ArrayList<GistsJson>>() {
                @Override
                public void onResponse(Call<ArrayList<GistsJson>> call, Response<ArrayList<GistsJson>> response) {
                    if(response.isSuccessful()) {
                        Log.v(TAG,"response "+response.body().size());
                        ArrayList<GistsJson> gists = response.body();
                        gistsRecyclerAdapter.clear();
                        for (GistsJson elem : gists)
                            gistsRecyclerAdapter.addItem(elem);
                        gistsRecyclerAdapter.notifyDataSetChanged();
                    }
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
    public void onResume() {
        super.onResume();
        mTracker.setScreenName("Image~" + TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gists, container, false);
        ButterKnife.bind(this,rootView);
        TrackerApplication application = (TrackerApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        if(Utility.hasConnection(getContext())){
            materialProgressBar.setVisibility(View.VISIBLE);
        }else
            materialProgressBar.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(gistsRecyclerAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
