package com.tmdb.android.data.source;

import com.tmdb.android.data.source.interfaces.MoviesDataSource;
import com.tmdb.android.data.source.local.MoviesLocalDataSource;
import com.tmdb.android.data.source.remote.MoviesRemoteDataSource;
import com.tmdb.android.model.Movie;
import com.tmdb.android.model.Trailer;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ronel on 20/03/2017.
 */
public class TmdbRepository implements MoviesDataSource {

    private static TmdbRepository mInstance;

    private MoviesDataSource remoteDataSource;

    private MoviesDataSource localDataSource;

    private Map<Long, Movie> cachedMovies;

    private Map<Long, List<Trailer>> cachedTrailers;

    private boolean cacheMoviesIsDirty = false;

    private boolean cachedTrailersIsDirty = false;

    public static TmdbRepository getInstace() {
        if(mInstance == null) {
            mInstance = new TmdbRepository();
        }
        return mInstance;
    }

    private TmdbRepository() {
        remoteDataSource = MoviesRemoteDataSource.getInstance();
        localDataSource = MoviesLocalDataSource.getInstance();
    }

    @Override
    public Observable<List<Movie>> getPopularMovies() {
        if(cachedMovies != null && !cacheMoviesIsDirty) {
            return Observable.from(cachedMovies.values()).toList();
        } else if(cachedMovies == null) {
            cachedMovies = new LinkedHashMap<>();
        }

        Observable<List<Movie>> remoteMovies = getAndSaveRemotePopularMovies();

        if (cacheMoviesIsDirty) {
            return remoteMovies;
        } else {
            // Query the local storage if available. If not, query the network.
            Observable<List<Movie>> localMovies = getAndCacheLocalPopularMovies();
            return Observable.concat(localMovies, remoteMovies)
                    .filter(movies -> !movies.isEmpty())
                    .first();
        }
    }

    private Observable<List<Movie>> getAndCacheLocalPopularMovies() {
        return localDataSource.getPopularMovies()
                .flatMap(movies -> Observable.from(movies)
                        .doOnNext(movie -> cachedMovies.put(movie.getMovieId(), movie))
                        .toList());
    }

    private Observable<List<Movie>> getAndSaveRemotePopularMovies() {
        return remoteDataSource
                .getPopularMovies()
                .flatMap(movies -> Observable.from(movies).doOnNext(movie -> {
                    localDataSource.saveMovie(movie);
                    cachedMovies.put(movie.getMovieId(), movie);
                }).toList())
                .doOnCompleted(() -> cacheMoviesIsDirty = false);
    }


    @Override
    public Observable<List<Trailer>> getMovieTrailers(long movieId) {
        if(cachedTrailers != null && !cachedTrailersIsDirty) {
            return Observable.just(cachedTrailers)
                    .map(trailersMap -> trailersMap.get(movieId));
        } else if(cachedTrailers == null) {
            cachedTrailers = new LinkedHashMap<>();
        }

        Observable<List<Trailer>> remoteTrailers = getAndCacheLocalMovieTrailers(movieId);

        if (cachedTrailersIsDirty) {
            return remoteTrailers;
        } else {
            // Query the local storage if available. If not, query the network.
            Observable<List<Trailer>> localTrailers = getAndSaveRemoteMovieTrailers(movieId);
            return Observable.concat(localTrailers, remoteTrailers)
                    .filter(trailers -> !trailers.isEmpty())
                    .first();
        }
    }

    private Observable<List<Trailer>> getAndCacheLocalMovieTrailers(long movieId) {
        return localDataSource.getMovieTrailers(movieId)
                        .doOnNext(trailers -> cachedTrailers.put(movieId, trailers));
    }

    private Observable<List<Trailer>> getAndSaveRemoteMovieTrailers(long movieId) {
        return remoteDataSource.getMovieTrailers(movieId)
                .doOnNext(trailers -> {
                    localDataSource.saveTrailers(movieId, trailers);
                    cachedTrailers.put(movieId, trailers);
                })
                .doOnCompleted(() -> cachedTrailersIsDirty = false);
    }


    @Override
    public void saveMovie(Movie movie) {
        localDataSource.saveMovie(movie);
        remoteDataSource.saveMovie(movie);

        // Do in memory cache update to keep the app UI up to date
        if (cachedMovies == null) {
            cachedMovies = new LinkedHashMap<>();
        }
        cachedMovies.put(movie.getMovieId(), movie);
    }

    @Override
    public void saveTrailers(long movieId, List<Trailer> trailers) {
        localDataSource.saveTrailers(movieId, trailers);
        remoteDataSource.saveTrailers(movieId, trailers);

        // Do in memory cache update to keep the app UI up to date
        if(cachedTrailers == null) {
            cachedTrailers = new LinkedHashMap<>();
        }

        cachedTrailers.put(movieId, trailers);
    }
}
