package com.example.gaurav.gitfetchapp.Repositories;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.AccessToken;
import com.example.gaurav.gitfetchapp.DividerItemDecoration;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.Issues.IssuesJson;
import com.example.gaurav.gitfetchapp.Issues.IssuesRecyclerAdapter;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Events.EventsJson;
import com.example.gaurav.gitfetchapp.Repositories.BranchDetails.BranchDetailJson;
import com.example.gaurav.gitfetchapp.Repositories.Commits.CommitsRepoJson;
import com.example.gaurav.gitfetchapp.Repositories.TreeDetails.*;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.Utility;
import com.wang.avi.AVLoadingIndicatorView;

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
    private static final String REPOSITORY_PAGER_STATE = "repo_pager_state";
    private static final String TAG = RepositoryPagerFragment.class.getName();
    private int mPage;
    private UserRepoJson userRepoJson;
    private BranchRecyclerAdapter branchRecyclerAdapter;
    private FilesRecyclerAdapter filesRecyclerAdapter;
    private EventsRecyclerAdapter eventsRecyclerAdapter;
    private CommitsRecyclerAdapter commitsRecyclerAdapter;
    private IssuesRecyclerAdapter issuesRecyclerAdapter;
    private String default_branch;
    private String repo_branch;
    private Parcelable state, mRecyclerState;
    private LinearLayoutManager layoutManager;

    private GitHubEndpointInterface gitHubEndpointInterface;

    @BindView(R.id.branches_recyclerview) RecyclerView branchesRecyclerView;
    @BindView(R.id.avi) AVLoadingIndicatorView avLoadingIndicatorView;

    public static RepositoryPagerFragment newInstance(int page, UserRepoJson item) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putParcelable(USER_REPO, item);
        RepositoryPagerFragment fragment = new RepositoryPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        if(mPage == 2) {
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
            if(Utility.hasConnection(getContext())){
            //avLoadingIndicatorView.show();
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
                   // avLoadingIndicatorView.hide();
                }

                @Override
                public void onFailure(Call<ArrayList<RepoContentsJson>> call, Throwable t) {
                    //avLoadingIndicatorView.hide();
                }
            });
        }else
            Toast.makeText(getContext(), R.string.notOnline, Toast.LENGTH_SHORT).show();
        } else if(mPage == 3){
            if(Utility.hasConnection(getContext())) {
                //avLoadingIndicatorView.show();
                GitHubEndpointInterface endpointInterface = ServiceGenerator.createService(
                        GitHubEndpointInterface.class, AccessToken.getInstance());
                commitsRecyclerAdapter = new CommitsRecyclerAdapter(getContext(), new ArrayList<CommitsRepoJson>());
                Call<ArrayList<CommitsRepoJson>> call = endpointInterface.getRepoCommits(
                        userRepoJson.getOwner().getLogin(), userRepoJson.getName());
                call.enqueue(new Callback<ArrayList<CommitsRepoJson>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CommitsRepoJson>> call, Response<ArrayList<CommitsRepoJson>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<CommitsRepoJson> list = response.body();
                            Log.v(TAG, "commits size: " + list.size());
                            //commitsRecyclerAdapter.clear();
                            for (CommitsRepoJson elem : list)
                                commitsRecyclerAdapter.addItem(elem);
                            commitsRecyclerAdapter.notifyDataSetChanged();
                        }
                       // avLoadingIndicatorView.hide();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CommitsRepoJson>> call, Throwable t) {
                       // avLoadingIndicatorView.hide();
                    }
                });
            } else
                Toast.makeText(getContext(),R.string.notOnline,Toast.LENGTH_SHORT).show();
        } else if(mPage == 4){
            if(Utility.hasConnection(getContext())) {
                //avLoadingIndicatorView.show();
                GitHubEndpointInterface endpointInterface = ServiceGenerator.createService(
                        GitHubEndpointInterface.class, AccessToken.getInstance());
                issuesRecyclerAdapter = new IssuesRecyclerAdapter(getContext(), new ArrayList<IssuesJson>());
                Call<ArrayList<IssuesJson>> call = endpointInterface.getRepoIssues(
                        "wasabeef", "awesome-android-ui");
                //userRepoJson.getOwner().getLogin(),userRepoJson.getName());
                call.enqueue(new Callback<ArrayList<IssuesJson>>() {
                    @Override
                    public void onResponse(Call<ArrayList<IssuesJson>> call, Response<ArrayList<IssuesJson>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<IssuesJson> list = response.body();
                            Log.v(TAG, "issues size: " + list.size());
                            //commitsRecyclerAdapter.clear();
                            for (IssuesJson elem : list)
                                issuesRecyclerAdapter.addItem(elem);
                            issuesRecyclerAdapter.notifyDataSetChanged();
                        }
                       // avLoadingIndicatorView.hide();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<IssuesJson>> call, Throwable t) {
                       // avLoadingIndicatorView.hide();
                    }
                });
            }else
                Toast.makeText(getContext(), R.string.notOnline, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repository_content, container, false);
        ButterKnife.bind(this, view);

        //if(mPage == 1){
        //} else {
            layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            branchesRecyclerView.setLayoutManager(layoutManager);
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
            branchesRecyclerView.addItemDecoration(itemDecoration);

            //if(mPage == 1)
            //  branchesRecyclerView.setAdapter(branchRecyclerAdapter);
/*            if (mPage == 2)
                branchesRecyclerView.setAdapter(filesRecyclerAdapter);
            else if (mPage == 3)
                //branchesRecyclerView.setAdapter(eventsRecyclerAdapter);
                branchesRecyclerView.setAdapter(commitsRecyclerAdapter);*/
        //}
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        state = layoutManager.onSaveInstanceState();
        outState.putParcelable(REPOSITORY_PAGER_STATE,state);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (state != null) {
            layoutManager.onRestoreInstanceState(state);
        }
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPage == 2)
            branchesRecyclerView.setAdapter(filesRecyclerAdapter);
        else if (mPage == 3)
            //branchesRecyclerView.setAdapter(eventsRecyclerAdapter);
            branchesRecyclerView.setAdapter(commitsRecyclerAdapter);
        else if(mPage == 4)
            branchesRecyclerView.setAdapter(issuesRecyclerAdapter);

        // Restore previous state (including selected item index and scroll position)
        if(state != null) {
            //Log.d(TAG, "trying to restore gridview state..");
            layoutManager.onRestoreInstanceState(state);
        }
    }
}