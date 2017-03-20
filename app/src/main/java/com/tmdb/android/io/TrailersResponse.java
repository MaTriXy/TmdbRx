package com.tmdb.android.io;

import com.google.gson.annotations.SerializedName;
import com.tmdb.android.model.Trailer;
import java.util.List;

public class TrailersResponse {
    @SerializedName("id")
    public int movieId;
    @SerializedName("results")
    public List<Trailer> trailers;
}
