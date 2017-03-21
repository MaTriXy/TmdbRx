package com.tmdb.android.movies;

import android.app.Fragment;
import com.tmdb.android.model.Movie;
import java.util.List;

/**
 * Created by ronelg on 3/21/17.
 */

public class MoviesFragment extends Fragment implements MoviesContract.View {

    private MoviesContract.Presenter mPresenter;

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showMovies(List<Movie> movies) {

    }

    @Override
    public void showLoadingMoviesError() {

    }

    @Override
    public void showNoMovies() {

    }
}
