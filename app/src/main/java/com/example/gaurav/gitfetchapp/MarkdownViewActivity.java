package com.example.gaurav.gitfetchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import us.feras.mdv.MarkdownView;


public class MarkdownViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);

        Intent intent =  getIntent();
        String url = intent.getExtras().getString(Intent.EXTRA_TEXT);
        markdownView.loadMarkdownFile(url);
    }

}
