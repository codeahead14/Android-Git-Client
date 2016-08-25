package com.example.gaurav.gitfetchapp.Events;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.CircleTransform;
import com.example.gaurav.gitfetchapp.Events.CreateEventPayload.CreateEventPayload;
import com.example.gaurav.gitfetchapp.Events.DeleteEventPayload.DeleteEventPayload;
import com.example.gaurav.gitfetchapp.Events.DeploymentEventPayload.DeploymentEventPayload;
import com.example.gaurav.gitfetchapp.Events.ForkEventPayload.ForkPayload;
import com.example.gaurav.gitfetchapp.Events.GollumEventPayload.GollumEventPayload;
import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.IssueCommentPayload;
import com.example.gaurav.gitfetchapp.Events.IssueEventPayload.IssueEventPayload;
import com.example.gaurav.gitfetchapp.Events.PullRequestPayload.PullRequestPayload;
import com.example.gaurav.gitfetchapp.Events.PushEventPayload.PushEventPayload;
import com.example.gaurav.gitfetchapp.Events.WatchEventPayload.WatchEventPayload;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.PostLoginActivity;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.UserInfo.User;
import com.example.gaurav.gitfetchapp.UserInfoActivity;
import com.example.gaurav.gitfetchapp.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GAURAV on 13-08-2016.
 */
