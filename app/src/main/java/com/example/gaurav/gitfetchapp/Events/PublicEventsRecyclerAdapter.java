package com.example.gaurav.gitfetchapp.Events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.gaurav.gitfetchapp.Events.MemberEventsPayload.MemberEventPayload;
import com.example.gaurav.gitfetchapp.Events.PullRequestPayload.PullRequestPayload;
import com.example.gaurav.gitfetchapp.Events.PushEventPayload.PushEventPayload;
import com.example.gaurav.gitfetchapp.Events.WatchEventPayload.WatchEventPayload;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.IntentServices.RepositoryDetailsReceiver;
import com.example.gaurav.gitfetchapp.IntentServices.RepositoryDetailsService;
import com.example.gaurav.gitfetchapp.IssuesFragment;
import com.example.gaurav.gitfetchapp.PostLoginActivity;
import com.example.gaurav.gitfetchapp.PrivateFeedsFragment;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.RecyclerViewParentAdapter;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryDetailActivity;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryDetailActivityFragment;
import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;
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
public class PublicEventsRecyclerAdapter extends RecyclerViewParentAdapter{
        //RecyclerView.Adapter<PublicEventsRecyclerAdapter.PublicEventsViewHolder> {

    private static final String TAG = PublicEventsRecyclerAdapter.class.getName();
    public static final String ServiceTag = "RepositoryName";
    private ArrayList<EventsJson> eventsJsonArrayList;
    private Context mContext;
    private RepositoryDetailsReceiver receiver;

    public PublicEventsRecyclerAdapter(Context context, ArrayList<EventsJson> list) {
        this.mContext = context;
        this.eventsJsonArrayList = list;
        setupServiceReceiver();
    }

    public void setupServiceReceiver() {
        receiver = new RepositoryDetailsReceiver(new Handler());
        receiver.setReceiver(new RepositoryDetailsReceiver.Receiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == Activity.RESULT_OK) {
                    UserRepoJson resultValue = resultData.getParcelable(Intent.EXTRA_TEXT);
                    Intent intent = new Intent(mContext, RepositoryDetailActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT,resultValue);
                    mContext.startActivity(intent);
                    //Toast.makeText(mContext, resultValue.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if(position == eventsJsonArrayList.size()-1 && PrivateFeedsFragment.loadingEvents != 1) {
            Log.v(TAG,"View PROG");
            return VIEW_PROG;
        }else {;
            return VIEW_ITEM;
        }
    }

