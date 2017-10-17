package com.example.gaurav.gitfetchapp.Issues;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.Issue;
import com.example.gaurav.gitfetchapp.IssuesFragment;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.RecyclerViewParentAdapter;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryPagerFragment;
import com.example.gaurav.gitfetchapp.Utility;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 25-08-2016.
 */
public class IssuesRecyclerAdapter extends RecyclerViewParentAdapter{
    private static final String TAG = IssuesRecyclerAdapter.class.getName();
    private List<IssueItem> issuesList;
    private Context mContext;
    private String callFragment;
    private int loadingIndicator;
    private int loading_state;

    public IssuesRecyclerAdapter(Context context, List<IssueItem> items, String callFragment){
        this.mContext = context;
        this.issuesList = items;
        this.callFragment = callFragment;
    }

    public IssuesRecyclerAdapter(Context context, List<IssueItem> items, String callFragment,
                                 int loading_state){
        this.mContext = context;
        this.issuesList = items;
        this.callFragment = callFragment;
        this.loading_state = loading_state;
    }

    public void updateState(int state){
        this.loading_state = state;
    }

    public int getState(){
        return this.loading_state;
    }

    @Override
    //public IssuesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.issues_layout_item, parent, false);
            vh = new IssuesViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progressbar_viewholder, parent, false);
            vh = new ProgressBarViewHolder(v);
        }
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        if (callFragment.equals(IssuesPagerFragment.TAG)) {
            loadingIndicator = getState();
        }else if(callFragment.equals(RepositoryPagerFragment.TAG))
            loadingIndicator = RepositoryPagerFragment.loadingIndicator;
        if(position == issuesList.size()-1 && loadingIndicator != 1) {
            return VIEW_PROG;
        }else {
            return VIEW_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Typeface tf_1 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/RobotoCondensed-Regular.ttf");
        Typeface tf_2 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/Roboto-Light.ttf");

        if(viewHolder instanceof IssuesViewHolder) {
            IssuesViewHolder holder = (IssuesViewHolder) viewHolder;
            holder.issues_title.setText(issuesList.get(position).getTitle());
            holder.issues_title.setTypeface(tf_1);
            String action = "open";

            if (issuesList.get(position).getState().compareTo("open") == 0 ||
                    issuesList.get(position).getState().compareTo("opened") == 0) {
                action = "opened";
                holder.issues_image_icon.setImageResource(R.drawable.issue_opened_14x16);
            } else if (issuesList.get(position).getState().compareTo("close") == 0 ||
                    issuesList.get(position).getState().compareTo("closed") == 0) {
                action = "closed";
                holder.issues_image_icon.setImageResource(R.drawable.issue_closed_16x16);
            }
            Spanned str = Html.fromHtml("<b>#" + issuesList.get(position).getNumber() + "</b> " +
                    action + " by <b>" + issuesList.get(position).getUser().getLogin() + " " +
                    Utility.formatDateString(issuesList.get(position).getCreatedAt()) + "</b>");
            holder.issues_updated_desc.setText(str);
            holder.issues_updated_desc.setTypeface(tf_2);
        }else if(viewHolder instanceof ProgressBarViewHolder){
            ((ProgressBarViewHolder) viewHolder).materialProgressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        if (issuesList.size() != 0) {
            return issuesList.size();
        }
        return 0;
    }

    public void addItem(IssueItem item){
        issuesList.add(item);
        Log.v(TAG,"Adding item");
        notifyItemInserted(issuesList.size()-1);
    }

    public void clear(){
        issuesList.clear();
    }

    public class IssuesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView issues_image_icon;
        private TextView issues_title;
        private TextView issues_updated_desc;

        public IssuesViewHolder(View view){
            super(view);
            issues_image_icon = (ImageView) view.findViewById(R.id.issues_image_icon);
            issues_title = (TextView) view.findViewById(R.id.issues_title);
            issues_updated_desc = (TextView) view.findViewById(R.id.issues_updated_desc);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            int clickPos = getAdapterPosition();
            Intent intent = new Intent(mContext,IssueActivity.class);
            intent.putExtra(IssueActivity.ISSUE_CONTENTS,issuesList.get(clickPos));
            mContext.startActivity(intent);
        }
    }
}
