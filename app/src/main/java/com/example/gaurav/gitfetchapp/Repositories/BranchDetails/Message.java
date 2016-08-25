package com.example.gaurav.gitfetchapp.Repositories.BranchDetails;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GAURAV on 07-08-2016.
 */
public class Message implements Parcelable {

    protected Message(Parcel in) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}