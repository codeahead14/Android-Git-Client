package com.example.gaurav.gitfetchapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.gaurav.gitfetchapp.Repositories.RepositoryAdapter;
import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GAURAV on 28-07-2016.
 */
public class RepositoriesFragment extends Fragment {
    private static final String TAG = RepositoriesFragment.class.getName();
    public static final String REPO_NAMES_PREFS = "repo_names_prefs";
    private View rootView;
    private AccessToken mAccessToken;
    private Intent intent;
    private RepositoryAdapter mRepositoryAdapter;
    private static ArrayList<UserRepoJson> userRepoList = new ArrayList<>();
    private boolean hasRepositoryData = false;
    private ArrayList<String> repoNames;
    private Context mContext;

    @BindView(R.id.repository_recycler) RecyclerView repoRecyclerView;

    public RepositoriesFragment() {
        mAccessToken = AccessToken.getInstance();
        Log.v(TAG,"access token: "+mAccessToken.getAccessToken());
        repoNames = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        intent = getActivity().getIntent();
        //String token = intent.getExtras().getString(Intent.EXTRA_TEXT);
        String[] intentData = intent.getExtras().getStringArray(Intent.EXTRA_TEXT);
        String token = intentData[1];
        GitHubEndpointInterface gitInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class, mAccessToken);
        Call<ArrayList<UserRepoJson>> call = gitInterface.getUserRepositories();

        mRepositoryAdapter = new RepositoryAdapter(getContext(), R.layout.repository_cardview,
                userRepoList);

        call.enqueue(new Callback<ArrayList<UserRepoJson>>() {
            @Override
            public void onResponse(Call<ArrayList<UserRepoJson>> call, Response<ArrayList<UserRepoJson>> response) {
                if(response.isSuccessful()) {
                    ArrayList<UserRepoJson> item = response.body();
                    hasRepositoryData = true;
                    mRepositoryAdapter.clear();
                    for (UserRepoJson elem : item){
                        mRepositoryAdapter.addItem(elem);
                        repoNames.add(elem.getName());
                    }
                    mRepositoryAdapter.notifyDataSetChanged();
                    mRepositoryAdapter.notifyDataSetChanged();
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putStringSet(REPO_NAMES_PREFS, new HashSet<String>(repoNames));
                    editor.apply();
                }
                //repoRecyclerView.setAdapter(mRepositoryAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<UserRepoJson>> call, Throwable t) {
                Log.v(TAG,"Stack trace"+t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                             Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_repositories, containter, false);
        ButterKnife.bind(this,rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        repoRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        repoRecyclerView.addItemDecoration(itemDecoration);
        repoRecyclerView.setAdapter(mRepositoryAdapter);
        //}
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Repositories");

        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

}
