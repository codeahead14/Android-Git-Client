package com.example.gaurav.gitfetchapp.Issues;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.R;

import java.util.ArrayList;

/**
 * Created by GAURAV on 15-01-2017.
 */

public class IssueCommentsRecyclerAdapter extends RecyclerView.Adapter<IssueCommentsRecyclerAdapter.IssueCommentsViewHolder> {
    private static final String TAG = IssueCommentsRecyclerAdapter.class.getName();
    private ArrayList<IssueCommentsJson> issueCommentsJsons;
    private Context context;

    public IssueCommentsRecyclerAdapter(Context context, ArrayList<IssueCommentsJson> issueCommentsJsons){
        this.context = context;
        this.issueCommentsJsons = issueCommentsJsons;
    }

    @Override
    public IssueCommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater.from(parent.getContext()));
        View view = inflater.inflate(R.layout.issue_comments_item,parent,false);

        return new IssueCommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IssueCommentsViewHolder holder, int position) {
        Log.v(TAG,"size "+issueCommentsJsons.size());
        if (getItemCount() > 0) {
            IssueCommentsJson item = issueCommentsJsons.get(position);
            holder.issue_comments_author_date.setText(Html.fromHtml(
                    "<b>" + item.getUser().getLogin() + "</b>" + "commented on " +
                            "<b>" + item.getCreatedAt()
            ));

            holder.issue_comment_body.setText(item.getBody());
        }
    }

    public void addItem(IssueCommentsJson elem){
        issueCommentsJsons.add(elem);
        notifyItemInserted(issueCommentsJsons.size()-1);
    }

    private void clear(){
        issueCommentsJsons.clear();
    }


    @Override
    public int getItemCount() {
        if(issueCommentsJsons.size() > 0)
            return issueCommentsJsons.size();
        return 0;
    }

    public class IssueCommentsViewHolder extends RecyclerView.ViewHolder{
        TextView issue_comments_author_date;
        TextView issue_comment_body;

        public IssueCommentsViewHolder(View view){
            super(view);
            issue_comments_author_date = (TextView)view.findViewById(R.id.issue_comment_author_date);
            issue_comment_body = (TextView) view.findViewById(R.id.issue_comment_body);
        }
    }
}
