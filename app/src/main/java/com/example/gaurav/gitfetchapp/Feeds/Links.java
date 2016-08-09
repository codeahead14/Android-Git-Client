package com.example.gaurav.gitfetchapp.Feeds;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("timeline")
    @Expose
    private Timeline timeline;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("current_user_public")
    @Expose
    private CurrentUserPublic currentUserPublic;
    @SerializedName("current_user")
    @Expose
    private CurrentUser currentUser;
    @SerializedName("current_user_actor")
    @Expose
    private CurrentUserActor currentUserActor;
    @SerializedName("current_user_organization")
    @Expose
    private CurrentUserOrganization currentUserOrganization;
    @SerializedName("current_user_organizations")
    @Expose
    private List<Object> currentUserOrganizations = new ArrayList<Object>();

    /**
     *
     * @return
     * The timeline
     */
    public Timeline getTimeline() {
        return timeline;
    }

    /**
     *
     * @param timeline
     * The timeline
     */
    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
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
     * The currentUserPublic
     */
    public CurrentUserPublic getCurrentUserPublic() {
        return currentUserPublic;
    }

    /**
     *
     * @param currentUserPublic
     * The current_user_public
     */
    public void setCurrentUserPublic(CurrentUserPublic currentUserPublic) {
        this.currentUserPublic = currentUserPublic;
    }

    /**
     *
     * @return
     * The currentUser
     */
    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    /**
     *
     * @param currentUser
     * The current_user
     */
    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    /**
     *
     * @return
     * The currentUserActor
     */
    public CurrentUserActor getCurrentUserActor() {
        return currentUserActor;
    }

    /**
     *
     * @param currentUserActor
     * The current_user_actor
     */
    public void setCurrentUserActor(CurrentUserActor currentUserActor) {
        this.currentUserActor = currentUserActor;
    }

    /**
     *
     * @return
     * The currentUserOrganization
     */
    public CurrentUserOrganization getCurrentUserOrganization() {
        return currentUserOrganization;
    }

    /**
     *
     * @param currentUserOrganization
     * The current_user_organization
     */
    public void setCurrentUserOrganization(CurrentUserOrganization currentUserOrganization) {
        this.currentUserOrganization = currentUserOrganization;
    }

    /**
     *
     * @return
     * The currentUserOrganizations
     */
    public List<Object> getCurrentUserOrganizations() {
        return currentUserOrganizations;
    }

    /**
     *
     * @param currentUserOrganizations
     * The current_user_organizations
     */
    public void setCurrentUserOrganizations(List<Object> currentUserOrganizations) {
        this.currentUserOrganizations = currentUserOrganizations;
    }

}