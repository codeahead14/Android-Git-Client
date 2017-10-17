package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Repositories.TreeDetails.*;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.gaurav.gitfetchapp.Repositories.FileViewActivity.FILE_NAME;
import static com.example.gaurav.gitfetchapp.Repositories.FileViewActivity.FILE_URL;

/**
 * Created by GAURAV on 07-08-2016.
 */
//public class FilesRecyclerAdapter extends RecyclerView.Adapter<FilesRecyclerAdapter.FilesViewHolder> {
public class FilesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = FilesRecyclerAdapter.class.getName();
    private Context mContext;
    private ArrayList<RepoContentsJson> files;
    private HorizontalListRecyclerAdapter listAdapter;
    private String mOwner;
    private String mRepoName;
    private String mBranch;
    private static final int HEADER = 0, CONTENT = 1;

    //public FilesRecyclerAdapter(Context context, TreeDetailsJson item, String owner, String repo){
    public FilesRecyclerAdapter(Context context, ArrayList<RepoContentsJson> item, String owner,
                                String repo, String branch, HorizontalListRecyclerAdapter listArray){
        this.mContext = context;
        files = item;
        this.mOwner = owner;
        this.mRepoName = repo;
        this.mBranch = branch;
        this.listAdapter = listArray;

        listAdapter.setItemListener(new HorizontalListRecyclerAdapter.OnItemClick() {
            @Override
            public void OnItemClickListener(String path) {
                fetchContents("dir",0,path.substring(0,path.length()-1), false);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case CONTENT:
                final View view = layoutInflater.inflate(R.layout.repository_files_layout,
                        parent,false);
                FilesViewHolder filesViewHolder = new FilesViewHolder(view);
                return filesViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case CONTENT:
                if(holder instanceof FilesViewHolder) {
                    FilesViewHolder fh = (FilesViewHolder) holder;
                    if (files.get(position).getType().compareTo("file") == 0)
                        fh.files_type_imageview.setImageResource(R.drawable.file_text_24x28dp);
                    else
                        fh.files_type_imageview.setImageResource(R.drawable.file_dir_24x26dp);
                    fh.file_name_textview.setText(files.get(position).getName());
                }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return CONTENT;
    }

    //public void addItem(com.example.gaurav.gitfetchapp.Repositories.TreeDetails.Tree item){
    public void addItem(RepoContentsJson item){
        files.add(item);
        notifyItemInserted(files.size()-1);
    }

    public void clear(){
        files.clear();
        //files.getTree().clear();
    }

    @Override
    public int getItemCount() {
        if(files != null)
            return files.size();
          //  return files.getTree().size();
        return 0;
    }

    public void fetchContents(String type, final int clickPos, String path,final boolean flag){
        GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class);

        if(Utility.hasConnection(mContext)) {
            //avLoadingIndicatorView.show();
            if (type.compareTo("file") == 0) {
                String fileName = files.get(clickPos).getName();
                String download_url = files.get(clickPos).getDownloadUrl().toString();
                Intent intent = new Intent(mContext, FileViewActivity.class);
                intent.putExtra(FILE_NAME,fileName);
                intent.putExtra(FILE_URL, download_url);
                mContext.startActivity(intent);
            } else {
                //String path = files.get(clickPos).getPath();
                Log.v(TAG,path);
                final String endPoint = path.split("/")[path.split("/").length-1];
                if(endPoint.compareTo(mBranch) == 0)
                    path = "";
                Call<ArrayList<RepoContentsJson>> call = gitHubEndpointInterface.getRepoContents(
                        mOwner, mRepoName, path, mBranch);
                call.enqueue(new Callback<ArrayList<RepoContentsJson>>() {
                    @Override
                    public void onResponse(Call<ArrayList<RepoContentsJson>> call,
                                           Response<ArrayList<RepoContentsJson>> response) {
                        if(response.isSuccessful()) {
                            Log.v(TAG, "response successful");
                            clear();
                            ArrayList<RepoContentsJson> item = response.body();
                            for (RepoContentsJson elem : item)
                                addItem(elem);
                            notifyDataSetChanged();
                            if(flag)
                                listAdapter.addItem(endPoint);
                            else
                                listAdapter.removeItem(endPoint);
                            listAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<RepoContentsJson>> call, Throwable t) {
                    }
                });

            }
        }else
            Toast.makeText(mContext, mContext.getResources().getString(R.string.notOnline), Toast.LENGTH_SHORT).show();

    }

    public class FilesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView file_name_textview;
        private ImageView files_type_imageview;
       // private AVLoadingIndicatorView avLoadingIndicatorView;

        FilesViewHolder(View view){
            super(view);
            file_name_textview = (TextView) view.findViewById(R.id.file_name_text);
            files_type_imageview = (ImageView) view.findViewById(R.id.file_img);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPos = getAdapterPosition();
            String type = files.get(clickPos).getType();
            String path = files.get(clickPos).getPath();
            fetchContents(type,clickPos,path,true);
        }
    }
}
