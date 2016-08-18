package com.example.gaurav.gitfetchapp.Events.CreateCommentPayload;

import com.example.gaurav.gitfetchapp.Events.Payload;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateCommentPayload extends Payload{

    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("comment")
    @Expose
    private Comment comment;

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
     * The comment
     */
    public Comment getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     * The comment
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

}