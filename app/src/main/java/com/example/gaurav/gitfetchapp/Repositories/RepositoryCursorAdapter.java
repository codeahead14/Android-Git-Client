package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.CursorRecyclerViewAdapter;
import com.example.gaurav.gitfetchapp.R;

import java.util.ArrayList;

/**
 * Created by GAURAV on 21-08-2016.
 */
public class RepositoryCursorAdapter extends CursorRecyclerViewAdapter<RepositoryCursorAdapter.RepositoryCursorViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public RepositoryCursorAdapter(Context context, Cursor cursor){
        super(context,cursor);
    }

    @Override
    public void onBindViewHolder(RepositoryCursorViewHolder viewHolder, Cursor cursor) {

    }

    @Override
    public RepositoryCursorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View repositoryView = layoutInflater.inflate(R.layout.repository_cardview,parent,false);
        RepositoryCursorViewHolder repositoryViewHolder = new RepositoryCursorViewHolder(repositoryView);
        return repositoryViewHolder;
    }

    public class RepositoryCursorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView pushed_at_textview;
        private TextView repo_name_textview;
        private TextView stargazer_textview;
        private TextView fork_count_textview;
        private TextView language_textview;

        public RepositoryCursorViewHolder(View view){
            super(view);
            repo_name_textview = (TextView) view.findViewById(R.id.repo_name_text);
            pushed_at_textview = (TextView) view.findViewById(R.id.pushedText);
            stargazer_textview = (TextView) view.findViewById(R.id.stargazerText);
            fork_count_textview = (TextView) view.findViewById(R.id.forkText);
            language_textview = (TextView) view.findViewById(R.id.languageText);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
