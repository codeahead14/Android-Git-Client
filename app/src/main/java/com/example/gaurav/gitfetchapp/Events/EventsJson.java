package com.example.gaurav.gitfetchapp.Events;

import android.util.Log;

import com.example.gaurav.gitfetchapp.Events.CreateCommentPayload.CreateCommentPayload;
import com.example.gaurav.gitfetchapp.Events.CreateEventPayload.CreateEventPayload;
import com.example.gaurav.gitfetchapp.Events.DeleteEventPayload.DeleteEventPayload;
import com.example.gaurav.gitfetchapp.Events.DeploymentEventPayload.DeploymentEventPayload;
import com.example.gaurav.gitfetchapp.Events.ForkEventPayload.ForkPayload;
import com.example.gaurav.gitfetchapp.Events.GollumEventPayload.GollumEventPayload;
import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.IssueCommentPayload;
import com.example.gaurav.gitfetchapp.Events.IssueEventPayload.IssueEventPayload;
import com.example.gaurav.gitfetchapp.Events.PullRequestPayload.PullRequest;
import com.example.gaurav.gitfetchapp.Events.PullRequestPayload.PullRequestPayload;
import com.example.gaurav.gitfetchapp.Events.PushEventPayload.PushEventPayload;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventsJson {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("actor")
    @Expose
    private Actor actor;
    @SerializedName("repo")
    @Expose
    private Repo repo;
    @SerializedName("payload")
    @Expose
    private Payload payload;
    @SerializedName("public")
    @Expose
    private Boolean _public;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("org")
    @Expose
    private Org org;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The actor
     */
    public Actor getActor() {
        return actor;
    }

    /**
     *
     * @param actor
     * The actor
     */
    public void setActor(Actor actor) {
        this.actor = actor;
    }

    /**
     *
     * @return
     * The repo
     */
    public Repo getRepo() {
        return repo;
    }

    /**
     *
     * @param repo
     * The repo
     */
    public void setRepo(Repo repo) {
        this.repo = repo;
    }

    /**
     *
     * @return
     * The payload
     */
    public Payload getPayload() {
        //return payload;
        String type = getType();
        Payload mPayload;

        /*if(payload instanceof CreateCommentPayload){
            CreateCommentPayload mPayload = (CreateCommentPayload) payload;
            return mPayload;
        } else if(payload instanceof CreateEventPayload)
            return (CreateEventPayload) payload;
        else if(payload instanceof DeleteEventPayload)
            return (DeleteEventPayload) payload;
        else if(payload instanceof DeploymentEventPayload)
            return (DeploymentEventPayload) payload;
        else if(payload instanceof ForkPayload)
            return (ForkPayload) payload;
        else if(payload instanceof GollumEventPayload)
            return (GollumEventPayload) payload;
        else if(payload instanceof IssueCommentPayload)
            return (IssueCommentPayload) payload;
        else if(payload instanceof IssueEventPayload)
            return (IssueEventPayload) payload;
        else if(payload instanceof PullRequestPayload)
            return (PullRequestPayload) payload;
        else if(payload instanceof PushEventPayload)
            return (PushEventPayload) payload;
        else
            return (PushEventPayload) payload;*/

        /*switch (type){
            case "IssueCommentEvent":
                IssueCommentPayload payload1 = (IssueCommentPayload) payload;
                return payload1;
            case "ForkEvent":
                //mPayload = new ForkPayload();
                ForkPayload payload2 = (ForkPayload) payload;
                return payload2;
            case "PushEvent":
                //mPayload = new PushEventPayload();
                PushEventPayload payload3 = (PushEventPayload) payload;
                return payload3;
            case "PullRequestEvent":
                PullRequestPayload payload4 = (PullRequestPayload) payload;
                return payload4;
            case "CreateEvent":
                CreateEventPayload payload5 = (CreateEventPayload) payload;
                return payload5;
            case "IssueEvent":
                IssueEventPayload payload6 = (IssueEventPayload) payload;
                return payload6;
            case "GollumEvent":
                GollumEventPayload payload7 = (GollumEventPayload) payload;
                return payload7;
            case "DeleteEvent":
                DeleteEventPayload payload8 = (DeleteEventPayload) payload;
                return payload8;
            case "DeploymentEvent":
                DeleteEventPayload payload9 = (DeleteEventPayload) payload;
                return payload9;
            default:
                PushEventPayload payload10 = (PushEventPayload) payload;
                return payload10;
        }*/

        return payload;
    }

    /**
     *
     * @param payload
     * The payload
     */
    public void setPayload(Payload payload) {
        //this.payload = payload;
        String type = this.getType();

        /*switch (type){
            case "IssueCommentEvent":
                this.payload = (IssueCommentPayload) payload;
                break;
            case "ForkEvent":
                this.payload = (ForkPayload) payload;
                break;
            case "PushEvent":
                this.payload = (PushEventPayload) payload;
                break;
            case "PullRequestEvent":
                this.payload = (PullRequestPayload) payload;
                break;
            case "CreateEvent":
                this.payload = (CreateEventPayload) payload;
                break;
            case "IssueEvent":
                this.payload = (IssueEventPayload) payload;
                break;
            case "GollumEvent":
                this.payload = (GollumEventPayload) payload;
                break;
            case "DeleteEvent":
                this.payload = (DeleteEventPayload) payload;
                break;
            case "DeploymentEvent":
                this.payload = (DeploymentEventPayload) payload;
                break;
            default:
                this.payload = (PushEventPayload)payload;
                break;
        }*/
        this.payload = payload;
    }

    /**
     *
     * @return
     * The _public
     */
    public Boolean getPublic() {
        return _public;
    }

    /**
     *
     * @param _public
     * The public
     */
    public void setPublic(Boolean _public) {
        this._public = _public;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The org
     */
    public Org getOrg() {
        return org;
    }

    /**
     *
     * @param org
     * The org
     */
    public void setOrg(Org org) {
        this.org = org;
    }

}