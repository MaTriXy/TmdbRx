package com.tmdb.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Builder;

@AllArgsConstructor(suppressConstructorProperties = true)
@Getter
@Builder
public class Movie {
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("id")
    @Expose
    public long movieId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("vote_average")
    @Expose
    public double voteAverage;
}
