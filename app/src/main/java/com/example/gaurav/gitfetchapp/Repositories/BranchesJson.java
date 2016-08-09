package com.example.gaurav.gitfetchapp.Repositories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchesJson {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("commit")
    @Expose
    private Commit commit;

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

}