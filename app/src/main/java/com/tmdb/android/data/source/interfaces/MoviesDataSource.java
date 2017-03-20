package com.tmdb.android.data.source.interfaces;

import com.tmdb.android.model.Movie;
import com.tmdb.android.model.Trailer;
import java.util.List;
import rx.Observable;

/**
 * Created by ronel on 20/03/2017.
 */

public interface MoviesDataSource {

     Observable<List<Movie>> getPopularMovies();

     Observable<List<Trailer>> getMovieTrailers(long movieId);

     void saveMovie(Movie movie);

     void saveTrailers(long movieId, List<Trailer> trailers);


}
