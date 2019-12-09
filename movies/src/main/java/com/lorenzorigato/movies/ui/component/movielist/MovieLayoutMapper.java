package com.lorenzorigato.movies.ui.component.movielist;

import com.lorenzorigato.base.model.entity.Movie;

public class MovieLayoutMapper {

    public MovieViewHolder.Layout mapToLayout(Movie movie) {
        if (movie == null) {
            return new MovieViewHolder.Layout(
                    0,
                    "loading.jpg",
                    false,
                    false);
        }
        else {
            return new MovieViewHolder.Layout(
                    movie.getId(),
                    movie.getPosterFullPath(),
                    movie.getRating() > 7.0,
                    movie.isFavorite());
        }
    }
}
