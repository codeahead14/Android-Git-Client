package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by GAURAV on 30-07-2016.
 */
public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {
    private Context mContext;
    private ArrayList<UserRepoJson> mUserRepo = new ArrayList<>();

    public class RepositoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView repo_name_textview;
        public RepositoryViewHolder(View view){
            super(view);
            repo_name_textview = (TextView) view.findViewById(R.id.repo_name_text);
            repo_name_textview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext,"OnClick",Toast.LENGTH_SHORT).show();
        }
    }

    public RepositoryAdapter(Context context, int layoutResourceId, ArrayList<UserRepoJson> userRep0){
        mContext = context;
        mUserRepo = userRep0;
    }

    @Override
    public RepositoryAdapter.RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View repositoryView = layoutInflater.inflate(R.layout.repository_cardview,parent,false);
        Log.v("Adapter","inflating card view");
        RepositoryViewHolder repositoryViewHolder = new RepositoryViewHolder(repositoryView);
        return repositoryViewHolder;
    }

    @Override
    public void onBindViewHolder(RepositoryAdapter.RepositoryViewHolder holder, int position) {
        Log.v("Adapter","position: "+position);
        String repoName = mUserRepo.get(position).getName();
        holder.repo_name_textview.setText(repoName);
    }

    @Override
    public int getItemCount() {
        int size = 0;
        //if(mUserRepo != null)
            size = mUserRepo.size();
        return size;
    }

    public void addItem(UserRepoJson item){
        mUserRepo.add(item);
        notifyItemInserted(mUserRepo.size()-1);
    }

    public void setData(ArrayList<UserRepoJson> data){
        //mUserRepo = data;
        for(UserRepoJson item : data)
            mUserRepo.add(item);
        Log.v("RepositoryAdapter","Setting data "+mUserRepo.size());
        notifyItemInserted(mUserRepo.size()-1);
    }
}
