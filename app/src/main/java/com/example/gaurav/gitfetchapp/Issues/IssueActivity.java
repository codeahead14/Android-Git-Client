package com.example.gaurav.gitfetchapp.Issues;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.gaurav.gitfetchapp.R;

public class IssueActivity extends AppCompatActivity {
    public static final String ISSUE_CONTENTS = "ISSUE_CONTENTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.issue_container, new IssueActivityFragment())
                    .commit();
        }
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*Intent intent = getIntent();
        IssueItem item = intent.getExtras().getParcelable(ISSUE_CONTENTS);
        String title = item.getTitle();
        getSupportActionBar().setTitle(title);*/
    }

}
