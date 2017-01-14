package com.example.gaurav.gitfetchapp.Issues;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PullRequest implements Parcelable {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("diff_url")
    @Expose
    private String diffUrl;
    @SerializedName("patch_url")
    @Expose
    private String patchUrl;

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The htmlUrl
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     * @param htmlUrl The html_url
     */
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    /**
     * @return The diffUrl
     */
    public String getDiffUrl() {
        return diffUrl;
    }

    /**
     * @param diffUrl The diff_url
     */
    public void setDiffUrl(String diffUrl) {
        this.diffUrl = diffUrl;
    }

    /**
     * @return The patchUrl
     */
    public String getPatchUrl() {
        return patchUrl;
    }

    /**
     * @param patchUrl The patch_url
     */
    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }

    protected PullRequest(Parcel in) {
        url = in.readString();
        htmlUrl = in.readString();
        diffUrl = in.readString();
        patchUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(htmlUrl);
        dest.writeString(diffUrl);
        dest.writeString(patchUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PullRequest> CREATOR = new Parcelable.Creator<PullRequest>() {
        @Override
        public PullRequest createFromParcel(Parcel in) {
            return new PullRequest(in);
        }

        @Override
        public PullRequest[] newArray(int size) {
            return new PullRequest[size];
        }
    };
}