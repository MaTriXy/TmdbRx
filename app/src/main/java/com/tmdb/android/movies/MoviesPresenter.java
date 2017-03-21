package com.tmdb.android.movies;

import com.tmdb.android.data.source.TmdbRepository;

/**
 * Created by ronelg on 3/21/17.
 */
public class MoviesPresenter implements MoviesContract.Presenter{

    private TmdbRepository repository;
    private MoviesContract.View moviewView;

    public MoviesPresenter(TmdbRepository repository,
        MoviesContract.View moviewView) {
        this.repository = repository;
        this.moviewView = moviewView;

        moviewView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadMovies(boolean forceUpdate) {

    }
}
