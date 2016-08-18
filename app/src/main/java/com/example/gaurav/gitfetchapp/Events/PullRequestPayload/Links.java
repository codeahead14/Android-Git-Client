package com.example.gaurav.gitfetchapp.Events.PullRequestPayload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("self")
    @Expose
    private Self self;
    @SerializedName("html")
    @Expose
    private Html html;
    @SerializedName("issue")
    @Expose
    private Issue issue;
    @SerializedName("comments")
    @Expose
    private Comments comments;
    @SerializedName("review_comments")
    @Expose
    private ReviewComments reviewComments;
    @SerializedName("review_comment")
    @Expose
    private ReviewComment reviewComment;
    @SerializedName("commits")
    @Expose
    private Commits commits;
    @SerializedName("statuses")
    @Expose
    private Statuses statuses;

    /**
     *
     * @return
     * The self
     */
    public Self getSelf() {
        return self;
    }

    /**
     *
     * @param self
     * The self
     */
    public void setSelf(Self self) {
        this.self = self;
    }

    /**
     *
     * @return
     * The html
     */
    public Html getHtml() {
        return html;
    }

    /**
     *
     * @param html
     * The html
     */
    public void setHtml(Html html) {
        this.html = html;
    }

    /**
     *
     * @return
     * The issue
     */
    public Issue getIssue() {
        return issue;
    }

    /**
     *
     * @param issue
     * The issue
     */
    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    /**
     *
     * @return
     * The comments
     */
    public Comments getComments() {
        return comments;
    }

    /**
     *
     * @param comments
     * The comments
     */
    public void setComments(Comments comments) {
        this.comments = comments;
    }

    /**
     *
     * @return
     * The reviewComments
     */
    public ReviewComments getReviewComments() {
        return reviewComments;
    }

    /**
     *
     * @param reviewComments
     * The review_comments
     */
    public void setReviewComments(ReviewComments reviewComments) {
        this.reviewComments = reviewComments;
    }

    /**
     *
     * @return
     * The reviewComment
     */
    public ReviewComment getReviewComment() {
        return reviewComment;
    }

    /**
     *
     * @param reviewComment
     * The review_comment
     */
    public void setReviewComment(ReviewComment reviewComment) {
        this.reviewComment = reviewComment;
    }

    /**
     *
     * @return
     * The commits
     */
    public Commits getCommits() {
        return commits;
    }

    /**
     *
     * @param commits
     * The commits
     */
    public void setCommits(Commits commits) {
        this.commits = commits;
    }

    /**
     *
     * @return
     * The statuses
     */
    public Statuses getStatuses() {
        return statuses;
    }

    /**
     *
     * @param statuses
     * The statuses
     */
    public void setStatuses(Statuses statuses) {
        this.statuses = statuses;
    }

}