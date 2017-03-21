package com.tmdb.android.movies;

import com.tmdb.android.BasePresenter;
import com.tmdb.android.BaseView;
import com.tmdb.android.model.Movie;
import java.util.List;

/**
 * Created by ronelg on 3/21/17.
 */

public class MoviesContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMovies(List<Movie> movies);

        void showLoadingMoviesError();

        void showNoMovies();

    }

    interface Presenter extends BasePresenter {

        void loadMovies(boolean forceUpdate);

    }
}
