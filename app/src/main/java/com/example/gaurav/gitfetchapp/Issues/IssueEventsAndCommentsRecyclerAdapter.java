package com.example.gaurav.gitfetchapp.Issues;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.CircleTransform;
import com.example.gaurav.gitfetchapp.CropSquareTransformation;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Utility;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Objects;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.example.gaurav.gitfetchapp.R.id.sort_comments;
import static com.example.gaurav.gitfetchapp.R.id.view;

/**
 * Created by GAURAV on 23-01-2017.
 */

public class IssueEventsAndCommentsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = IssueEventsAndCommentsRecyclerAdapter.class.getName();
    private ArrayList<Object> eventCommentsList;
    private Context context;
    private static final int VIEW_EVENTS = 0;
    private static final int VIEW_COMMENTS =1;

    private class IssueComentsViewHolder extends RecyclerView.ViewHolder{
        TextView issue_comments_author_date;
        TextView issue_comment_body;
        ImageView issue_comment_author_img;

        private IssueComentsViewHolder(View itemView){
            super(itemView);
            issue_comments_author_date = (TextView)itemView.findViewById(R.id.issue_comment_author_date);
            issue_comment_body = (TextView) itemView.findViewById(R.id.issue_comment_body);
            issue_comment_author_img = (ImageView) itemView.findViewById(R.id.issue_comment_author_img);
        }
    }

    private class IssueEventsViewHolder extends RecyclerView.ViewHolder{
        ImageView issue_allevent_tag;
        TextView issue_allevent_details;

        private IssueEventsViewHolder (View view){
            super(view);
            issue_allevent_tag = (ImageView) view.findViewById(R.id.issue_allevent_tag);
            issue_allevent_details = (TextView) view.findViewById(R.id.issue_allevent_details);
        }
    }

    public IssueEventsAndCommentsRecyclerAdapter(Context context, ArrayList<Object> items){
        this.context = context;
        this.eventCommentsList = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType){
            case VIEW_EVENTS:
                View v1 = inflater.inflate(R.layout.issue_all_events_listitem,parent,false);
                viewHolder = new IssueEventsViewHolder(v1);
                break;
            case VIEW_COMMENTS:
                View v2 = (View)inflater.inflate(R.layout.issue_comments_item,parent,false);
                viewHolder = new IssueComentsViewHolder(v2);
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Typeface tf_1 = Typeface.createFromAsset(context.getResources().getAssets(),"font/RobotoCondensed-Regular.ttf");
        Typeface tf_2 = Typeface.createFromAsset(context.getResources().getAssets(),"font/Roboto-Regular.ttf");

        if(eventCommentsList.get(position) instanceof IssueCommentsJson){
            IssueCommentsJson item = (IssueCommentsJson)eventCommentsList.get(position);
            IssueComentsViewHolder viewHolder = (IssueComentsViewHolder) holder;
            viewHolder.issue_comments_author_date.setText(Html.fromHtml(
                    "<b>" + item.getUser().getLogin() + "</b>" + "<b>" + " . " + "</b>" +
                            Utility.dateFormatConversion(item.getCreatedAt())
            ));

            viewHolder.issue_comment_body.setText(item.getBody());
            viewHolder.issue_comment_body.setTypeface(tf_1);
            Picasso.with(context)
                    .load(((IssueCommentsJson) eventCommentsList.get(position)).getUser().getAvatarUrl())
                    .transform(new CircleTransform())
                    .resize(72,72)
                    .into(viewHolder.issue_comment_author_img);
        }else if(eventCommentsList.get(position) instanceof IssueEventsJson){
            IssueEventsJson item = (IssueEventsJson) eventCommentsList.get(position);
            final IssueEventsViewHolder viewHolder = (IssueEventsViewHolder) holder;
            //viewHolder.issue_allevent_tag.
            switch (item.getEvent().toLowerCase()){
                case "referenced":
                    viewHolder.issue_allevent_tag.setImageResource(R.drawable.ic_bookmark);
                    break;
                case "closed":
                    viewHolder.issue_allevent_tag.setImageResource(R.drawable.ic_circle_slash);
                    break;
                case "reopened":
                    viewHolder.issue_allevent_tag.setImageResource(R.drawable.ic_issue_reopened);
                    break;
                default:
                    viewHolder.issue_allevent_tag.setImageResource(R.drawable.ic_bookmark);
            }

            if(item.getEvent().toLowerCase().compareTo("referenced") ==0){
                viewHolder.issue_allevent_tag.setImageResource(R.drawable.ic_bookmark);
            }

            viewHolder.issue_allevent_details.setText(Html.fromHtml("<b>"+item.getActor().getLogin()
                                + "</b>" + " " + item.getEvent() + " on " + "<b>" +
            Utility.dateFormatConversion(item.getCreatedAt()) + "</b>"));
            final Target mTarget = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    Drawable mBitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
                    viewHolder.issue_allevent_details.setCompoundDrawablesWithIntrinsicBounds(mBitmapDrawable,
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
    }

    @Override
    public int getItemViewType(int position) {
        if(eventCommentsList.get(position) instanceof IssueEventsJson){
            Log.v(TAG,"type events");
            return VIEW_EVENTS;
        }else if (eventCommentsList.get(position) instanceof IssueCommentsJson){
            return VIEW_COMMENTS;
        }
        return -1;
    }

    public void addItem(Object item){
        eventCommentsList.add(item);
        notifyItemInserted(eventCommentsList.size()-1);
    }

    @Override
    public int getItemCount() {
        if(eventCommentsList.size() > 0){
            return eventCommentsList.size();
        }
        return 0;
    }
}
