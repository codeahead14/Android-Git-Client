package com.example.gaurav.gitfetchapp.Events;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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
import com.example.gaurav.gitfetchapp.OnDataFetchFinished;
import com.example.gaurav.gitfetchapp.PublicEventsFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 * Created by GAURAV on 14-08-2016.
 */
public class EventsAsyncTask extends AsyncTask<String, Void, ArrayList<EventsJson>>{

    private static final String TAG = EventsAsyncTask.class.getName();
    private String responseJSONStr;
    private PublicEventsRecyclerAdapter adapter;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private ProgressBar progressBar;
    private OnDataFetchFinished onDataFetchFinished;

    public EventsAsyncTask(PublicEventsRecyclerAdapter adapter, OnDataFetchFinished callback){
        this.adapter = adapter;
        this.onDataFetchFinished = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //avLoadingIndicatorView.show();
    }

    @Override
    protected ArrayList<EventsJson> doInBackground(String... params) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.github.com")
                .appendPath("events");

        //String events_url = builder.toString();
        String events_url = params[0];
        Log.v(TAG,"Url: "+events_url);
        try {
            URL url = new URL(events_url);
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                int responseCode;
                try {
                    responseCode = urlConnection.getResponseCode();
                } catch (IOException e) {
                    responseCode = urlConnection.getResponseCode();
                }
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    responseJSONStr = null;
                    return null;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    responseJSONStr = null;
                }
                responseJSONStr = buffer.toString();
                return parseJsonResult(responseJSONStr);
            } catch (IOException e) {
                Log.v(TAG, "url connection exception");
            }
        } catch (MalformedURLException e) {
            Log.v(TAG, "malformed url");
        }

        return null;
    }

    private ArrayList<EventsJson> parseJsonResult(String result) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            JSONArray jsonArray = new JSONArray(result);
            ArrayList<EventsJson> items = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject post = jsonArray.getJSONObject(i);
                EventsJson item = new EventsJson();
                String type = post.getString("type");
                String id = post.getString("id");
                Actor actor = gson.fromJson(post.get("actor").toString(),Actor.class);
                Repo repo = gson.fromJson(post.get("repo").toString(), Repo.class);
                Boolean isPublic = (post.getString("public").compareTo("true")==0) ? true : false;
                String created_at = post.getString("created_at");

                switch (type) {
                    case "IssueCommentEvent":
                        IssueCommentPayload payload1 = gson.fromJson(post.get("payload").toString(),
                                IssueCommentPayload.class);
                        item.setPayload(payload1);
                        break;
                    case "ForkEvent":
                        ForkPayload payload2 = gson.fromJson(post.get("payload").toString(),
                                ForkPayload.class);
                        item.setPayload(payload2);
                        break;
                    case "PushEvent":
                        PushEventPayload payload3 = gson.fromJson(post.get("payload").toString(),
                                PushEventPayload.class);
                        item.setPayload(payload3);
                        break;
                    case "PullRequestEvent":
                        PullRequestPayload payload4 = gson.fromJson(post.get("payload").toString(),
                                PullRequestPayload.class);
                        item.setPayload(payload4);
                        break;
                    case "CreateEvent":
                        CreateEventPayload payload5 = gson.fromJson(post.get("payload").toString(),
                                CreateEventPayload.class);
                        item.setPayload(payload5);
                        break;
                    case "IssueEvent":
                        IssueEventPayload payload6 = gson.fromJson(post.get("payload").toString(),
                                IssueEventPayload.class);
                        item.setPayload(payload6);
                        break;
                    case "GollumEvent":
                        GollumEventPayload payload7 = gson.fromJson(post.get("payload").toString(),
                                GollumEventPayload.class);
                        item.setPayload(payload7);
                        break;
                    case "DeleteEvent":
                        DeleteEventPayload payload8 = gson.fromJson(post.get("payload").toString(),
                                DeleteEventPayload.class);
                        item.setPayload(payload8);
                        break;
                    case "DeploymentEvent":
                        DeploymentEventPayload payload9 = gson.fromJson(post.get("payload").toString(),
                                DeploymentEventPayload.class);
                        item.setPayload(payload9);
                        break;
                    case "WatchEvent":
                        WatchEventPayload payload10 = gson.fromJson(post.get("payload").toString(),
                                WatchEventPayload.class);
                        item.setPayload(payload10);
                        break;
                    case "MemberEvent":
                        MemberEventPayload payload11 = gson.fromJson(post.get("payload").toString(),
                                MemberEventPayload.class);
                        item.setPayload(payload11);
                        break;
                    default:
                        Payload payload12 = gson.fromJson(post.get("payload").toString(),
                                Payload.class);
                        item.setPayload(payload12);
                        break;
                }

                item.setId(id);
                item.setType(type);
                item.setActor(actor);
                item.setRepo(repo);
                item.setPublic(isPublic);
                item.setCreatedAt(created_at);
                //item.setOrg(org);

                items.add(item);
            }
            return items;
        } catch (JSONException j) {
            Log.v(TAG, "jsonexception " + j.getMessage());

        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<EventsJson> eventsJsons) {
        super.onPostExecute(eventsJsons);
        onDataFetchFinished.onDataFetchFinishedCallback();
        if(eventsJsons.size() == 0)
            adapter.notifyDataSetChanged();
        else{
            for (EventsJson elem : eventsJsons) {
                adapter.addItem(elem);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
