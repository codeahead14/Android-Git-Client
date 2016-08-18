package com.example.gaurav.gitfetchapp.Repositories.Commits;

import java.util.ArrayList;
import java.util.List;

import com.example.gaurav.gitfetchapp.Repositories.BranchDetails.Author_;
import com.example.gaurav.gitfetchapp.Repositories.BranchDetails.Commit_;
import com.example.gaurav.gitfetchapp.Repositories.BranchDetails.Committer;
import com.example.gaurav.gitfetchapp.Repositories.BranchDetails.Committer_;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommitsRepoJson {

    @SerializedName("sha")
    @Expose
    private String sha;
    @SerializedName("commit")
    @Expose
    private Commit_ commit;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("comments_url")
    @Expose
    private String commentsUrl;
    @SerializedName("author")
    @Expose
    private Author_ author;
    @SerializedName("committer")
    @Expose
    private Committer_ committer;
    @SerializedName("parents")
    @Expose
    private List<Parent> parents = new ArrayList<Parent>();

    /**
     *
     * @return
     * The sha
     */
    public String getSha() {
        return sha;
    }

    /**
     *
     * @param sha
     * The sha
     */
    public void setSha(String sha) {
        this.sha = sha;
    }

    /**
     *
     * @return
     * The commit
     */
    public Commit_ getCommit() {
        return commit;
    }

    /**
     *
     * @param commit
     * The commit
     */
    public void setCommit(Commit_ commit) {
        this.commit = commit;
    }

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
     * The author
     */
    public Author_ getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    public void setAuthor(Author_ author) {
        this.author = author;
    }

    /**
     *
     * @return
     * The committer
     */
    public Committer_ getCommitter() {
        return committer;
    }

    /**
     *
     * @param committer
     * The committer
     */
    public void setCommitter(Committer_ committer) {
        this.committer = committer;
    }

    /**
     *
     * @return
     * The parents
     */
    public List<Parent> getParents() {
        return parents;
    }

    /**
     *
     * @param parents
     * The parents
     */
    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }

}