package com.example.gaurav.gitfetchapp.Repositories.BranchDetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchDetailJson implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("commit")
    @Expose
    private Commit commit;
    @SerializedName("_links")
    @Expose
    private Links links;

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The commit
     */
    public Commit getCommit() {
        return commit;
    }

    /**
     *
     * @param commit
     * The commit
     */
    public void setCommit(Commit commit) {
        this.commit = commit;
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


    protected BranchDetailJson(Parcel in) {
        name = in.readString();
        commit = (Commit) in.readValue(Commit.class.getClassLoader());
        links = (Links) in.readValue(Links.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeValue(commit);
        dest.writeValue(links);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BranchDetailJson> CREATOR = new Parcelable.Creator<BranchDetailJson>() {
        @Override
        public BranchDetailJson createFromParcel(Parcel in) {
            return new BranchDetailJson(in);
        }

        @Override
        public BranchDetailJson[] newArray(int size) {
            return new BranchDetailJson[size];
        }
    };
}