package com.lorenzorigato.movies.ui.component.movielist;

import com.lorenzorigato.base.model.entity.Movie;

public class MovieLayoutMapper {

    public MovieViewHolder.Layout mapToLayout(Movie movie, boolean isTop) {
        return new MovieViewHolder.Layout(
                movie.getId(),
                movie.getPosterFullPath(),
                isTop,
                movie.isFavorite());
    }
}
