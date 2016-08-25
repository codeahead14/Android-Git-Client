package com.example.gaurav.gitfetchapp.Repositories.BranchDetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Commit_ implements Parcelable {

    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("committer")
    @Expose
    private Committer committer;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("tree")
    @Expose
    private Tree tree;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;

    /**
     *
     * @return
     * The author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     *
     * @return
     * The committer
     */
    public Committer getCommitter() {
        return committer;
    }

    /**
     *
     * @param committer
     * The committer
     */
    public void setCommitter(Committer committer) {
        this.committer = committer;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The tree
     */
    public Tree getTree() {
        return tree;
    }

    /**
     *
     * @param tree
     * The tree
     */
    public void setTree(Tree tree) {
        this.tree = tree;
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
     * The commentCount
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     *
     * @param commentCount
     * The comment_count
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }


    protected Commit_(Parcel in) {
        author = (Author) in.readValue(Author.class.getClassLoader());
        committer = (Committer) in.readValue(Committer.class.getClassLoader());
        message = in.readString();
        tree = (Tree) in.readValue(Tree.class.getClassLoader());
        url = in.readString();
        commentCount = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(author);
        dest.writeValue(committer);
        dest.writeString(message);
        dest.writeValue(tree);
        dest.writeString(url);
        if (commentCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(commentCount);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Commit_> CREATOR = new Parcelable.Creator<Commit_>() {
        @Override
        public Commit_ createFromParcel(Parcel in) {
            return new Commit_(in);
        }

        @Override
        public Commit_[] newArray(int size) {
            return new Commit_[size];
        }
    };
}