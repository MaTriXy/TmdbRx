package com.tmdb.android.io;

import com.google.gson.annotations.SerializedName;
import com.tmdb.android.model.Movie;
import java.util.List;

public class MoviesResponse {

    @SerializedName("page")
    public int page;
    @SerializedName("total_results")
    public int totalResults;
    @SerializedName("total_pages")
    public int totalPages;
    @SerializedName("results")
    public List<Movie> movies;
}
