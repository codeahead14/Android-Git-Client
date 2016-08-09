package com.example.gaurav.gitfetchapp.Gists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Files {

    @SerializedName("filename")
    @Expose
    private List<Filename> filename;

    /**
     *
     * @return
     * The filename
     */
    public List<Filename> getFilename() {
        return filename;
    }

    /**
     *
     * @param filename
     * The filename
     */
    public void setFilename(List<Filename> filename) {
        this.filename = filename;
    }

}