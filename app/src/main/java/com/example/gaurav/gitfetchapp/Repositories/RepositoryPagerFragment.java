package com.example.gaurav.gitfetchapp.Repositories;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
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
import com.example.gaurav.gitfetchapp.EndlessRecyclerViewScrollListener;
import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.Issue;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.Issues.IssueItem;
import com.example.gaurav.gitfetchapp.Issues.IssuesJson;
import com.example.gaurav.gitfetchapp.Issues.IssuesRecyclerAdapter;
import com.example.gaurav.gitfetchapp.PreLoginDeciderActivity;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Repositories.Commits.CommitsRepoJson;
import com.example.gaurav.gitfetchapp.Repositories.TreeDetails.*;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GAURAV on 06-08-2016.
 */
public class RepositoryPagerFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String USER_REPO = "USER_REPO";
    public static final String SELECTED_BRANCH = "SELECTED_BRANCH";
    public static Observable<Integer> loading;

    private static final String REPOSITORY_PAGER_STATE = "repo_pager_state";
    public static final String TAG = RepositoryPagerFragment.class.getName();
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
    private HorizontalListRecyclerAdapter listItemArray;
    private static MaterialProgressBar materialProgressBar;
    private GitHubEndpointInterface gitHubEndpointInterface;
    private static int PAGE_COUNT = 0;
    private Subscription pagerSubscription;
    public static int loadingIndicator = 0;

    @BindView(R.id.branches_recyclerview)
    RecyclerView branchesRecyclerView;
    @BindView(R.id.hierarchy_list)
    RecyclerView fileHierarchyList;
    //@BindView(R.id.repository_progress_bar)
    //MaterialProgressBar materialProgressBar;

    public static RepositoryPagerFragment newInstance(int page, UserRepoJson item, String branch) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putParcelable(USER_REPO, item);
        args.putString(SELECTED_BRANCH, branch);
        Log.v(TAG,"branch in instance "+branch);

        RepositoryPagerFragment fragment = new RepositoryPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        userRepoJson = getArguments().getParcelable(USER_REPO);
        repo_branch = getArguments().getString(SELECTED_BRANCH);

        gitHubEndpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class);
        listItemArray = new HorizontalListRecyclerAdapter(getContext(), new ArrayList<String>());

        if(mPage == 2)
            filesRecyclerAdapter = new FilesRecyclerAdapter(getContext(), new ArrayList<RepoContentsJson>(),
                userRepoJson.getOwner().getLogin(), userRepoJson.getName(), repo_branch, listItemArray);
        else if(mPage == 3)
            commitsRecyclerAdapter = new CommitsRecyclerAdapter(getContext(), new ArrayList<CommitsRepoJson>());
        else if(mPage == 4) {
            PAGE_COUNT = 1;
            loadingIndicator = 1;
            issuesRecyclerAdapter = new IssuesRecyclerAdapter(getContext(), new ArrayList<IssueItem>(),
                    TAG);
            issuesRecyclerAdapter.clear();
        }
        //fetchPagerData();
        createObservable();
    }

    private void createObservable(){
        Observable<Integer> callObservable = Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return fetchPagerData();
            }
        });

        pagerSubscription = callObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        materialProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    private int fetchPagerData() {
        repo_branch = RepositoryDetailActivityFragment.repoBranch;
        if (mPage == 2) {
            if (Utility.hasConnection(getContext())) {
                //materialProgressBar.setVisibility(View.VISIBLE);
                listItemArray.addItem(repo_branch);
                listItemArray.notifyDataSetChanged();
                Call<ArrayList<RepoContentsJson>> call = gitHubEndpointInterface.getRepoContents(
                        userRepoJson.getOwner().getLogin(), userRepoJson.getName(), "", repo_branch);
                call.enqueue(new Callback<ArrayList<RepoContentsJson>>() {
                    @Override
                    public void onResponse(Call<ArrayList<RepoContentsJson>> call, Response<ArrayList<RepoContentsJson>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<RepoContentsJson> item = response.body();
                            for (RepoContentsJson elem : item)
                                filesRecyclerAdapter.addItem(elem);
                            filesRecyclerAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<RepoContentsJson>> call, Throwable t) {
                        //avLoadingIndicatorView.hide();
                    }
                });
            } else
                Toast.makeText(getContext(), R.string.notOnline, Toast.LENGTH_SHORT).show();
        } else if (mPage == 3) {
            if (Utility.hasConnection(getContext())) {
                //avLoadingIndicatorView.show();
                GitHubEndpointInterface endpointInterface = ServiceGenerator.createService(
                        GitHubEndpointInterface.class, AccessToken.getInstance());
                Call<ArrayList<CommitsRepoJson>> call = endpointInterface.getRepoCommits(
                        userRepoJson.getOwner().getLogin(), userRepoJson.getName());
                call.enqueue(new Callback<ArrayList<CommitsRepoJson>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CommitsRepoJson>> call, Response<ArrayList<CommitsRepoJson>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<CommitsRepoJson> list = response.body();
                            for (CommitsRepoJson elem : list)
                                commitsRecyclerAdapter.addItem(elem);
                            commitsRecyclerAdapter.notifyDataSetChanged();
                            //materialProgressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CommitsRepoJson>> call, Throwable t) {
                        // avLoadingIndicatorView.hide();
                    }
                });
            } else
                Toast.makeText(getContext(), R.string.notOnline, Toast.LENGTH_SHORT).show();
        } else if (mPage == 4) {
            if (Utility.hasConnection(getContext())) {
                //avLoadingIndicatorView.show();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                String AUTHOR = "author";
                String AUTHOR_NAME = prefs.getString(PreLoginDeciderActivity.USERNAME_KEY, null);
                String author_params = String.format("%s:%s", AUTHOR, "wasabeef");
                GitHubEndpointInterface endpointInterface = ServiceGenerator.createService(
                        GitHubEndpointInterface.class, AccessToken.getInstance());
                //Call<IssuesJson> call = endpointInterface.getIssues(author_params, PAGE_COUNT, 30);
                Call<ArrayList<IssueItem>> call = endpointInterface.getRepoIssues(
                        userRepoJson.getOwner().getLogin(), userRepoJson.getName());
                PAGE_COUNT++;
                //userRepoJson.getOwner().getLogin(),userRepoJson.getName());
                call.enqueue(new Callback<ArrayList<IssueItem>>() {
                    @Override
                    public void onResponse(Call<ArrayList<IssueItem>> call, Response<ArrayList<IssueItem>> response) {
                        if (response.isSuccessful()){
                            ArrayList<IssueItem> items = response.body();
                            for (IssueItem elem: items)
                                issuesRecyclerAdapter.addItem(elem);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<IssueItem>> call, Throwable t) {

                    }
                });
            }
        }
        return 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG,"onCreateView");
        View view = inflater.inflate(R.layout.fragment_repository_content, container, false);
        ButterKnife.bind(this, view);
        materialProgressBar = (MaterialProgressBar) view.findViewById(R.id.repository_progress_bar);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        branchesRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        branchesRecyclerView.addItemDecoration(itemDecoration);
        branchesRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, boolean loading) {
                //materialProgressBar.setVisibility(View.VISIBLE);
                loadingIndicator = 0;
                if (mPage != 2){
                    fetchPagerData();
                }
            }
        });

        LinearLayoutManager listManager = new LinearLayoutManager(getActivity());
        listManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fileHierarchyList.setLayoutManager(listManager);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        state = layoutManager.onSaveInstanceState();
        outState.putParcelable(REPOSITORY_PAGER_STATE, state);
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
        if (mPage == 2) {
            branchesRecyclerView.setAdapter(filesRecyclerAdapter);
            fileHierarchyList.setAdapter(listItemArray);
            branchesRecyclerView.setPadding(0, 0, 0, 56);
            //materialProgressBar.setVisibility(View.VISIBLE);
        } else if (mPage == 3) {
            //branchesRecyclerView.setAdapter(eventsRecyclerAdapter);
            branchesRecyclerView.setAdapter(commitsRecyclerAdapter);
            branchesRecyclerView.setPadding(0, 0, 0, 0);
        } else if (mPage == 4) {
            branchesRecyclerView.setAdapter(issuesRecyclerAdapter);
            branchesRecyclerView.setPadding(0, 0, 0, 0);
        }

        // Restore previous state (including selected item index and scroll position)
        if (state != null) {
            //Log.d(TAG, "trying to restore gridview state..");
            layoutManager.onRestoreInstanceState(state);
        }
    }

    private void OnHideProgressBar() {
        materialProgressBar.setVisibility(View.GONE);
        branchesRecyclerView.setVisibility(View.VISIBLE);
    }
}