package com.chirag.valarassignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chirag Desai on 17-02-2019.
 */
public class MovieDetailModel implements Parcelable {
    @Override
    public String toString() {
        return "MovieDetailModel{" +
                "response='" + response + '\'' +
                ", website='" + website + '\'' +
                ", production='" + production + '\'' +
                ", boxoffice='" + boxoffice + '\'' +
                ", dvd='" + dvd + '\'' +
                ", type='" + type + '\'' +
                ", imdbid='" + imdbid + '\'' +
                ", imdbvotes='" + imdbvotes + '\'' +
                ", imdbrating='" + imdbrating + '\'' +
                ", metascore='" + metascore + '\'' +
                ", ratings=" + ratings +
                ", poster='" + poster + '\'' +
                ", awards='" + awards + '\'' +
                ", country='" + country + '\'' +
                ", language='" + language + '\'' +
                ", plot='" + plot + '\'' +
                ", actors='" + actors + '\'' +
                ", writer='" + writer + '\'' +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", runtime='" + runtime + '\'' +
                ", released='" + released + '\'' +
                ", rated='" + rated + '\'' +
                ", year='" + year + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Expose
    @SerializedName("Response")
    private String response;
    @Expose
    @SerializedName("Website")
    private String website;
    @Expose
    @SerializedName("Production")
    private String production;
    @Expose
    @SerializedName("BoxOffice")
    private String boxoffice;
    @Expose
    @SerializedName("DVD")
    private String dvd;
    @Expose
    @SerializedName("Type")
    private String type;
    @Expose
    @SerializedName("imdbID")
    private String imdbid;
    @Expose
    @SerializedName("imdbVotes")
    private String imdbvotes;
    @Expose
    @SerializedName("imdbRating")
    private String imdbrating;
    @Expose
    @SerializedName("Metascore")
    private String metascore;
    @Expose
    @SerializedName("Ratings")
    private List<Ratings> ratings;
    @Expose
    @SerializedName("Poster")
    private String poster;
    @Expose
    @SerializedName("Awards")
    private String awards;
    @Expose
    @SerializedName("Country")
    private String country;
    @Expose
    @SerializedName("Language")
    private String language;
    @Expose
    @SerializedName("Plot")
    private String plot;
    @Expose
    @SerializedName("Actors")
    private String actors;
    @Expose
    @SerializedName("Writer")
    private String writer;
    @Expose
    @SerializedName("Director")
    private String director;
    @Expose
    @SerializedName("Genre")
    private String genre;
    @Expose
    @SerializedName("Runtime")
    private String runtime;
    @Expose
    @SerializedName("Released")
    private String released;
    @Expose
    @SerializedName("Rated")
    private String rated;
    @Expose
    @SerializedName("Year")
    private String year;
    @Expose
    @SerializedName("Title")
    private String title;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getBoxoffice() {
        return boxoffice;
    }

    public void setBoxoffice(String boxoffice) {
        this.boxoffice = boxoffice;
    }

    public String getDvd() {
        return dvd;
    }

    public void setDvd(String dvd) {
        this.dvd = dvd;
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

    public String getImdbvotes() {
        return imdbvotes;
    }

    public void setImdbvotes(String imdbvotes) {
        this.imdbvotes = imdbvotes;
    }

    public String getImdbrating() {
        return imdbrating;
    }

    public void setImdbrating(String imdbrating) {
        this.imdbrating = imdbrating;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public List<Ratings> getRatings() {
        return ratings;
    }

    public void setRatings(List<Ratings> ratings) {
        this.ratings = ratings;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
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

    public class Ratings implements Parcelable {
        @Override
        public String toString() {
            return "Ratings{" +
                    "value='" + value + '\'' +
                    ", source='" + source + '\'' +
                    '}';
        }

        @Expose
        @SerializedName("Value")
        private String value;
        @Expose
        @SerializedName("Source")
        private String source;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.value);
            dest.writeString(this.source);
        }

        public Ratings() {
        }

        protected Ratings(Parcel in) {
            this.value = in.readString();
            this.source = in.readString();
        }

        public final Creator<Ratings> CREATOR = new Creator<Ratings>() {
            @Override
            public Ratings createFromParcel(Parcel source) {
                return new Ratings(source);
            }

            @Override
            public Ratings[] newArray(int size) {
                return new Ratings[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.response);
        dest.writeString(this.website);
        dest.writeString(this.production);
        dest.writeString(this.boxoffice);
        dest.writeString(this.dvd);
        dest.writeString(this.type);
        dest.writeString(this.imdbid);
        dest.writeString(this.imdbvotes);
        dest.writeString(this.imdbrating);
        dest.writeString(this.metascore);
        dest.writeList(this.ratings);
        dest.writeString(this.poster);
        dest.writeString(this.awards);
        dest.writeString(this.country);
        dest.writeString(this.language);
        dest.writeString(this.plot);
        dest.writeString(this.actors);
        dest.writeString(this.writer);
        dest.writeString(this.director);
        dest.writeString(this.genre);
        dest.writeString(this.runtime);
        dest.writeString(this.released);
        dest.writeString(this.rated);
        dest.writeString(this.year);
        dest.writeString(this.title);
    }

    public MovieDetailModel() {
    }

    protected MovieDetailModel(Parcel in) {
        this.response = in.readString();
        this.website = in.readString();
        this.production = in.readString();
        this.boxoffice = in.readString();
        this.dvd = in.readString();
        this.type = in.readString();
        this.imdbid = in.readString();
        this.imdbvotes = in.readString();
        this.imdbrating = in.readString();
        this.metascore = in.readString();
        this.ratings = new ArrayList<Ratings>();
        in.readList(this.ratings, Ratings.class.getClassLoader());
        this.poster = in.readString();
        this.awards = in.readString();
        this.country = in.readString();
        this.language = in.readString();
        this.plot = in.readString();
        this.actors = in.readString();
        this.writer = in.readString();
        this.director = in.readString();
        this.genre = in.readString();
        this.runtime = in.readString();
        this.released = in.readString();
        this.rated = in.readString();
        this.year = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<MovieDetailModel> CREATOR = new Parcelable.Creator<MovieDetailModel>() {
        @Override
        public MovieDetailModel createFromParcel(Parcel source) {
            return new MovieDetailModel(source);
        }

        @Override
        public MovieDetailModel[] newArray(int size) {
            return new MovieDetailModel[size];
        }
    };
}
