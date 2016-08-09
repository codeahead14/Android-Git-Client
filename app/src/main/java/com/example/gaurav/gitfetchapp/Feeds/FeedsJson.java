package com.example.gaurav.gitfetchapp.Feeds;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedsJson {

    @SerializedName("timeline_url")
    @Expose
    private String timelineUrl;
    @SerializedName("user_url")
    @Expose
    private String userUrl;
    @SerializedName("current_user_public_url")
    @Expose
    private String currentUserPublicUrl;
    @SerializedName("current_user_url")
    @Expose
    private String currentUserUrl;
    @SerializedName("current_user_actor_url")
    @Expose
    private String currentUserActorUrl;
    @SerializedName("current_user_organization_url")
    @Expose
    private String currentUserOrganizationUrl;
    @SerializedName("current_user_organization_urls")
    @Expose
    private List<Object> currentUserOrganizationUrls = new ArrayList<Object>();
    @SerializedName("_links")
    @Expose
    private Links links;

    /**
     *
     * @return
     * The timelineUrl
     */
    public String getTimelineUrl() {
        return timelineUrl;
    }

    /**
     *
     * @param timelineUrl
     * The timeline_url
     */
    public void setTimelineUrl(String timelineUrl) {
        this.timelineUrl = timelineUrl;
    }

    /**
     *
     * @return
     * The userUrl
     */
    public String getUserUrl() {
        return userUrl;
    }

    /**
     *
     * @param userUrl
     * The user_url
     */
    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    /**
     *
     * @return
     * The currentUserPublicUrl
     */
    public String getCurrentUserPublicUrl() {
        return currentUserPublicUrl;
    }

    /**
     *
     * @param currentUserPublicUrl
     * The current_user_public_url
     */
    public void setCurrentUserPublicUrl(String currentUserPublicUrl) {
        this.currentUserPublicUrl = currentUserPublicUrl;
    }

    /**
     *
     * @return
     * The currentUserUrl
     */
    public String getCurrentUserUrl() {
        return currentUserUrl;
    }

    /**
     *
     * @param currentUserUrl
     * The current_user_url
     */
    public void setCurrentUserUrl(String currentUserUrl) {
        this.currentUserUrl = currentUserUrl;
    }

    /**
     *
     * @return
     * The currentUserActorUrl
     */
    public String getCurrentUserActorUrl() {
        return currentUserActorUrl;
    }

    /**
     *
     * @param currentUserActorUrl
     * The current_user_actor_url
     */
    public void setCurrentUserActorUrl(String currentUserActorUrl) {
        this.currentUserActorUrl = currentUserActorUrl;
    }

    /**
     *
     * @return
     * The currentUserOrganizationUrl
     */
    public String getCurrentUserOrganizationUrl() {
        return currentUserOrganizationUrl;
    }

    /**
     *
     * @param currentUserOrganizationUrl
     * The current_user_organization_url
     */
    public void setCurrentUserOrganizationUrl(String currentUserOrganizationUrl) {
        this.currentUserOrganizationUrl = currentUserOrganizationUrl;
    }

    /**
     *
     * @return
     * The currentUserOrganizationUrls
     */
    public List<Object> getCurrentUserOrganizationUrls() {
        return currentUserOrganizationUrls;
    }

    /**
     *
     * @param currentUserOrganizationUrls
     * The current_user_organization_urls
     */
    public void setCurrentUserOrganizationUrls(List<Object> currentUserOrganizationUrls) {
        this.currentUserOrganizationUrls = currentUserOrganizationUrls;
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

}