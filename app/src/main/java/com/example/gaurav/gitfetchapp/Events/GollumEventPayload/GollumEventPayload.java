package com.example.gaurav.gitfetchapp.Events.GollumEventPayload;

import java.util.ArrayList;
import java.util.List;

import com.example.gaurav.gitfetchapp.Events.Payload;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GollumEventPayload extends Payload {

    @SerializedName("pages")
    @Expose
    private List<Page> pages = new ArrayList<Page>();

    /**
     *
     * @return
     * The pages
     */
    public List<Page> getPages() {
        return pages;
    }

    /**
     *
     * @param pages
     * The pages
     */
    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

}
