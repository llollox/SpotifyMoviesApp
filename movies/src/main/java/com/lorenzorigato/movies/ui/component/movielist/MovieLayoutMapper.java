package com.lorenzorigato.movies.ui.component.movielist;

import com.lorenzorigato.base.model.entity.Movie;

public class MovieLayoutMapper {

    public MovieViewHolder.Layout mapToLayout(Movie movie, boolean isTop, boolean isFavorite) {
        return new MovieViewHolder.Layout(
                movie.getId(),
                movie.getPosterFullPath(),
                isTop,
                isFavorite);
    }
}
