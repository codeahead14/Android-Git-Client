package com.example.gaurav.gitfetchapp.Events.PullRequestPayload;

import java.util.ArrayList;
import java.util.List;

import com.example.gaurav.gitfetchapp.Events.IssueCommentPayload.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PullRequest {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("diff_url")
    @Expose
    private String diffUrl;
    @SerializedName("patch_url")
    @Expose
    private String patchUrl;
    @SerializedName("issue_url")
    @Expose
    private String issueUrl;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("locked")
    @Expose
    private Boolean locked;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("closed_at")
    @Expose
    private Object closedAt;
    @SerializedName("merged_at")
    @Expose
    private Object mergedAt;
    @SerializedName("merge_commit_sha")
    @Expose
    private Object mergeCommitSha;
    @SerializedName("assignee")
    @Expose
    private Object assignee;
    @SerializedName("assignees")
    @Expose
    private List<Object> assignees = new ArrayList<Object>();
    @SerializedName("milestone")
    @Expose
    private Object milestone;
    @SerializedName("commits_url")
    @Expose
    private String commitsUrl;
    @SerializedName("review_comments_url")
    @Expose
    private String reviewCommentsUrl;
    @SerializedName("review_comment_url")
    @Expose
    private String reviewCommentUrl;
    @SerializedName("comments_url")
    @Expose
    private String commentsUrl;
    @SerializedName("statuses_url")
    @Expose
    private String statusesUrl;
    @SerializedName("head")
    @Expose
    private Head head;
    @SerializedName("base")
    @Expose
    private Base base;
    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("merged")
    @Expose
    private Boolean merged;
    @SerializedName("mergeable")
    @Expose
    private Object mergeable;
    @SerializedName("mergeable_state")
    @Expose
    private String mergeableState;
    @SerializedName("merged_by")
    @Expose
    private Object mergedBy;
    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("review_comments")
    @Expose
    private Integer reviewComments;
    @SerializedName("commits")
    @Expose
    private Integer commits;
    @SerializedName("additions")
    @Expose
    private Integer additions;
    @SerializedName("deletions")
    @Expose
    private Integer deletions;
    @SerializedName("changed_files")
    @Expose
    private Integer changedFiles;

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
     * The diffUrl
     */
    public String getDiffUrl() {
        return diffUrl;
    }

    /**
     *
     * @param diffUrl
     * The diff_url
     */
    public void setDiffUrl(String diffUrl) {
        this.diffUrl = diffUrl;
    }

    /**
     *
     * @return
     * The patchUrl
     */
    public String getPatchUrl() {
        return patchUrl;
    }

    /**
     *
     * @param patchUrl
     * The patch_url
     */
    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }

    /**
     *
     * @return
     * The issueUrl
     */
    public String getIssueUrl() {
        return issueUrl;
    }

    /**
     *
     * @param issueUrl
     * The issue_url
     */
    public void setIssueUrl(String issueUrl) {
        this.issueUrl = issueUrl;
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
    public Object getClosedAt() {
        return closedAt;
    }

    /**
     *
     * @param closedAt
     * The closed_at
     */
    public void setClosedAt(Object closedAt) {
        this.closedAt = closedAt;
    }

    /**
     *
     * @return
     * The mergedAt
     */
    public Object getMergedAt() {
        return mergedAt;
    }

    /**
     *
     * @param mergedAt
     * The merged_at
     */
    public void setMergedAt(Object mergedAt) {
        this.mergedAt = mergedAt;
    }

    /**
     *
     * @return
     * The mergeCommitSha
     */
    public Object getMergeCommitSha() {
        return mergeCommitSha;
    }

    /**
     *
     * @param mergeCommitSha
     * The merge_commit_sha
     */
    public void setMergeCommitSha(Object mergeCommitSha) {
        this.mergeCommitSha = mergeCommitSha;
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
     * The commitsUrl
     */
    public String getCommitsUrl() {
        return commitsUrl;
    }

    /**
     *
     * @param commitsUrl
     * The commits_url
     */
    public void setCommitsUrl(String commitsUrl) {
        this.commitsUrl = commitsUrl;
    }

    /**
     *
     * @return
     * The reviewCommentsUrl
     */
    public String getReviewCommentsUrl() {
        return reviewCommentsUrl;
    }

    /**
     *
     * @param reviewCommentsUrl
     * The review_comments_url
     */
    public void setReviewCommentsUrl(String reviewCommentsUrl) {
        this.reviewCommentsUrl = reviewCommentsUrl;
    }

    /**
     *
     * @return
     * The reviewCommentUrl
     */
    public String getReviewCommentUrl() {
        return reviewCommentUrl;
    }

    /**
     *
     * @param reviewCommentUrl
     * The review_comment_url
     */
    public void setReviewCommentUrl(String reviewCommentUrl) {
        this.reviewCommentUrl = reviewCommentUrl;
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
     * The statusesUrl
     */
    public String getStatusesUrl() {
        return statusesUrl;
    }

    /**
     *
     * @param statusesUrl
     * The statuses_url
     */
    public void setStatusesUrl(String statusesUrl) {
        this.statusesUrl = statusesUrl;
    }

    /**
     *
     * @return
     * The head
     */
    public Head getHead() {
        return head;
    }

    /**
     *
     * @param head
     * The head
     */
    public void setHead(Head head) {
        this.head = head;
    }

    /**
     *
     * @return
     * The base
     */
    public Base getBase() {
        return base;
    }

    /**
     *
     * @param base
     * The base
     */
    public void setBase(Base base) {
        this.base = base;
    }

    /**
     *
     * @return
     * The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     *
     * @param links
     * The _links
     */
    public void setLinks(Links links) {
        this.links = links;
    }

    /**
     *
     * @return
     * The merged
     */
    public Boolean getMerged() {
        return merged;
    }

    /**
     *
     * @param merged
     * The merged
     */
    public void setMerged(Boolean merged) {
        this.merged = merged;
    }

    /**
     *
     * @return
     * The mergeable
     */
    public Object getMergeable() {
        return mergeable;
    }

    /**
     *
     * @param mergeable
     * The mergeable
     */
    public void setMergeable(Object mergeable) {
        this.mergeable = mergeable;
    }

    /**
     *
     * @return
     * The mergeableState
     */
    public String getMergeableState() {
        return mergeableState;
    }

    /**
     *
     * @param mergeableState
     * The mergeable_state
     */
    public void setMergeableState(String mergeableState) {
        this.mergeableState = mergeableState;
    }

    /**
     *
     * @return
     * The mergedBy
     */
    public Object getMergedBy() {
        return mergedBy;
    }

    /**
     *
     * @param mergedBy
     * The merged_by
     */
    public void setMergedBy(Object mergedBy) {
        this.mergedBy = mergedBy;
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
     * The reviewComments
     */
    public Integer getReviewComments() {
        return reviewComments;
    }

    /**
     *
     * @param reviewComments
     * The review_comments
     */
    public void setReviewComments(Integer reviewComments) {
        this.reviewComments = reviewComments;
    }

    /**
     *
     * @return
     * The commits
     */
    public Integer getCommits() {
        return commits;
    }

    /**
     *
     * @param commits
     * The commits
     */
    public void setCommits(Integer commits) {
        this.commits = commits;
    }

    /**
     *
     * @return
     * The additions
     */
    public Integer getAdditions() {
        return additions;
    }

    /**
     *
     * @param additions
     * The additions
     */
    public void setAdditions(Integer additions) {
        this.additions = additions;
    }

    /**
     *
     * @return
     * The deletions
     */
    public Integer getDeletions() {
        return deletions;
    }

    /**
     *
     * @param deletions
     * The deletions
     */
    public void setDeletions(Integer deletions) {
        this.deletions = deletions;
    }

    /**
     *
     * @return
     * The changedFiles
     */
    public Integer getChangedFiles() {
        return changedFiles;
    }

    /**
     *
     * @param changedFiles
     * The changed_files
     */
    public void setChangedFiles(Integer changedFiles) {
        this.changedFiles = changedFiles;
    }

}