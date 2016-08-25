package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.CircleTransform;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Repositories.Commits.CommitsRepoJson;
import com.example.gaurav.gitfetchapp.Utility;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by GAURAV on 17-08-2016.
 */
public class CommitsRecyclerAdapter extends RecyclerView.Adapter<CommitsRecyclerAdapter.CommitsViewHolder> {
    private static final String TAG = CommitsRecyclerAdapter.class.getName();
    private Context mContext;
    private ArrayList<CommitsRepoJson> commitsList;

    public CommitsRecyclerAdapter(Context context, ArrayList<CommitsRepoJson> commitsRepoJsonArrayList){
        this.mContext = context;
        this.commitsList = commitsRepoJsonArrayList;
    }

    @Override
    public CommitsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.repository_commits_layout,parent,false);
        CommitsViewHolder viewHolder = new CommitsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommitsViewHolder holder, int position) {
        Typeface tf_1 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/RobotoCondensed-Regular.ttf");
        Typeface tf_2 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/Roboto-Light.ttf");
        String committer = commitsList.get(position).getCommit().getCommitter().getName();
        String commitMsg = commitsList.get(position).getCommit().getMessage();
        Spanned commit_action = Html.fromHtml("<b>" + committer + "</b>" + " committed on " + "<b>" +
                Utility.formatDateString(commitsList.get(position).getCommit().getAuthor().getDate())+ " </b>");

        holder.commit_msg_textView.setText(commitMsg);
        holder.committer_name_text.setText(commit_action);
        holder.commit_msg_textView.setTypeface(tf_1);
        holder.committer_name_text.setTypeface(tf_2);

        Picasso.with(mContext)
                .load(commitsList.get(position).getCommitter().getAvatarUrl())
                .transform(new CircleTransform())
                .into(holder.committer_avatar_imageView);
    }

    @Override
    public int getItemCount() {
        if(commitsList != null)
            return commitsList.size();
        return 0;
    }

    public void addItem(CommitsRepoJson item){
        commitsList.add(item);
        notifyItemInserted(commitsList.size()-1);
    }

    public void clear(){
        commitsList.clear();
    }

    public class CommitsViewHolder extends RecyclerView.ViewHolder{
        private TextView commit_msg_textView;
        private TextView commit_branch_name_textView;
        private TextView committer_name_text;
        private ImageView committer_avatar_imageView;

        public CommitsViewHolder(View view){
            super(view);
            commit_msg_textView = (TextView) view.findViewById(R.id.commit_msg_text);
            commit_branch_name_textView = (TextView) view.findViewById(R.id.commit_branch_name_text);
            committer_name_text = (TextView) view.findViewById(R.id.committer_name_text);
            committer_avatar_imageView = (ImageView) view.findViewById(R.id.committer_avatar_img);
        }
    }
}