public class PublicEventsRecyclerAdapter extends
        RecyclerView.Adapter<PublicEventsRecyclerAdapter.PublicEventsViewHolder> {

    private static final String TAG = PublicEventsRecyclerAdapter.class.getName();
    private ArrayList<EventsJson> eventsJsonArrayList;
    private Context mContext;

    public PublicEventsRecyclerAdapter(Context context, ArrayList<EventsJson> list) {
        this.mContext = context;
        this.eventsJsonArrayList = list;
    }

    @Override
    public PublicEventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.public_events_item, parent, false);
        PublicEventsViewHolder viewHolder = new PublicEventsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PublicEventsViewHolder holder, int position) {
        Payload payload = eventsJsonArrayList.get(position).getPayload();
        String type = eventsJsonArrayList.get(position).getType();
        String participantName;
        String avatarUrl;
        String description;
        Spanned description2;
        Resources res = mContext.getResources();

        participantName = eventsJsonArrayList.get(position).getActor().getLogin();
        holder.git_participant_text_view.setText(participantName);

        avatarUrl = eventsJsonArrayList.get(position).getActor().getAvatarUrl();
        Picasso.with(mContext)
                .load(avatarUrl)
                .transform(new CircleTransform())
                .into(holder.git_avatar_image_view);

        String actor = eventsJsonArrayList.get(position).getActor().getLogin();

        if (payload instanceof IssueCommentPayload) {
            //description = String.format(res.getString(R.string.issue_comment_event),
            //      ((IssueCommentPayload) payload).getAction(),
            //    ((IssueCommentPayload) payload).getIssue().getNumber(),
            //  eventsJsonArrayList.get(position).getRepo().getName());
            description2 = android.text.Html.fromHtml("<b>" + ((IssueCommentPayload) payload).getAction() + "</b>"
                    + " comment on issue " + "<b>" + ((IssueCommentPayload) payload).getIssue().getNumber()
                    + "</b>" + " at " + "<b>" + eventsJsonArrayList.get(position).getRepo().getName()
                    + " </b> ");
        } else if (payload instanceof CreateEventPayload) {
            //description = String.format(res.getString(R.string.create_event),
            //      ((CreateEventPayload)payload).getRefType());
            description2 = android.text.Html.fromHtml("created " + "<b>" +
                    ((CreateEventPayload) payload).getRefType() + "</b");
        } else if (payload instanceof DeleteEventPayload) {
            //description = String.format(res.getString(R.string.delete_event),
            //      ((DeleteEventPayload)payload).getRefType(),
            //    ((DeleteEventPayload)payload).getRef());
            description2 = android.text.Html.fromHtml("deleted " + "<b>" +
                    ((DeleteEventPayload) payload).getRefType() + "</b>" + " at " +
                    "<b>" + ((DeleteEventPayload) payload).getRef() + "</b>");
        } else if (payload instanceof DeploymentEventPayload) {
            description = String.format(res.getString(R.string.deployment_event));
            description2 = android.text.Html.fromHtml(res.getString(R.string.deployment_event));
        } else if (payload instanceof ForkPayload) {
            description = String.format(res.getString(R.string.fork_event),
                    ((ForkPayload) payload).getForkee().getFullName());
            description2 = android.text.Html.fromHtml("forked " + "<b>" +
                    ((ForkPayload) payload).getForkee().getFullName() + "</b>");
        } else if (payload instanceof GollumEventPayload) {
            description = String.format(res.getString(R.string.gollum_event),
                    ((GollumEventPayload) payload).getPages().get(0).getAction(),
                    ((GollumEventPayload) payload).getPages().get(0).getPageName(),
                    ((GollumEventPayload) payload).getPages().get(0).getHtmlUrl());
            description2 = android.text.Html.fromHtml("<b>" +
                    ((GollumEventPayload) payload).getPages().get(0).getAction() + "</b>" +
                    "<b>" + ((GollumEventPayload) payload).getPages().get(0).getPageName() + "</b>" +
                    " page at " +
                    "<b>" + ((GollumEventPayload) payload).getPages().get(0).getHtmlUrl() + "</b>");
        } else if (payload instanceof IssueEventPayload) {
            description = String.format(res.getString(R.string.issue_event),
                    ((IssueEventPayload) payload).getAction(),
                    ((IssueEventPayload) payload).getIssue().getNumber(),
                    eventsJsonArrayList.get(position).getRepo().getName());
            description2 = android.text.Html.fromHtml("<b>" +
                    ((IssueEventPayload) payload).getAction() + "</b>" + " issue " +
                    "<b>" + ((IssueEventPayload) payload).getIssue().getNumber() + "</b>" +
                    "<b>" + eventsJsonArrayList.get(position).getRepo().getName() + "</b>");
        } else if (payload instanceof PullRequestPayload) {
            description = String.format(res.getString(R.string.pull_request_event),
                    ((PullRequestPayload) payload).getAction(),
                    ((PullRequestPayload) payload).getNumber(),
                    eventsJsonArrayList.get(position).getRepo().getName());
            description2 = android.text.Html.fromHtml("<b>" +
                    ((PullRequestPayload) payload).getAction() + "</b>" + " pull request " +
                    "<b>" + ((PullRequestPayload) payload).getNumber() + "</b>" + " at " +
                    "<b>" + eventsJsonArrayList.get(position).getRepo().getName() + "</b>");
        } else if (payload instanceof PushEventPayload) {
            //description = String.format(res.getString(R.string.push_event),
            //      actor, ((PushEventPayload) payload).getRef());
            description2 = android.text.Html.fromHtml("pushed to " + "<b>" +
                    ((PushEventPayload) payload).getRef() + "</b>");
            //Log.v(TAG," string text "+ description);
        } else if (payload instanceof WatchEventPayload) {
            description = String.format(res.getString(R.string.watch_event),
                    actor, ((WatchEventPayload) payload).getAction());
            description2 = android.text.Html.fromHtml("<b>" + ((WatchEventPayload) payload).getAction() + "</b>"
                    + " watching " + "<b>" + eventsJsonArrayList.get(position).getRepo().getName() + "</b>");
        } else
            description2 = android.text.Html.fromHtml("Information Update Pending");
        //description = "Information Update Pending";

        holder.git_description_text_view.setText(description2);
        //String str = android.text.Html.fromHtml(res.getText(R.string.push_event))
        //holder.git_description_text_view.setText(android.text.Html.fromHtml(description));

    }

    @Override
    public int getItemCount() {
        if (eventsJsonArrayList != null) {
            Log.v(TAG, "size : " + eventsJsonArrayList.size());
            return eventsJsonArrayList.size();
        }
        return 0;
    }

    public void clear() {
        eventsJsonArrayList.clear();
    }

    public void addItem(EventsJson item) {
        eventsJsonArrayList.add(item);
        notifyItemInserted(eventsJsonArrayList.size() - 1);
    }

    public class PublicEventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView git_participant_text_view;
        private TextView git_description_text_view;
        private ImageView git_avatar_image_view;

        public PublicEventsViewHolder(View view) {
            super(view);
            Typeface tf_1 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/RobotoCondensed-Regular.ttf");
            Typeface tf_2 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/Roboto-Light.ttf");
            git_participant_text_view = (TextView) view.findViewById(R.id.git_participant_text);
            git_participant_text_view.setTypeface(tf_2);
            git_description_text_view = (TextView) view.findViewById(R.id.event_description_text);
            git_description_text_view.setTypeface(tf_1);
            git_avatar_image_view = (ImageView) view.findViewById(R.id.git_avatar_img);

            git_participant_text_view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPos = getAdapterPosition();
            final Intent intent = new Intent(mContext, UserInfoActivity.class);

            if(Utility.hasConnection(mContext)) {
                GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                        GitHubEndpointInterface.class);
                Call<User> call = gitHubEndpointInterface.getUserDetails(eventsJsonArrayList
                        .get(clickPos)
                        .getActor()
                        .getLogin());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            User item = response.body();
                            intent.putExtra(PostLoginActivity.USER_DETAILS, item);
                            mContext.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(mContext, "Request Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else
                Toast.makeText(mContext, mContext.getResources().getString(R.string.notOnline), Toast.LENGTH_SHORT).show();
        }
    }
}
