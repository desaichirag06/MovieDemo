package com.chirag.valarassignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chirag Desai on 16-02-2019.
 */
public class SearchListModel implements Parcelable {

    @Override
    public String toString() {
        return "SearchListModel{" +
                "poster='" + poster + '\'' +
                ", type='" + type + '\'' +
                ", imdbid='" + imdbid + '\'' +
                ", year='" + year + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Expose
    @SerializedName("Poster")
    private String poster;
    @Expose
    @SerializedName("Type")
    private String type;
    @Expose
    @SerializedName("imdbID")
    private String imdbid;
    @Expose
    @SerializedName("Year")
    private String year;
    @Expose
    @SerializedName("Title")
    private String title;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImdbid() {
        return imdbid;
    }

    public void setImdbid(String imdbid) {
        this.imdbid = imdbid;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster);
        dest.writeString(this.type);
        dest.writeString(this.imdbid);
        dest.writeString(this.year);
        dest.writeString(this.title);
    }

    public SearchListModel() {
    }

    protected SearchListModel(Parcel in) {
        this.poster = in.readString();
        this.type = in.readString();
        this.imdbid = in.readString();
        this.year = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<SearchListModel> CREATOR = new Parcelable.Creator<SearchListModel>() {
        @Override
        public SearchListModel createFromParcel(Parcel source) {
            return new SearchListModel(source);
        }

        @Override
        public SearchListModel[] newArray(int size) {
            return new SearchListModel[size];
        }
    };
}
