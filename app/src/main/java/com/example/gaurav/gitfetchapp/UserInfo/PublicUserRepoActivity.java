package com.example.gaurav.gitfetchapp.UserInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryAdapter;
import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.UserInfoActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicUserRepoActivity extends AppCompatActivity {

    @BindView(R.id.public_user_repo_recycler)
    RecyclerView repo_RecyclerView;

    @BindView(R.id.toolbar) Toolbar toolbar;

    private RepositoryAdapter repositoryAdapter;
    private GitHubEndpointInterface githubEndpointInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_user_repo);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setVisibility(View.GONE);

        Intent intent = getIntent();
        String repo_url = intent.getExtras().getString(UserInfoActivity.REPO_URL);

        repositoryAdapter = new RepositoryAdapter(this,0, new ArrayList<UserRepoJson>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        repo_RecyclerView.setLayoutManager(layoutManager);
        //RecyclerView.ItemDecoration itemDecoration = new
          //      DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        //repo_RecyclerView.addItemDecoration(itemDecoration);
        repo_RecyclerView.setAdapter(repositoryAdapter);

        githubEndpointInterface = ServiceGenerator.createService(GitHubEndpointInterface.class);
        Call<ArrayList<UserRepoJson>> call = githubEndpointInterface.getPublicRepos(repo_url);
        call.enqueue(new Callback<ArrayList<UserRepoJson>>() {
            @Override
            public void onResponse(Call<ArrayList<UserRepoJson>> call, Response<ArrayList<UserRepoJson>> response) {
                if (response.isSuccessful()){
                    ArrayList<UserRepoJson> item = response.body();
                    repositoryAdapter.clear();
                    for (UserRepoJson elem : item){
                        repositoryAdapter.addItem(elem);
                    }
                    repositoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserRepoJson>> call, Throwable t) {

            }
        });
    }

}
