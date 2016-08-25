package com.example.gaurav.gitfetchapp.Repositories.BranchDetails;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Commit implements Parcelable {

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
    private List<Object> parents = new ArrayList<Object>();

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
    public List<Object> getParents() {
        return parents;
    }

    /**
     *
     * @param parents
     * The parents
     */
    public void setParents(List<Object> parents) {
        this.parents = parents;
    }


    protected Commit(Parcel in) {
        sha = in.readString();
        commit = (Commit_) in.readValue(Commit_.class.getClassLoader());
        url = in.readString();
        htmlUrl = in.readString();
        commentsUrl = in.readString();
        author = (Author_) in.readValue(Author_.class.getClassLoader());
        committer = (Committer_) in.readValue(Committer_.class.getClassLoader());
        if (in.readByte() == 0x01) {
            parents = new ArrayList<Object>();
            in.readList(parents, Object.class.getClassLoader());
        } else {
            parents = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sha);
        dest.writeValue(commit);
        dest.writeString(url);
        dest.writeString(htmlUrl);
        dest.writeString(commentsUrl);
        dest.writeValue(author);
        dest.writeValue(committer);
        if (parents == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(parents);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Commit> CREATOR = new Parcelable.Creator<Commit>() {
        @Override
        public Commit createFromParcel(Parcel in) {
            return new Commit(in);
        }

        @Override
        public Commit[] newArray(int size) {
            return new Commit[size];
        }
    };
}