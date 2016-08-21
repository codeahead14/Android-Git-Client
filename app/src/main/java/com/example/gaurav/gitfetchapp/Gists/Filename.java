package com.example.gaurav.gitfetchapp.Gists;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filename implements Parcelable {

    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("raw_url")
    @Expose
    private String rawUrl;
    @SerializedName("size")
    @Expose
    private Integer size;

    /**
     *
     * @return
     * The filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     *
     * @param filename
     * The filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     *
     * @param language
     * The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     *
     * @return
     * The rawUrl
     */
    public String getRawUrl() {
        return rawUrl;
    }

    /**
     *
     * @param rawUrl
     * The raw_url
     */
    public void setRawUrl(String rawUrl) {
        this.rawUrl = rawUrl;
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

    @Override
    public int describeContents() {
        return 0;
    }

    protected Filename(Parcel in) {
        filename = in.readString();
        type = in.readString();
        language = in.readString();
        rawUrl = in.readString();
        size = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(filename);
        parcel.writeString(type);
        parcel.writeString(language);
        parcel.writeString(rawUrl);
        parcel.writeInt(size);
    }

    public static final Parcelable.Creator<Filename> CREATOR = new Parcelable.Creator<Filename>() {
        @Override
        public Filename createFromParcel(Parcel in) {
            return new Filename(in);
        }

        @Override
        public Filename[] newArray(int size) {
            return new Filename[size];
        }
    };
}