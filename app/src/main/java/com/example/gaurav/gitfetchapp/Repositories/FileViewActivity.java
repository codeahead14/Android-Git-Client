package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileViewActivity extends AppCompatActivity {
    private static final String TAG = FileViewActivity.class.getName();

    @BindView(R.id.code_text) TextView code_text_view;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    //@BindView(R.id.src_listview) ListView source_list_view;
    @BindView(R.id.src_recyclerview)
    RecyclerView sourceRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_view);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String fileContent = intent.getStringExtra(Intent.EXTRA_TEXT);
        String[] source_lines = fileContent.split("\\n");
        //Log.v(TAG,"data: "+fileContent.split("\\n").length);
        //String fileContent = intent.getExtras().getString(Intent.EXTRA_TEXT);
        //code_text_view.setText(fileContent);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        sourceRecyclerView.setLayoutManager(layoutManager);
        SourceContentsAdapter sourceContentsAdapter = new SourceContentsAdapter(this, source_lines);
        sourceRecyclerView.setAdapter(sourceContentsAdapter);

    }
}
