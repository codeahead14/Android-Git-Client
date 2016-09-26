package com.example.gaurav.gitfetchapp.Events.MemberEventsPayload;

import com.example.gaurav.gitfetchapp.Events.Payload;
import com.example.gaurav.gitfetchapp.Repositories.Owner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberEventPayload extends Payload {

    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("member")
    @Expose
    private Owner member;

    /**
     *
     * @return
     * The action
     */
    public String getAction() {
        return action;
    }

    /**
     *
     * @param action
     * The action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     *
     * @return
     * The member
     */
    public Owner getMember() {
        return member;
    }

    /**
     *
     * @param member
     * The member
     */
    public void setMember(Owner member) {
        this.member = member;
    }

}