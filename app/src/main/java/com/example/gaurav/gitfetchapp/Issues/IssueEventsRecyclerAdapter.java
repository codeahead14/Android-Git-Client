package com.example.gaurav.gitfetchapp.Issues;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.CircleTransform;
import com.example.gaurav.gitfetchapp.CropSquareTransformation;
import com.example.gaurav.gitfetchapp.CustomViews.PicassoTextViewDrawable;
import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.Issue;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Utility;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by GAURAV on 14-01-2017.
 */

public class IssueEventsRecyclerAdapter extends RecyclerView.Adapter<IssueEventsRecyclerAdapter.IssueEventsViewHolder> {
    private ArrayList<IssueEventsJson> issueEventsJson;
    private Context context;

    public IssueEventsRecyclerAdapter(Context context, ArrayList<IssueEventsJson> issueEventsJson){
        this.context = context;
        this.issueEventsJson = issueEventsJson;
    }

    @Override
    public IssueEventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = (View) inflater.inflate(R.layout.issue_events_listitem,parent,false);

        return new IssueEventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IssueEventsViewHolder holder, int position) {
        String event;
        String postFix;

        /*Picasso.with(this.context)
                .load(R.drawable.ic_tag_outline)
                .transform(new CircleTransform())
                .resize(18,18)
                .into(holder.issue_event_tag);*/

        IssueEventsJson item = issueEventsJson.get(position);
        if(item.getEvent().compareTo("labeled") == 0){
            event = " added label ";
        } else if(item.getEvent().compareTo("unlabeled") == 0){
            event = " removed label";
        } else{
            event = " added label ";
        }
        postFix = "label on";
        //holder.issue_events_prefix.setText(String.format("%s %s",item.getActor().getLogin(),event));
        holder.issue_events_author.setText(item.getActor().getLogin());
        holder.issue_event_createdAt.setText(Utility.dateFormatConversion(item.getCreatedAt()));
        holder.issue_events_label.setText(item.getLabel().getName());
        holder.issue_event_action.setText(event);
        //holder.issue_events_postfix.setText(String.format("%s %s",postFix, Utility.dateFormatConversion(item.getCreatedAt())));
        GradientDrawable drawable = (GradientDrawable)holder.issue_events_label.getBackground();
        drawable.setColor(Color.parseColor("#"+item.getLabel().getColor()));

        final Target mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                Drawable mBitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
                holder.issue_events_author.setCompoundDrawablesWithIntrinsicBounds(mBitmapDrawable,
                        null,null,null);
            }

            @Override
            public void onBitmapFailed(Drawable drawable) {
                Log.d("DEBUG", "onBitmapFailed");
            }

            @Override
            public void onPrepareLoad(Drawable drawable) {
                Log.d("DEBUG", "onPrepareLoad");
            }
        };


        Picasso.with(context)
                .load(item.getActor().getAvatarUrl())
                .transform(new CircleTransform())
                .resize(72,72)
                .into(mTarget);

    }

    @Override
    public int getItemCount() {
        if (issueEventsJson.size() > 0)
            return issueEventsJson.size();
        return 0;
    }

    public void addItem(IssueEventsJson item){
        issueEventsJson.add(item);
        notifyItemInserted(issueEventsJson.size()-1);
    }

    public void clear(){
        issueEventsJson.clear();
    }

    public class IssueEventsViewHolder extends RecyclerView.ViewHolder{
        //TextView issue_events_prefix;
        TextView issue_events_author;
        TextView issue_events_label;
        TextView issue_event_createdAt;
        TextView issue_event_action;
        ImageView issue_event_tag;
        //TextView issue_events_postfix;

        public IssueEventsViewHolder(View view){
            super(view);
            issue_event_tag = (ImageView) view.findViewById(R.id.issue_event_tag);
            issue_events_author = (TextView) view.findViewById(R.id.issue_event_author);
            issue_event_createdAt = (TextView) view.findViewById(R.id.issue_event_createdAt);
            //issue_events_prefix = (TextView) view.findViewById(R.id.issue_event_prefix);
            issue_event_action = (TextView) view.findViewById(R.id.issue_event_action);
            issue_events_label = (TextView) view.findViewById(R.id.issue_event_label);
            //issue_events_postfix = (TextView) view.findViewById(R.id.issue_event_postfix);
        }
    }
}
