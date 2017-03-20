package com.tmdb.android.data.source.remote;

import com.tmdb.android.data.api.TmdbApi;
import com.tmdb.android.data.api.TmdbService;
import com.tmdb.android.data.source.interfaces.MoviesDataSource;
import com.tmdb.android.model.Movie;
import com.tmdb.android.model.Trailer;
import java.util.List;
import rx.Observable;

/**
 * Created by ronel on 20/03/2017.
 */
public class MoviesRemoteDataSource implements MoviesDataSource {

    private static MoviesRemoteDataSource mInstance;

    private TmdbService tmdbService;

    public int page = 1;
    public int totalPages;

    public static MoviesRemoteDataSource getInstance() {
        if(mInstance == null) {
            mInstance = new MoviesRemoteDataSource();
        }

        return mInstance;
    }

    private MoviesRemoteDataSource() {
         tmdbService = TmdbApi.getInstanse().getTmdbService();
    }

    @Override
    public Observable<List<Movie>> getPopularMovies() {
        return tmdbService.getPopularMovies(page)
                .doOnNext(moviesResponse -> {
                    page = moviesResponse.page;
                    totalPages = moviesResponse.totalPages;
                })
               .concatMap(moviesResponse -> Observable.from(moviesResponse.movies).toList());
    }

    public Observable<List<Trailer>> getMovieTrailers(long movieId) {
        return tmdbService.getMovieTrailers(movieId)
                .flatMap(trailersResponse -> Observable.from(trailersResponse.trailers).toList());
    }

    @Override
    public void saveMovie(Movie movie) {
        //no-op
    }

    @Override
    public void saveTrailers(long movieId, List<Trailer> trailers) {
        //no-op
    }
}
