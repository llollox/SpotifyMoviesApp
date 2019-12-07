package com.lorenzorigato.movies.ui.component.movielist;

import com.lorenzorigato.base.model.entity.Movie;

public class MovieLayoutMapper {

    public MovieViewHolder.Layout mapToLayout(Movie movie) {
        return new MovieViewHolder.Layout(
                movie.getId(),
                movie.getPosterFullPath(),
                movie.getRating() > 7.0,
                movie.isFavorite());
    }
}
