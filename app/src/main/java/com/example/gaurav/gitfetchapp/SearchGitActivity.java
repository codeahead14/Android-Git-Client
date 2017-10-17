package com.example.gaurav.gitfetchapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.SearchGit.Item;
import com.example.gaurav.gitfetchapp.SearchGit.SearchGitJson;
import com.example.gaurav.gitfetchapp.SearchGit.SearchUserRecyclerAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchGitActivity extends AppCompatActivity {

    @BindView(R.id.search_toolbar) Toolbar toolbar;
    @BindView(R.id.search_edit) EditText searchEdit;
    @BindView(R.id.search_user_recycler) RecyclerView searchRecyclerView;
    @BindView(R.id.no_results_text)
    TextView noResultsText;
    @BindView(R.id.search_progress_bar)
    MaterialProgressBar materialProgressBar;

    private static final String TAG = SearchGitActivity.class.getName();
    private SearchUserRecyclerAdapter searchUserRecyclerAdapter;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_git);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(!Utility.hasConnection(this)){
            searchEdit.setHint(R.string.SearchIsOffline);
        }else
            searchEdit.setHint(R.string.SearchHint);

        searchEdit.setImeActionLabel("Done", KeyEvent.KEYCODE_ENTER);
        TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String searchQuery = searchEdit.getText().toString();
                    if(Utility.hasConnection(SearchGitActivity.this)) {
                        materialProgressBar.setVisibility(View.VISIBLE);
                        fetchSearchResults(searchQuery);
                    }
                    return true;
                }
                return false;
            }
        };
        searchEdit.setOnEditorActionListener(editorActionListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        searchRecyclerView.addItemDecoration(itemDecoration);

        searchUserRecyclerAdapter = new SearchUserRecyclerAdapter(this,new ArrayList<Item>());
        searchRecyclerView.setAdapter(searchUserRecyclerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(Utility.hasConnection(context)){
                    searchEdit.setHint(R.string.SearchHint);
                }else if(!Utility.hasConnection(context)){
                    searchEdit.setHint(R.string.SearchIsOffline);
                }
            }
        };
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    public void fetchSearchResults(String query){
        GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class);
        String[] args = query.split(" ");
        Call<SearchGitJson> call = gitHubEndpointInterface.getSearchResults(args[0]);
        call.enqueue(new Callback<SearchGitJson>() {
            @Override
            public void onResponse(Call<SearchGitJson> call, Response<SearchGitJson> response) {
                if(response.isSuccessful()){
                    SearchGitJson item = response.body();
                    searchUserRecyclerAdapter.clear();
                    if(item.getTotalCount() == 0) {
                        noResultsText.setVisibility(View.VISIBLE);
                        searchRecyclerView.setVisibility(View.GONE);
                    }else {
                        searchRecyclerView.setVisibility(View.VISIBLE);
                        noResultsText.setVisibility(View.GONE);
                        List<Item> searchItems = item.getItems();
                        for (Item elem : searchItems)
                            searchUserRecyclerAdapter.addItem(elem);
                        searchUserRecyclerAdapter.notifyDataSetChanged();
                    }
                    materialProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SearchGitJson> call, Throwable t) {
                Log.v(TAG,"failure: "+t.getMessage());
            }
        });
    }

}
