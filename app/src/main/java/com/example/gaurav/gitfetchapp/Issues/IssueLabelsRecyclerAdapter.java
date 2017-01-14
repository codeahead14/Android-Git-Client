package com.example.gaurav.gitfetchapp.Issues;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.R;

import java.util.ArrayList;

/**
 * Created by GAURAV on 13-01-2017.
 */

public class IssueLabelsRecyclerAdapter extends RecyclerView.Adapter<IssueLabelsRecyclerAdapter.IssueLabelsViewHolder> {
    private final static String TAG = IssueLabelsRecyclerAdapter.class.getName();
    private Context mContext;
    private ArrayList<String> issueLabels;
    private IssueItem issueItem;

    public class IssueLabelsViewHolder extends RecyclerView.ViewHolder{
        private TextView issue_labels_text;

        public IssueLabelsViewHolder(View view){
            super(view);
            issue_labels_text = (TextView)view.findViewById(R.id.issue_label);
        }
    }

    public IssueLabelsRecyclerAdapter(Context context, ArrayList<String> labels, IssueItem item){
        mContext = context;
        issueLabels = labels;
        issueItem = item;
    }

    @Override
    public IssueLabelsRecyclerAdapter.IssueLabelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = (View) inflater.inflate(R.layout.issue_labels_listitem,parent,false);

        return new IssueLabelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IssueLabelsViewHolder holder, int position) {
        holder.issue_labels_text.setText(issueLabels.get(position));
        GradientDrawable labelBackground = (GradientDrawable)holder.issue_labels_text.getBackground();
        labelBackground.setColor(Color.parseColor("#"+issueItem.getLabels().get(position).getColor()));
        //Log.v(TAG,"Color "+issueItem.getLabels().get(position).getColor());
        //labelBackground.setColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        if(issueLabels.size() >0)
            return issueLabels.size();
        return 0;
    }

    public void addItem(String item){
        issueLabels.add(item);
        notifyItemInserted(issueLabels.size()-1);
    }

    public void clear(){
        issueLabels.clear();
    }
}
