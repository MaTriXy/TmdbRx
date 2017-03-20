package com.tmdb.android.data.api;

import com.tmdb.android.io.MoviesResponse;
import com.tmdb.android.io.TrailersResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ronel on 20/03/2017.
 */

public interface TmdbService {

    String ENDPOINT = "https://api.themoviedb.org/3/";

    @GET("movie/popular")
    Observable<MoviesResponse> getPopularMovies(@Query("page") Integer page);

    @GET("movie/{movie_id}/videos")
    Observable<TrailersResponse> getMovieTrailers(@Path("movie_id") long movieId);
}
