package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Repositories.TreeDetails.*;
import com.example.gaurav.gitfetchapp.Repositories.TreeDetails.Tree;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

/**
 * Created by GAURAV on 07-08-2016.
 */
public class FilesRecyclerAdapter extends RecyclerView.Adapter<FilesRecyclerAdapter.FilesViewHolder> {
    private static final String TAG = FilesRecyclerAdapter.class.getName();
    private Context mContext;
    //private TreeDetailsJson files;
    private ArrayList<RepoContentsJson> files;
    private String mOwner;
    private String mRepoName;
    private String mBranch;

    //public FilesRecyclerAdapter(Context context, TreeDetailsJson item, String owner, String repo){
    public FilesRecyclerAdapter(Context context, ArrayList<RepoContentsJson> item, String owner,
                                String repo, String branch){
        this.mContext = context;
        files = item;
        this.mOwner = owner;
        this.mRepoName = repo;
        this.mBranch = branch;
    }

    @Override
    public FilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.repository_files_layout,
                parent,false);
        FilesViewHolder filesViewHolder = new FilesViewHolder(view);
        return filesViewHolder;
    }

    @Override
    public void onBindViewHolder(FilesViewHolder holder, int position) {
        Log.v(TAG,"position: "+position);
        if(files.get(position).getType().compareTo("file") == 0)
            holder.files_type_imageview.setImageResource(R.drawable.file_text_24x28dp);
        else
            holder.files_type_imageview.setImageResource(R.drawable.file_dir_24x26dp);
        holder.file_name_textview.setText(files.get(position).getName());
        //holder.file_name_textview.setText(files.getTree().get(position).getPath());
    }

    //public void addItem(com.example.gaurav.gitfetchapp.Repositories.TreeDetails.Tree item){
    public void addItem(RepoContentsJson item){
        files.add(item);
        notifyItemInserted(files.size()-1);
        /*files.getTree().add(item);
        Log.v(TAG,"size: "+files.getTree().size());
        notifyItemInserted(files.getTree().size()-1);*/
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
            GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                    GitHubEndpointInterface.class);

            if(Utility.hasConnection(mContext)) {
                //avLoadingIndicatorView.show();
                if (type.compareTo("file") == 0) {
                    String download_url = files.get(clickPos).getDownloadUrl().toString();
                    Call<ResponseBody> call = gitHubEndpointInterface.downloadFileWithDynamicUrlSync(download_url);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Log.d(TAG, "server contacted and has file");
                                BufferedReader reader = null;
                                StringBuilder sb = new StringBuilder();
                                try {
                                    reader = new BufferedReader(new InputStreamReader(
                                            response.body().byteStream()));
                                    String line;
                                    try {
                                        while ((line = reader.readLine()) != null) {
                                            sb.append(line);
                                            sb.append("\n");
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    ;
                                } finally {

                                }
                                Log.v(TAG, sb.toString());
                                //avLoadingIndicatorView.hide();
                                //file_name_textview.setText(sb.toString());
                                Intent intent = new Intent(mContext, FileViewActivity.class);
                                intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                                Log.v(TAG, "intent string: " + sb.toString());
                                mContext.startActivity(intent);
                                //boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                                //Log.d(TAG, "file download was a success? " + writtenToDisk);
                            } else {
                                Log.d(TAG, "server contact failed");
                                //avLoadingIndicatorView.hide();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e(TAG, "error");
                           // avLoadingIndicatorView.hide();
                        }
                    });
                } else {
                    //avLoadingIndicatorView.show();
                    String path = files.get(clickPos).getPath();
                    Call<ArrayList<RepoContentsJson>> call = gitHubEndpointInterface.getRepoContents(
                            mOwner, mRepoName, path, mBranch);
                    call.enqueue(new Callback<ArrayList<RepoContentsJson>>() {
                        @Override
                        public void onResponse(Call<ArrayList<RepoContentsJson>> call,
                                               Response<ArrayList<RepoContentsJson>> response) {
                            clear();
                            ArrayList<RepoContentsJson> item = response.body();
                            for (RepoContentsJson elem : item)
                                addItem(elem);
                            notifyDataSetChanged();
                            //avLoadingIndicatorView.hide();
                        }

                        @Override
                        public void onFailure(Call<ArrayList<RepoContentsJson>> call, Throwable t) {
                            //avLoadingIndicatorView.hide();
                        }
                    });

                }
                //Call<TreeDetailsJson> call = gitHubEndpointInterface.getRepoTree(mOwner,mRepoName,
                //      files.getTree().get(clickPos).getType()+"s",files.getTree().get(clickPos).getSha());
            /*call.enqueue(new Callback<TreeDetailsJson>() {
                @Override
                public void onResponse(Call<TreeDetailsJson> call, Response<TreeDetailsJson> response) {
                    TreeDetailsJson item = response.body();
                    if(item != null){
                        Log.v(TAG,"response Received + item not null ");
                        files.getTree().clear();
                        for (Tree elem : item.getTree())
                            addItem(elem);
                        notifyDataSetChanged();
                    }else
                        Log.v(TAG,"response Received + item null ");
                }

                @Override
                public void onFailure(Call<TreeDetailsJson> call, Throwable t) {
                    Log.v(TAG,"Failure response");
                }
            });*/
            }else
                Toast.makeText(mContext, mContext.getResources().getString(R.string.notOnline), Toast.LENGTH_SHORT).show();
        }
    }
}
