package com.lorenzorigato.movies.ui.component.movielist;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;

public class MovieBoundaryCallback extends PagedList.BoundaryCallback<MovieViewHolder.Layout> {

    private Genre genre;
    private IMovieRepository movieRepository;
    private boolean isLastPage = false;

    public MovieBoundaryCallback(Genre genre, IMovieRepository movieRepository) {
        this.genre = genre;
        this.movieRepository = movieRepository;
    }

    @Override
    public void onZeroItemsLoaded() {
        if (!this.isLastPage) {
            this.movieRepository.updateByGenre(this.genre, -1, (error, movies, isLastPage) -> {
                this.isLastPage = isLastPage;
            });
        }
    }

    @Override
    public void onItemAtEndLoaded(@NonNull MovieViewHolder.Layout itemAtEnd) {
        if (!this.isLastPage) {
            this.movieRepository.updateByGenre(this.genre, itemAtEnd.getId(), (error, movies, isLastPage) -> {
                this.isLastPage = isLastPage;
            });
        }
    }
}
