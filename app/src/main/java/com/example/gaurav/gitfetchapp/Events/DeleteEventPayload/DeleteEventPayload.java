package com.example.gaurav.gitfetchapp.Events.DeleteEventPayload;

import com.example.gaurav.gitfetchapp.Events.Payload;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteEventPayload extends Payload{

    @SerializedName("ref")
    @Expose
    private String ref;
    @SerializedName("ref_type")
    @Expose
    private String refType;
    @SerializedName("pusher_type")
    @Expose
    private String pusherType;

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
     * The refType
     */
    public String getRefType() {
        return refType;
    }

    /**
     *
     * @param refType
     * The ref_type
     */
    public void setRefType(String refType) {
        this.refType = refType;
    }

    /**
     *
     * @return
     * The pusherType
     */
    public String getPusherType() {
        return pusherType;
    }

    /**
     *
     * @param pusherType
     * The pusher_type
     */
    public void setPusherType(String pusherType) {
        this.pusherType = pusherType;
    }

}