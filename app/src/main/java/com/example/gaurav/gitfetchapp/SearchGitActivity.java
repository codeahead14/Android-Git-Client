package com.example.gaurav.gitfetchapp;

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
import android.widget.EditText;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.SearchGit.Item;
import com.example.gaurav.gitfetchapp.SearchGit.SearchGitJson;
import com.example.gaurav.gitfetchapp.SearchGit.SearchUserRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchGitActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.search_edit) EditText searchEdit;
    @BindView(R.id.search_user_recycler) RecyclerView searchRecyclerView;

    private static final String TAG = SearchGitActivity.class.getName();
    private SearchUserRecyclerAdapter searchUserRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_git);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchEdit.setImeActionLabel("Done", KeyEvent.KEYCODE_ENTER);

        TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    Log.v(TAG,"hitting Enter");
                    String searchQuery = searchEdit.getText().toString();
                    fetchSearchResults(searchQuery);
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

    public void fetchSearchResults(String query){
        Log.v(TAG,"fetchingSearchQuery");
        GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class);
        String[] args = query.split(" ");
        Call<SearchGitJson> call = gitHubEndpointInterface.getSearchResults(args[0]);
        call.enqueue(new Callback<SearchGitJson>() {
            @Override
            public void onResponse(Call<SearchGitJson> call, Response<SearchGitJson> response) {
                if(response.isSuccessful()){
                    SearchGitJson item = response.body();
                    List<Item> searchItems = item.getItems();
                    Log.v(TAG,"responsesuccessful: "+searchItems.size());
                    for(Item elem: searchItems)
                        searchUserRecyclerAdapter.addItem(elem);
                    searchUserRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SearchGitJson> call, Throwable t) {
                Log.v(TAG,"failure: "+t.getMessage());
            }
        });
    }

}
