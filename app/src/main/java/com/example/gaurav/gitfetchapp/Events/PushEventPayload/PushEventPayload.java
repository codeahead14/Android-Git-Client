package com.example.gaurav.gitfetchapp.Events.PushEventPayload;

import java.util.ArrayList;
import java.util.List;

import com.example.gaurav.gitfetchapp.Events.Payload;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Converter;

public class PushEventPayload extends Payload{

    @Override
    public void printName(){
        String name = PushEventPayload.class.getName();
    }

    @SerializedName("push_id")
    @Expose
    private Integer pushId;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("distinct_size")
    @Expose
    private Integer distinctSize;
    @SerializedName("ref")
    @Expose
    private String ref;
    @SerializedName("head")
    @Expose
    private String head;
    @SerializedName("before")
    @Expose
    private String before;
    @SerializedName("commits")
    @Expose
    private List<Commit> commits = new ArrayList<Commit>();

    /**
     *
     * @return
     * The pushId
     */
    public Integer getPushId() {
        return pushId;
    }

    /**
     *
     * @param pushId
     * The push_id
     */
    public void setPushId(Integer pushId) {
        this.pushId = pushId;
    }

    /**
     *
     * @return
     * The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The distinctSize
     */
    public Integer getDistinctSize() {
        return distinctSize;
    }

    /**
     *
     * @param distinctSize
     * The distinct_size
     */
    public void setDistinctSize(Integer distinctSize) {
        this.distinctSize = distinctSize;
    }

    /**
     *
     * @return
     * The ref
     */
    public String getRef() {
        return ref;
    }

    /**
     *
     * @param ref
     * The ref
     */
    public void setRef(String ref) {
        this.ref = ref;
    }

    /**
     *
     * @return
     * The head
     */
    public String getHead() {
        return head;
    }

    /**
     *
     * @param head
     * The head
     */
    public void setHead(String head) {
        this.head = head;
    }

    /**
     *
     * @return
     * The before
     */
    public String getBefore() {
        return before;
    }

    /**
     *
     * @param before
     * The before
     */
    public void setBefore(String before) {
        this.before = before;
    }

    /**
     *
     * @return
     * The commits
     */
    public List<Commit> getCommits() {
        return commits;
    }

    /**
     *
     * @param commits
     * The commits
     */
    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

}