package com.example.gaurav.gitfetchapp.Issues;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueItem implements Parcelable {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("repository_url")
    @Expose
    private String repositoryUrl;
    @SerializedName("labels_url")
    @Expose
    private String labelsUrl;
    @SerializedName("comments_url")
    @Expose
    private String commentsUrl;
    @SerializedName("events_url")
    @Expose
    private String eventsUrl;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("labels")
    @Expose
    private List<IssueLabel> labels = new ArrayList<IssueLabel>();
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("locked")
    @Expose
    private Boolean locked;
    @SerializedName("assignee")
    @Expose
    private Object assignee;
    @SerializedName("assignees")
    @Expose
    private List<Object> assignees = new ArrayList<Object>();
    @SerializedName("milestone")
    @Expose
    private Object milestone;
    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("closed_at")
    @Expose
    private String closedAt;
    @SerializedName("pull_request")
    @Expose
    private PullRequest pullRequest;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("score")
    @Expose
    private Double score;

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The repositoryUrl
     */
    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    /**
     *
     * @param repositoryUrl
     * The repository_url
     */
    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    /**
     *
     * @return
     * The labelsUrl
     */
    public String getLabelsUrl() {
        return labelsUrl;
    }

    /**
     *
     * @param labelsUrl
     * The labels_url
     */
    public void setLabelsUrl(String labelsUrl) {
        this.labelsUrl = labelsUrl;
    }

    /**
     *
     * @return
     * The commentsUrl
     */
    public String getCommentsUrl() {
        return commentsUrl;
    }

    /**
     *
     * @param commentsUrl
     * The comments_url
     */
    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    /**
     *
     * @return
     * The eventsUrl
     */
    public String getEventsUrl() {
        return eventsUrl;
    }

    /**
     *
     * @param eventsUrl
     * The events_url
     */
    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    /**
     *
     * @return
     * The htmlUrl
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     *
     * @param htmlUrl
     * The html_url
     */
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     *
     * @param number
     * The number
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The labels
     */
    public List<IssueLabel> getLabels() {
        return labels;
    }

    /**
     *
     * @param labels
     * The labels
     */
    public void setLabels(List<IssueLabel> labels) {
        this.labels = labels;
    }

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The locked
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     *
     * @param locked
     * The locked
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     *
     * @return
     * The assignee
     */
    public Object getAssignee() {
        return assignee;
    }

    /**
     *
     * @param assignee
     * The assignee
     */
    public void setAssignee(Object assignee) {
        this.assignee = assignee;
    }

    /**
     *
     * @return
     * The assignees
     */
    public List<Object> getAssignees() {
        return assignees;
    }

    /**
     *
     * @param assignees
     * The assignees
     */
    public void setAssignees(List<Object> assignees) {
        this.assignees = assignees;
    }

    /**
     *
     * @return
     * The milestone
     */
    public Object getMilestone() {
        return milestone;
    }

    /**
     *
     * @param milestone
     * The milestone
     */
    public void setMilestone(Object milestone) {
        this.milestone = milestone;
    }

    /**
     *
     * @return
     * The comments
     */
    public Integer getComments() {
        return comments;
    }

    /**
     *
     * @param comments
     * The comments
     */
    public void setComments(Integer comments) {
        this.comments = comments;
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
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The closedAt
     */
    public String getClosedAt() {
        return closedAt;
    }

    /**
     *
     * @param closedAt
     * The closed_at
     */
    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    /**
     *
     * @return
     * The pullRequest
     */
    public PullRequest getPullRequest() {
        return pullRequest;
    }

    /**
     *
     * @param pullRequest
     * The pull_request
     */
    public void setPullRequest(PullRequest pullRequest) {
        this.pullRequest = pullRequest;
    }

    /**
     *
     * @return
     * The body
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @param body
     * The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     *
     * @return
     * The score
     */
    public Double getScore() {
        return score;
    }

    /**
     *
     * @param score
     * The score
     */
    public void setScore(Double score) {
        this.score = score;
    }



    protected IssueItem(Parcel in) {
        url = in.readString();
        repositoryUrl = in.readString();
        labelsUrl = in.readString();
        commentsUrl = in.readString();
        eventsUrl = in.readString();
        htmlUrl = in.readString();
        id = in.readByte() == 0x00 ? null : in.readInt();
        number = in.readByte() == 0x00 ? null : in.readInt();
        title = in.readString();
        user = (User) in.readValue(User.class.getClassLoader());
        if (in.readByte() == 0x01) {
            labels = new ArrayList<IssueLabel>();
            in.readList(labels, IssueLabel.class.getClassLoader());
        } else {
            labels = null;
        }
        state = in.readString();
        byte lockedVal = in.readByte();
        locked = lockedVal == 0x02 ? null : lockedVal != 0x00;
        assignee = (Object) in.readValue(Object.class.getClassLoader());
        if (in.readByte() == 0x01) {
            assignees = new ArrayList<Object>();
            in.readList(assignees, Object.class.getClassLoader());
        } else {
            assignees = null;
        }
        milestone = (Object) in.readValue(Object.class.getClassLoader());
        comments = in.readByte() == 0x00 ? null : in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();
        closedAt = in.readString();
        pullRequest = (PullRequest) in.readValue(PullRequest.class.getClassLoader());
        body = in.readString();
        score = in.readByte() == 0x00 ? null : in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(repositoryUrl);
        dest.writeString(labelsUrl);
        dest.writeString(commentsUrl);
        dest.writeString(eventsUrl);
        dest.writeString(htmlUrl);
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        if (number == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(number);
        }
        dest.writeString(title);
        dest.writeValue(user);
        if (labels == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(labels);
        }
        dest.writeString(state);
        if (locked == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (locked ? 0x01 : 0x00));
        }
        dest.writeValue(assignee);
        if (assignees == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(assignees);
        }
        dest.writeValue(milestone);
        if (comments == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(comments);
        }
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(closedAt);
        dest.writeValue(pullRequest);
        dest.writeString(body);
        if (score == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(score);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<IssueItem> CREATOR = new Parcelable.Creator<IssueItem>() {
        @Override
        public IssueItem createFromParcel(Parcel in) {
            return new IssueItem(in);
        }

        @Override
        public IssueItem[] newArray(int size) {
            return new IssueItem[size];
        }
    };
}