    @Override
    //public PublicEventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.public_events_item, parent, false);
            vh = new PublicEventsViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progressbar_viewholder, parent, false);
            vh = new ProgressBarViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof ProgressBarViewHolder){
            ((ProgressBarViewHolder) viewHolder).materialProgressBar.setIndeterminate(true);
        }else if(viewHolder instanceof PublicEventsViewHolder) {
            final PublicEventsViewHolder holder = (PublicEventsViewHolder) viewHolder;

            Payload payload = eventsJsonArrayList.get(position).getPayload();
            String type = eventsJsonArrayList.get(position).getType();
            String participantName;
            String avatarUrl;
            String description;
            Spanned description2;
            Resources res = mContext.getResources();

            participantName = eventsJsonArrayList.get(position).getActor().getLogin();
            holder.git_participant_text_view.setText(participantName);
            holder.git_timeStamp_text_view.setText(Utility.formatDateString(
                    eventsJsonArrayList.get(position).getCreatedAt()));

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
                description2 = android.text.Html.fromHtml("created " + ((CreateEventPayload) payload).getRefType() +
                        " <b>" +
                        eventsJsonArrayList.get(position).getRepo().getName() + "</b>");
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
                         eventsJsonArrayList.get(position).getRepo().getName() + "</b>" + " to " + "<b>" +
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
                /*description = String.format(res.getString(R.string.issue_event),
                        ((IssueEventPayload) payload).getAction(),
                        ((IssueEventPayload) payload).getIssue().getNumber(),
                        eventsJsonArrayList.get(position).getRepo().getName());*/
                description2 = android.text.Html.fromHtml("<b>" +
                        ((IssueEventPayload) payload).getAction() + "</b>" + " issue " +
                        "<b>" + ((IssueEventPayload) payload).getIssue().getNumber() + "</b>" +
                        "<b>" + eventsJsonArrayList.get(position).getRepo().getName() + "</b>");
            } else if (payload instanceof PullRequestPayload) {
                /*description = String.format(res.getString(R.string.pull_request_event),
                        ((PullRequestPayload) payload).getAction(),
                        ((PullRequestPayload) payload).getNumber(),
                        eventsJsonArrayList.get(position).getRepo().getName());*/
                description2 = android.text.Html.fromHtml("<b>" +
                        ((PullRequestPayload) payload).getAction() + "</b>" + " pull request " +
                        "<b>" + ((PullRequestPayload) payload).getNumber() + "</b>" + " at " +
                        "<b>" + eventsJsonArrayList.get(position).getRepo().getName() + "</b>");
            } else if (payload instanceof PushEventPayload) {
                //description = String.format(res.getString(R.string.push_event),
                //      actor, ((PushEventPayload) payload).getRef());
                String[] arr = ((PushEventPayload) payload).getRef().split("/");
                String branch = arr[arr.length - 1];
                description2 = android.text.Html.fromHtml("pushed to " + "<b>" +
                        branch + "</b>" + " at " + "<b>" + eventsJsonArrayList.get(position).getRepo().getName());
                //Log.v(TAG," string text "+ description);
            } else if (payload instanceof WatchEventPayload) {
                description = String.format(res.getString(R.string.watch_event),
                        actor, ((WatchEventPayload) payload).getAction());
                description2 = android.text.Html.fromHtml(" starred " +
                        "<b>" + eventsJsonArrayList.get(position).getRepo().getName() + "</b>");
            } else if (payload instanceof MemberEventPayload) {
                description2 = android.text.Html.fromHtml("<b> " + eventsJsonArrayList.get(position).getActor().getLogin()
                        + "</b>" + " " + ((MemberEventPayload) payload).getAction() + "<b>" + " " +
                        ((MemberEventPayload) payload).getMember().getLogin() + "</b>" + " to " + "<b>" +
                        eventsJsonArrayList.get(position).getRepo().getName() + "</b>");
            } else
                description2 = android.text.Html.fromHtml("Information Update Pending");
            //description = "Information Update Pending";

            holder.git_description_text_view.setText(description2);
            holder.git_description_text_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, RepositoryDetailsService.class);
                    intent.putExtra(ServiceTag, eventsJsonArrayList.get(holder.getAdapterPosition())
                            .getRepo().getUrl());
                    intent.putExtra("ServiceCallback", receiver);
                    mContext.startService(intent);
                }
            });
            //String str = android.text.Html.fromHtml(res.getText(R.string.push_event))
            //holder.git_description_text_view.setText(android.text.Html.fromHtml(description));
        }
    }

    @Override
    public int getItemCount() {
        if (eventsJsonArrayList != null) {
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
        private TextView git_timeStamp_text_view;

        public PublicEventsViewHolder(View view) {
            super(view);
            Typeface tf_1 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/RobotoCondensed-Regular.ttf");
            Typeface tf_2 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/Roboto-Light.ttf");
            git_participant_text_view = (TextView) view.findViewById(R.id.git_participant_text);
            git_participant_text_view.setTypeface(tf_2);
            git_description_text_view = (TextView) view.findViewById(R.id.event_description_text);
            git_description_text_view.setTypeface(tf_1);
            git_avatar_image_view = (ImageView) view.findViewById(R.id.git_avatar_img);
            git_timeStamp_text_view = (TextView) view.findViewById(R.id.git_time_stamp);

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
