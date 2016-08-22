package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.RepositoriesFragment;

import java.util.ArrayList;

/**
 * Created by GAURAV on 21-08-2016.
 */
public class RepositoryListAdapter extends CursorAdapter {
    private static final String TAG = RepositoryListAdapter.class.getName();
    private ArrayList<UserRepoJson> mRepoData = new ArrayList<>();

    public RepositoryListAdapter(Context context, Cursor c) {
        super(context, c);
        Log.v(TAG,"Constructor");
    }

    public UserRepoJson getItem(int position){
        Log.v(TAG,"position: "+position);
        return mRepoData.get(position);
    }

    public void clear(){
        mRepoData.clear();
    }

    public void updateValues(ArrayList<UserRepoJson> elements) {
        Log.v(TAG,"size: "+elements.size());
        for(UserRepoJson elem: elements){
            this.mRepoData.add(elem);
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder{
        private TextView pushed_at_textview;
        private TextView repo_name_textview;
        private TextView stargazer_textview;
        private TextView fork_count_textview;
        private TextView language_textview;

        public ViewHolder(View view) {
            repo_name_textview = (TextView) view.findViewById(R.id.repo_name_text);
            pushed_at_textview = (TextView) view.findViewById(R.id.pushedText);
            stargazer_textview = (TextView) view.findViewById(R.id.stargazerText);
            fork_count_textview = (TextView) view.findViewById(R.id.forkText);
            language_textview = (TextView) view.findViewById(R.id.languageText);

        }
    }

    public ArrayList<UserRepoJson> getRepositories(){
        return this.mRepoData;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.repository_cardview, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.v(TAG,"Binding List view");
        ViewHolder holder = (ViewHolder) view.getTag();
        String repoName = cursor.getString(RepositoriesFragment.COLUMN_NAME);
        String pushedAt = cursor.getString(RepositoriesFragment.COLUMN_PUSHED_AT);
        Integer forks = cursor.getInt(RepositoriesFragment.COLUMN_FORKS);
        Integer stars = cursor.getInt(RepositoriesFragment.COLUMN_STARGAZERS_COUNT);
        String language = cursor.getString(RepositoriesFragment.COLUMN_LANGUAGE);

        holder.repo_name_textview.setText(repoName);
        holder.fork_count_textview.setText(forks.toString());
        holder.stargazer_textview.setText(stars.toString());
        holder.language_textview.setText(language);
        holder.pushed_at_textview.setText(pushedAt);
    }
}
