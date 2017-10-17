package com.example.gaurav.gitfetchapp;

import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.User;
import com.example.gaurav.gitfetchapp.Events.Repo;
import com.example.gaurav.gitfetchapp.GooglePlayServices.TrackerApplication;
import com.example.gaurav.gitfetchapp.Repositories.Owner;
import com.example.gaurav.gitfetchapp.Repositories.RepositoriesSettingsActivity;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryAdapter;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryDetailActivity;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryListAdapter;
import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;
import com.example.gaurav.gitfetchapp.RepositoryDataBase.RepositoryContract;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GAURAV on 28-07-2016.
 */
public class RepositoriesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = RepositoriesFragment.class.getName();
    private static final String REPO_TAG = "repositories_state";
    private static final int REPOSITORIES_LOADER = 1;
    public static final String REPO_NAMES_PREFS = "repo_names_prefs";
    private View rootView;
    private AccessToken mAccessToken;
    private Intent intent;
    private RepositoryAdapter mRepositoryAdapter;
    private ArrayList<UserRepoJson> userRepoList;
    private boolean hasRepositoryData = false;
    private ArrayList<String> repoNames;
    private Context mContext;
    private RepositoryListAdapter mRepoListAdapter;
    private String[] intentData;
    private Parcelable state;
    private Tracker mTracker;
    private BroadcastReceiver broadcastReceiver;
    private Snackbar connectionSnackbar;
    Vector<ContentValues> cVVector;

    @BindView(R.id.repository_recycler)
    ListView repoRecyclerView;
    @BindView(R.id.repositories_progress_bar)
    MaterialProgressBar materialProgressBar;

    public static final String[] REPOSITORY_COLUMNS_ALL = {
            RepositoryContract.RepositoryEntry.TABLE_NAME + "." + RepositoryContract.RepositoryEntry._ID,
            RepositoryContract.RepositoryEntry.TABLE_NAME + "." + RepositoryContract.RepositoryEntry.COLUMN_ID,
            RepositoryContract.RepositoryEntry.COLUMN_OWNER_KEY,
            RepositoryContract.RepositoryEntry.COLUMN_NAME,
            RepositoryContract.RepositoryEntry.COLUMN_FULL_NAME,
            RepositoryContract.RepositoryEntry.COLUMN_IS_PRIVATE,
            RepositoryContract.RepositoryEntry.TABLE_NAME + "." + RepositoryContract.RepositoryEntry.COLUMN_HTML_URL,
            RepositoryContract.RepositoryEntry.COLUMN_DESCRIPTION,
            RepositoryContract.RepositoryEntry.COLUMN_IS_FORK,
            RepositoryContract.RepositoryEntry.TABLE_NAME + "." + RepositoryContract.RepositoryEntry.COLUMN_URL,
            RepositoryContract.RepositoryEntry.COLUMN_FORKS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_KEY_URL,
            RepositoryContract.RepositoryEntry.COLUMN_COLLABORATORS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_TEAMS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_ISSUE_EVENTS_URL,
            RepositoryContract.RepositoryEntry.TABLE_NAME + "." + RepositoryContract.RepositoryEntry.COLUMN_EVENTS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_ASSIGNEES_URL,
            RepositoryContract.RepositoryEntry.COLUMN_BRANCHES_URL,
            RepositoryContract.RepositoryEntry.COLUMN_TAGS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_BLOBS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_GIT_TAGS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_REFS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_TREES_URL,
            RepositoryContract.RepositoryEntry.COLUMN_STATUSES_URL,
            RepositoryContract.RepositoryEntry.COLUMN_LANGUAGES_URL,
            RepositoryContract.RepositoryEntry.COLUMN_STARGAZERS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_CONTRIBUTORS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_SUBSCRIBERS_URL,
            RepositoryContract.RepositoryEntry.TABLE_NAME + "." + RepositoryContract.RepositoryEntry.COLUMN_SUBSCRIPTION_URL,
            RepositoryContract.RepositoryEntry.COLUMN_COMMITS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_GIT_COMMITS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_COMMENTS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_ISSUE_COMMENT_URL,
            RepositoryContract.RepositoryEntry.COLUMN_CONTENTS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_COMPARE_URL,
            RepositoryContract.RepositoryEntry.COLUMN_MERGES_URL,
            RepositoryContract.RepositoryEntry.COLUMN_ARCHIVE_URL,
            RepositoryContract.RepositoryEntry.COLUMN_DOWNLOADS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_ISSUES_URL,
            RepositoryContract.RepositoryEntry.COLUMN_PULLS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_MILESTONES_URL,
            RepositoryContract.RepositoryEntry.COLUMN_NOTIFICATIONS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_LABELS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_RELEASES_URL,
            RepositoryContract.RepositoryEntry.COLUMN_DEPLOYMENTS_URL,
            RepositoryContract.RepositoryEntry.COLUMN_CREATED_AT,
            RepositoryContract.RepositoryEntry.COLUMN_PUSHED_AT,
            RepositoryContract.RepositoryEntry.COLUMN_GIT_URL,
            RepositoryContract.RepositoryEntry.COLUMN_SSH_URL,
            RepositoryContract.RepositoryEntry.COLUMN_CLONE_URL,
            RepositoryContract.RepositoryEntry.COLUMN_SVN_URL,
            RepositoryContract.RepositoryEntry.COLUMN_HOMEPAGE,
            RepositoryContract.RepositoryEntry.COLUMN_SIZE,
            RepositoryContract.RepositoryEntry.COLUMN_STARGAZERS_COUNT,
            RepositoryContract.RepositoryEntry.COLUMN_WATCHERS_COUNT,
            RepositoryContract.RepositoryEntry.COLUMN_LANGUAGE,
            RepositoryContract.RepositoryEntry.COLUMN_HAS_ISSUES,
            RepositoryContract.RepositoryEntry.COLUMN_HAS_DOWNLOADS,
            RepositoryContract.RepositoryEntry.COLUMN_HAS_WIKI,
            RepositoryContract.RepositoryEntry.COLUMN_HAS_PAGES,
            RepositoryContract.RepositoryEntry.COLUMN_FORKS_COUNT,
            RepositoryContract.RepositoryEntry.COLUMN_MIRROR_URL,
            RepositoryContract.RepositoryEntry.COLUMN_OPEN_ISSUES_COUNT,
            RepositoryContract.RepositoryEntry.COLUMN_FORKS,
            RepositoryContract.RepositoryEntry.COLUMN_OPEN_ISSUES,
            RepositoryContract.RepositoryEntry.COLUMN_WATCHERS,
            RepositoryContract.RepositoryEntry.COLUMN_DEFAULT_BRANCH
    };

    public static final String[] REPOSITORY_COLUMNS = {
            RepositoryContract.RepositoryEntry.TABLE_NAME + "." + RepositoryContract.RepositoryEntry._ID,
            RepositoryContract.RepositoryEntry.COLUMN_OWNER_KEY,
            RepositoryContract.RepositoryEntry.TABLE_NAME + "." + RepositoryContract.RepositoryEntry.COLUMN_ID,
            RepositoryContract.RepositoryEntry.COLUMN_NAME,
            RepositoryContract.RepositoryEntry.COLUMN_FULL_NAME,
            RepositoryContract.RepositoryEntry.TABLE_NAME + "." + RepositoryContract.RepositoryEntry.COLUMN_HTML_URL,
            RepositoryContract.RepositoryEntry.COLUMN_CREATED_AT,
            RepositoryContract.RepositoryEntry.COLUMN_PUSHED_AT,
            RepositoryContract.RepositoryEntry.COLUMN_SIZE,
            RepositoryContract.RepositoryEntry.COLUMN_STARGAZERS_COUNT,
            RepositoryContract.RepositoryEntry.COLUMN_WATCHERS_COUNT,
            RepositoryContract.RepositoryEntry.COLUMN_LANGUAGE,
            RepositoryContract.RepositoryEntry.COLUMN_FORKS_COUNT,
            RepositoryContract.RepositoryEntry.COLUMN_OPEN_ISSUES_COUNT,
            RepositoryContract.RepositoryEntry.COLUMN_FORKS,
            RepositoryContract.RepositoryEntry.COLUMN_OPEN_ISSUES,
            RepositoryContract.RepositoryEntry.COLUMN_WATCHERS,
            RepositoryContract.RepositoryEntry.COLUMN_DEFAULT_BRANCH
    };

    public static final String[] OWNER_COLUMNS = {
            RepositoryContract.OwnerEntry.TABLE_NAME + "." + RepositoryContract.OwnerEntry._ID,
            RepositoryContract.OwnerEntry.COLUMN_LOGIN,
            RepositoryContract.OwnerEntry.TABLE_NAME + "." + RepositoryContract.OwnerEntry.COLUMN_ID,
            RepositoryContract.OwnerEntry.COLUMN_AVATAR_URL,
            RepositoryContract.OwnerEntry.COLUMN_GRAVATAR_ID,
            RepositoryContract.OwnerEntry.COLUMN_URL,
            RepositoryContract.OwnerEntry.TABLE_NAME + "." + RepositoryContract.OwnerEntry.COLUMN_HTML_URL,
            RepositoryContract.OwnerEntry.COLUMN_FOLLOWERS_URL,
            RepositoryContract.OwnerEntry.COLUMN_FOLLOWING_URL,
            RepositoryContract.OwnerEntry.COLUMN_GISTS_URL,
            RepositoryContract.OwnerEntry.COLUMN_STARRED_URL,
            RepositoryContract.OwnerEntry.COLUMN_SUBSCRIPTION_URL,
            RepositoryContract.OwnerEntry.COLUMN_ORGANIZATIONS_URL,
            RepositoryContract.OwnerEntry.COLUMN_REPOS_URL,
            RepositoryContract.OwnerEntry.COLUMN_EVENTS_URL,
            RepositoryContract.OwnerEntry.COLUMN_RECEIVED_EVENTS_URL,
            RepositoryContract.OwnerEntry.COLUMN_TYPE,
            RepositoryContract.OwnerEntry.COLUMN_SITE_ADMIN
    };

    public static final int COLUMN_REPOSITORY_TABLE_ID = 0;
    public static final int COLUMN_OWNER_KEY = 1;
    public static final int COLUMN_ID = 2;
    public static final int COLUMN_NAME = 3;
    public static final int COLUMN_FULL_NAME = 4;
    public static final int COLUMN_HTML_URL = 5;
    public static final int COLUMN_CREATED_AT = 6;
    public static final int COLUMN_PUSHED_AT = 7;
    public static final int COLUMN_SIZE = 8;
    public static final int COLUMN_STARGAZERS_COUNT = 9;
    public static final int COLUMN_WATCHERS_COUNT = 10;
    public static final int COLUMN_LANGUAGE = 11;
    public static final int COLUMN_FORKS_COUNT = 12;
    public static final int COLUMN_OPEN_ISSUES_COUNT = 13;
    public static final int COLUMN_FORKS = 14;
    public static final int COLUMN_OPEN_ISSUES = 15;
    public static final int COLUMN_WATCHERS = 16;
    public static final int COLUMN_DEFAULT_BRANCH = 17;

    public RepositoriesFragment() {
        mAccessToken = AccessToken.getInstance();
        repoNames = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String sort = prefs.getString(mContext.getString(R.string.pref_repositories_sort),
                mContext.getString(R.string.pref_repositories_sort_default));
        String visibility = prefs.getString(mContext.getString(R.string.pref_repositories_visibility),
                mContext.getString(R.string.pref_repositories_visibility_default));
        String type = prefs.getString(mContext.getString(R.string.pref_repositories_type),
                mContext.getString(R.string.pref_repositories_type_default));
        String direction = prefs.getString(mContext.getString(R.string.pref_repositories_direction),
                mContext.getString(R.string.pref_repositories_direction_default));

        if (userRepoList == null)
            fetchRepositories(sort,visibility,type,direction);
        else {
            mRepoListAdapter.updateValues(userRepoList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTracker.setScreenName("Image~" + TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        /*broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(!Utility.hasConnection(context)){
                    if(connectionSnackbar != null){
                        connectionSnackbar.show();
                    }
                }else if(Utility.hasConnection(context)){
                    if(connectionSnackbar != null)
                        connectionSnackbar.dismiss();
                }
            }
        };
        getActivity().registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));*/
    }

    @Override
    public void onPause() {
        super.onPause();
        state = repoRecyclerView.onSaveInstanceState();
        //getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(REPO_TAG, mRepoListAdapter.getRepositories());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        intent = getActivity().getIntent();
        userRepoList = null;
        mRepositoryAdapter = new RepositoryAdapter(getContext(), R.layout.repository_cardview,
                new ArrayList<UserRepoJson>());
        mRepoListAdapter = new RepositoryListAdapter(getContext(), null);
        if (savedInstanceState != null)
            userRepoList = savedInstanceState.getParcelableArrayList(REPO_TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_repositories, containter, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), RepositoriesSettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TrackerApplication application = (TrackerApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

        repoRecyclerView.setAdapter(mRepoListAdapter);
        repoRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(mContext, RepositoryDetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, mRepoListAdapter.getItem(position)); //userRepoList.get(position));
                mContext.startActivity(intent);
            }
        });

        // Restore previous state (including selected item index and scroll position)
        if (state != null) {
            repoRecyclerView.onRestoreInstanceState(state);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(REPOSITORIES_LOADER, null, this);

        /*(connectionSnackbar = Snackbar.make(rootView, getResources().getString(R.string.notOnline),
                Snackbar.LENGTH_INDEFINITE);
        if(!Utility.hasConnection(getActivity())) {
            connectionSnackbar.setAction(R.string.network_settings, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                }
            });
            connectionSnackbar.setActionTextColor(getResources().getColor(R.color.teal300));
            connectionSnackbar.show();
        }*/

        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(
                getResources().getDrawable(R.drawable.app_bar_gradient));
        //new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Repositories");

        Window window = getActivity().getWindow();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        String owner = prefs.getString(PreLoginDeciderActivity.USERNAME_KEY, null);
        // PreLoginDeciderActivity.getLoginName();// intentData[0];
        Uri repoWithOwnerUri = RepositoryContract.RepositoryEntry.buildRepositoryUriWithOwner(owner);
        return new CursorLoader(getActivity(),
                repoWithOwnerUri,
                REPOSITORY_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mRepoListAdapter.swapCursor(data);
        mRepoListAdapter.clear();
        ArrayList<UserRepoJson> items = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            int ownerId = data.getInt(COLUMN_OWNER_KEY);
            Owner owner = new Owner();
            Cursor repoCursor = mContext.getContentResolver().query(
                    RepositoryContract.OwnerEntry.CONTENT_URI,
                    OWNER_COLUMNS,
                    RepositoryContract.OwnerEntry._ID + " = ?",
                    new String[]{Integer.toString(ownerId)},
                    null);
            if (repoCursor != null && repoCursor.moveToFirst()) {
                owner.setId(repoCursor.getInt(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_ID)));
                owner.setLogin(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_LOGIN)));
                owner.setAvatarUrl(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_AVATAR_URL)));
                owner.setGravatarId(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_GRAVATAR_ID)));
                owner.setUrl(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_URL)));
                owner.setHtmlUrl(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_HTML_URL)));
                owner.setFollowersUrl(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_FOLLOWERS_URL)));
                owner.setFollowingUrl(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_FOLLOWING_URL)));
                owner.setGistsUrl(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_GISTS_URL)));
                owner.setStarredUrl(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_STARRED_URL)));
                owner.setSubscriptionsUrl(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_SUBSCRIPTION_URL)));
                owner.setOrganizationsUrl(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_ORGANIZATIONS_URL)));
                owner.setReposUrl(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_REPOS_URL)));
                owner.setEventsUrl(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_EVENTS_URL)));
                owner.setReceivedEventsUrl(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_RECEIVED_EVENTS_URL)));
                owner.setType(repoCursor.getString(repoCursor.getColumnIndex(RepositoryContract.OwnerEntry.COLUMN_TYPE)));
                owner.setSiteAdmin(false);
            }
            do {
                UserRepoJson item = new UserRepoJson();
                item.setId(data.getInt(COLUMN_ID));
                item.setOwner(owner);
                item.setName(data.getString(COLUMN_NAME));
                item.setFullName(data.getString(COLUMN_FULL_NAME));
                item.setHtmlUrl(data.getString(COLUMN_HTML_URL));
                item.setCreatedAt(Utility.formatDateString(data.getString(COLUMN_CREATED_AT)));
                item.setPushedAt(Utility.formatDateString(data.getString(COLUMN_PUSHED_AT)));
                item.setSize(data.getInt(COLUMN_SIZE));
                item.setStargazersCount(data.getInt(COLUMN_STARGAZERS_COUNT));
                item.setWatchersCount(data.getInt(COLUMN_WATCHERS_COUNT));
                item.setLanguage(data.getString(COLUMN_LANGUAGE));
                item.setForksCount(data.getInt(COLUMN_FORKS_COUNT));
                item.setOpenIssuesCount(data.getInt(COLUMN_OPEN_ISSUES_COUNT));
                item.setForks(data.getInt(COLUMN_FORKS));
                item.setOpenIssues(data.getInt(COLUMN_OPEN_ISSUES));
                item.setWatchers(data.getInt(COLUMN_WATCHERS));
                item.setDefaultBranch(data.getString(COLUMN_DEFAULT_BRANCH));

                items.add(item);
            } while (data.moveToNext());
            mRepoListAdapter.updateValues(items);
        }
        materialProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRepoListAdapter.swapCursor(null);
    }

    public void fetchRepositories(String sort, String visibility, String type, String direction) {
        GitHubEndpointInterface gitInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class, mAccessToken);
        Call<ArrayList<UserRepoJson>> call = gitInterface.getUserRepositories(sort,
                type,
                direction);

        if (Utility.hasConnection(getContext())) {
            call.enqueue(new Callback<ArrayList<UserRepoJson>>() {
                @Override
                public void onResponse(Call<ArrayList<UserRepoJson>> call, Response<ArrayList<UserRepoJson>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<UserRepoJson> item = response.body();
                        //updateList(item);
                        cVVector = new Vector<ContentValues>(item.size());
                        hasRepositoryData = true;
                        mRepositoryAdapter.clear();

                        Cursor repoCursor = mContext.getContentResolver().query(
                                RepositoryContract.OwnerEntry.CONTENT_URI,
                                new String[]{RepositoryContract.OwnerEntry._ID},
                                RepositoryContract.OwnerEntry.COLUMN_LOGIN + " = ?",
                                new String[]{item.get(0).getOwner().getLogin()},
                                null);

                        long ownerId;


                        if (repoCursor.moveToFirst()) {
                            int ownerIndex = repoCursor.getColumnIndex(RepositoryContract.OwnerEntry._ID);
                            ownerId = repoCursor.getLong(ownerIndex);
                        } else {

                            Owner ownerItem = item.get(0).getOwner();
                            ContentValues ownerValues = new ContentValues();
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_ID, ownerItem.getId());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_LOGIN, ownerItem.getLogin());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_AVATAR_URL, ownerItem.getAvatarUrl());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_GRAVATAR_ID, ownerItem.getGravatarId());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_URL, ownerItem.getUrl());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_HTML_URL, ownerItem.getHtmlUrl());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_EVENTS_URL, ownerItem.getEventsUrl());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_FOLLOWERS_URL, ownerItem.getFollowersUrl());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_FOLLOWING_URL, ownerItem.getFollowingUrl());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_SUBSCRIPTION_URL, ownerItem.getSubscriptionsUrl());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_ORGANIZATIONS_URL, ownerItem.getOrganizationsUrl());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_GISTS_URL, ownerItem.getGistsUrl());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_STARRED_URL, ownerItem.getStarredUrl());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_RECEIVED_EVENTS_URL, ownerItem.getReceivedEventsUrl());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_REPOS_URL, ownerItem.getReposUrl());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_TYPE, ownerItem.getType());
                            ownerValues.put(RepositoryContract.OwnerEntry.COLUMN_SITE_ADMIN, ownerItem.getSiteAdmin());

                            // Finally, insert owner data into the database.
                            Uri insertedUri = getContext().getContentResolver().insert(
                                    RepositoryContract.OwnerEntry.CONTENT_URI,
                                    ownerValues
                            );
                            ownerId = ContentUris.parseId(insertedUri);
                        }

                        for (UserRepoJson elem : item) {
                            mRepositoryAdapter.addItem(elem);
                            repoNames.add(elem.getName());
                            ContentValues repoValues = new ContentValues();
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_OWNER_KEY, ownerId);
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_ID, elem.getId());
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_NAME, elem.getName());
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_FULL_NAME, elem.getFullName());
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_HTML_URL, elem.getHtmlUrl());
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_CREATED_AT,
                                    Utility.formatDateString(elem.getCreatedAt()));
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_PUSHED_AT,
                                    Utility.formatDateString(elem.getPushedAt()));
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_SIZE, elem.getSize());
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_STARGAZERS_COUNT, elem.getStargazersCount());
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_WATCHERS_COUNT, elem.getWatchersCount());
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_LANGUAGE, elem.getLanguage());
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_FORKS_COUNT, elem.getForksCount());
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_OPEN_ISSUES_COUNT, elem.getOpenIssuesCount());
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_FORKS, elem.getForks());
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_OPEN_ISSUES, elem.getOpenIssues());
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_WATCHERS, elem.getWatchers());
                            repoValues.put(RepositoryContract.RepositoryEntry.COLUMN_DEFAULT_BRANCH, elem.getDefaultBranch());

                            cVVector.add(repoValues);
                        }

                        int inserted = 0;
                        // add to database
                        if (cVVector.size() > 0) {
                            ContentValues[] cvArray = new ContentValues[cVVector.size()];
                            cVVector.toArray(cvArray);
                            inserted = mContext.getContentResolver().bulkInsert(
                                    RepositoryContract.RepositoryEntry.CONTENT_URI, cvArray);
                        }

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
                    Log.v(TAG, "Stack trace" + t.getMessage());
                }
            });
        }
    }
}
