package com.example.gaurav.gitfetchapp.Repositories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import android.os.Parcel;
import android.os.Parcelable;

public class Permissions implements Parcelable {

    @SerializedName("admin")
    @Expose
    private Boolean admin;
    @SerializedName("push")
    @Expose
    private Boolean push;
    @SerializedName("pull")
    @Expose
    private Boolean pull;

    /**
     *
     * @return
     * The admin
     */
    public Boolean getAdmin() {
        return admin;
    }

    /**
     *
     * @param admin
     * The admin
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    /**
     *
     * @return
     * The push
     */
    public Boolean getPush() {
        return push;
    }

    /**
     *
     * @param push
     * The push
     */
    public void setPush(Boolean push) {
        this.push = push;
    }

    /**
     *
     * @return
     * The pull
     */
    public Boolean getPull() {
        return pull;
    }

    /**
     *
     * @param pull
     * The pull
     */
    public void setPull(Boolean pull) {
        this.pull = pull;
    }


    protected Permissions(Parcel in) {
        byte adminVal = in.readByte();
        admin = adminVal == 0x02 ? null : adminVal != 0x00;
        byte pushVal = in.readByte();
        push = pushVal == 0x02 ? null : pushVal != 0x00;
        byte pullVal = in.readByte();
        pull = pullVal == 0x02 ? null : pullVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (admin == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (admin ? 0x01 : 0x00));
        }
        if (push == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (push ? 0x01 : 0x00));
        }
        if (pull == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (pull ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Permissions> CREATOR = new Parcelable.Creator<Permissions>() {
        @Override
        public Permissions createFromParcel(Parcel in) {
            return new Permissions(in);
        }

        @Override
        public Permissions[] newArray(int size) {
            return new Permissions[size];
        }
    };
}