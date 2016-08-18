package com.example.gaurav.gitfetchapp.Repositories;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gaurav.gitfetchapp.AccessToken;
import com.example.gaurav.gitfetchapp.DividerItemDecoration;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Events.EventsJson;
import com.example.gaurav.gitfetchapp.Repositories.BranchDetails.BranchDetailJson;
import com.example.gaurav.gitfetchapp.Repositories.Commits.CommitsRepoJson;
import com.example.gaurav.gitfetchapp.Repositories.TreeDetails.*;
import com.example.gaurav.gitfetchapp.ServiceGenerator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GAURAV on 06-08-2016.
 */
public class RepositoryPagerFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String USER_REPO = "USER_REPO";
    private static final String TAG = RepositoryPagerFragment.class.getName();
    private int mPage;
    private UserRepoJson userRepoJson;
    private BranchRecyclerAdapter branchRecyclerAdapter;
    private FilesRecyclerAdapter filesRecyclerAdapter;
    private EventsRecyclerAdapter eventsRecyclerAdapter;
    private CommitsRecyclerAdapter commitsRecyclerAdapter;
    private String default_branch;
    private String repo_branch;

    private GitHubEndpointInterface gitHubEndpointInterface;

    @BindView(R.id.branches_recyclerview) RecyclerView branchesRecyclerView;

    public static RepositoryPagerFragment newInstance(int page, UserRepoJson item) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putParcelable(USER_REPO, item);
        RepositoryPagerFragment fragment = new RepositoryPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*private void fetchTree(String sha){
        String owner = userRepoJson.getOwner().getLogin();
        String repo = userRepoJson.getName();
        Call<TreeDetailsJson> call = gitHubEndpointInterface.getRepoTree(owner,repo,"trees",sha);
        call.enqueue(new Callback<TreeDetailsJson>() {
            @Override
            public void onResponse(Call<TreeDetailsJson> call, Response<TreeDetailsJson> response) {
                TreeDetailsJson item = response.body();
                for(com.example.gaurav.gitfetchapp.Repositories.TreeDetails.Tree elem : item.getTree())
                    filesRecyclerAdapter.addItem(elem);
                filesRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TreeDetailsJson> call, Throwable t) {

            }
        });
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        userRepoJson = getArguments().getParcelable(USER_REPO);
        default_branch = userRepoJson.getDefaultBranch();
        repo_branch = RepositoryDetailActivityFragment.repoBranch;
        gitHubEndpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class);
        fetchPagerData();
    }

    private void fetchPagerData(){
        repo_branch = RepositoryDetailActivityFragment.repoBranch;
        Log.v(TAG,"repo branch in Pager: "+repo_branch);
        if(mPage==1) {
            /*branchRecyclerAdapter = new BranchRecyclerAdapter(getContext(), new ArrayList<BranchesJson>(),
                    default_branch);
            Call<ArrayList<BranchesJson>> call = gitHubEndpointInterface.getUserBranches(
                    userRepoJson.getOwner().getLogin(), userRepoJson.getName());
            call.enqueue(new Callback<ArrayList<BranchesJson>>() {
                @Override
                public void onResponse(Call<ArrayList<BranchesJson>> call, Response<ArrayList<BranchesJson>> response) {
                    ArrayList<BranchesJson> item = response.body();
                    branchRecyclerAdapter.clear();
                    for (BranchesJson elem : item) {
                        branchRecyclerAdapter.addItem(elem);
                    }
                    branchRecyclerAdapter.notifyDataSetChanged();
                    Log.v(TAG, "response: " + item.size());
                }

                @Override
                public void onFailure(Call<ArrayList<BranchesJson>> call, Throwable t) {

                }
            });*/

        } else if(mPage == 2) {
            //filesRecyclerAdapter = new FilesRecyclerAdapter(getContext(), new TreeDetailsJson(),
            //      userRepoJson.getOwner().getLogin(), userRepoJson.getName());
            //Call<BranchDetailJson> call = gitHubEndpointInterface.getBranchDetails(
            //      userRepoJson.getOwner().getLogin(), userRepoJson.getName(),default_branch);
            /*call.enqueue(new Callback<BranchDetailJson>() {
                @Override
                public void onResponse(Call<BranchDetailJson> call, Response<BranchDetailJson> response) {
                    BranchDetailJson branchDetailJson = response.body();
                    //fetchTree(branchDetailJson.getCommit().getCommit().getTree().getUrl());
                    fetchTree(branchDetailJson.getCommit().getCommit().getTree().getSha());

                }

                @Override
                public void onFailure(Call<BranchDetailJson> call, Throwable t) {

                }
            });*/
            filesRecyclerAdapter = new FilesRecyclerAdapter(getContext(), new ArrayList<RepoContentsJson>(),
                    userRepoJson.getOwner().getLogin(), userRepoJson.getName(),repo_branch);
            Call<ArrayList<RepoContentsJson>> call = gitHubEndpointInterface.getRepoContents(
                    userRepoJson.getOwner().getLogin(), userRepoJson.getName(),"",repo_branch);
            call.enqueue(new Callback<ArrayList<RepoContentsJson>>() {
                @Override
                public void onResponse(Call<ArrayList<RepoContentsJson>> call, Response<ArrayList<RepoContentsJson>> response) {
                    if(response.isSuccessful()) {
                        ArrayList<RepoContentsJson> item = response.body();
                        for (RepoContentsJson elem : item)
                            filesRecyclerAdapter.addItem(elem);
                        filesRecyclerAdapter.notifyDataSetChanged();
                        Log.v(TAG, "size: " + item.size());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<RepoContentsJson>> call, Throwable t) {

                }
            });
        } else if(mPage == 3){
            GitHubEndpointInterface endpointInterface = ServiceGenerator.createService(
                    GitHubEndpointInterface.class, AccessToken.getInstance());
            commitsRecyclerAdapter = new CommitsRecyclerAdapter(getContext(), new ArrayList<CommitsRepoJson>());
            Call<ArrayList<CommitsRepoJson>> call = endpointInterface.getRepoCommits(
                    userRepoJson.getOwner().getLogin(),userRepoJson.getName());
            call.enqueue(new Callback<ArrayList<CommitsRepoJson>>() {
                @Override
                public void onResponse(Call<ArrayList<CommitsRepoJson>> call, Response<ArrayList<CommitsRepoJson>> response) {
                    if(response.isSuccessful()) {
                        ArrayList<CommitsRepoJson> list = response.body();
                        Log.v(TAG,"commits size: "+list.size());
                        //commitsRecyclerAdapter.clear();
                        for (CommitsRepoJson elem : list)
                            commitsRecyclerAdapter.addItem(elem);
                        commitsRecyclerAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<CommitsRepoJson>> call, Throwable t) {

                }
            });

            /*eventsRecyclerAdapter = new EventsRecyclerAdapter(getContext(),new ArrayList<EventsJson>());
            Call<ArrayList<EventsJson>> call = gitHubEndpointInterface.getRepoEvents(
                    userRepoJson.getOwner().getLogin(), userRepoJson.getName());
            call.enqueue(new Callback<ArrayList<EventsJson>>() {
                @Override
                public void onResponse(Call<ArrayList<EventsJson>> call, Response<ArrayList<EventsJson>> response) {
                    ArrayList<EventsJson> item = response.body();
                    for( EventsJson elem: item){
                        eventsRecyclerAdapter.addItem(elem);
                    }
                    eventsRecyclerAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ArrayList<EventsJson>> call, Throwable t) {

                }
            });*/

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repository_content, container, false);
        ButterKnife.bind(this, view);

        //if(mPage == 1){
        //} else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            branchesRecyclerView.setLayoutManager(layoutManager);
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
            branchesRecyclerView.addItemDecoration(itemDecoration);

            //if(mPage == 1)
            //  branchesRecyclerView.setAdapter(branchRecyclerAdapter);
            if (mPage == 2)
                branchesRecyclerView.setAdapter(filesRecyclerAdapter);
            else if (mPage == 3)
                //branchesRecyclerView.setAdapter(eventsRecyclerAdapter);
                branchesRecyclerView.setAdapter(commitsRecyclerAdapter);
        //}
        return view;
    }
}