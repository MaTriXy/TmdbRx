package com.tmdb.android.data.source.local;

import com.tmdb.android.data.source.interfaces.MoviesDataSource;
import com.tmdb.android.model.Movie;
import com.tmdb.android.model.Trailer;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import rx.Observable;

/**
 * Created by ronel on 20/03/2017.
 */
public class MoviesLocalDataSource implements MoviesDataSource {

    private static MoviesLocalDataSource mInstance;

    private Map<Long,Movie> cachedMovies;

    private Map<Long, List<Trailer>> cachedTrailers;

    public static MoviesLocalDataSource getInstance() {
        if(mInstance == null) {
            mInstance = new MoviesLocalDataSource();
        }

        return mInstance;
    }

    private MoviesLocalDataSource() {
        cachedMovies = new LinkedHashMap<>();
        cachedTrailers = new LinkedHashMap<>();
    }

    @Override
    public Observable<List<Movie>> getPopularMovies() {
        return Observable.from(cachedMovies.values()).toList();
    }

    @Override
    public Observable<List<Trailer>> getMovieTrailers(long movieId) {
        return Observable.just(cachedTrailers)
                .map(trailersMap -> trailersMap.get(movieId));
    }

    @Override
    public void saveMovie(Movie movie) {
        if(cachedMovies == null) {
            cachedMovies = new LinkedHashMap<>();
        }

        cachedMovies.put(movie.getMovieId(), movie);
    }

    @Override
    public void saveTrailers(long movieId, List<Trailer> trailers) {
        if(cachedTrailers == null) {
            cachedTrailers = new LinkedHashMap<>();
        }

        cachedTrailers.put(movieId, trailers);
    }
}
