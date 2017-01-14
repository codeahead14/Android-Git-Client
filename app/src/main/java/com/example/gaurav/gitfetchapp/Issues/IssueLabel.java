package com.example.gaurav.gitfetchapp.Issues;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueLabel implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("default")
    @Expose
    private Boolean _default;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getDefault() {
        return _default;
    }

    public void setDefault(Boolean _default) {
        this._default = _default;
    }


    protected IssueLabel(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        url = in.readString();
        name = in.readString();
        color = in.readString();
        byte _defaultVal = in.readByte();
        _default = _defaultVal == 0x02 ? null : _defaultVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(color);
        if (_default == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (_default ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<IssueLabel> CREATOR = new Parcelable.Creator<IssueLabel>() {
        @Override
        public IssueLabel createFromParcel(Parcel in) {
            return new IssueLabel(in);
        }

        @Override
        public IssueLabel[] newArray(int size) {
            return new IssueLabel[size];
        }
    };
}