package com.lorenzorigato.movies.ui.component.movielist;

import androidx.recyclerview.widget.RecyclerView;

import com.lorenzorigato.movies.R;
import com.lorenzorigato.movies.databinding.ViewMovieListItemBinding;

import java.util.Objects;

public class MovieViewHolder extends RecyclerView.ViewHolder {


    // Static **************************************************************************************
    public static class Layout {
        private int id;
        private String title;
        private String posterUrl;
        private boolean isFavorite;
        private boolean isTop;

        public Layout(int id, String title, String posterUrl, boolean isTop, boolean isFavorite) {
            this.id = id;
            this.title = title;
            this.posterUrl = posterUrl;
            this.isFavorite = isFavorite;
            this.isTop = isTop;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() { return title; }

        public String getPosterUrl() {
            return posterUrl;
        }

        public boolean isFavorite() {
            return isFavorite;
        }

        public boolean isTop() {
            return isTop;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Layout layout = (Layout) o;
            return id == layout.id &&
                    isFavorite == layout.isFavorite &&
                    isTop == layout.isTop &&
                    Objects.equals(title, layout.title) &&
                    Objects.equals(posterUrl, layout.posterUrl);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, title, posterUrl, isFavorite, isTop);
        }
    }

    // Private class attributes ********************************************************************
    private ViewMovieListItemBinding binding;
    private MovieAdapter.Listener listener;


    // Constructor *********************************************************************************
    public MovieViewHolder(ViewMovieListItemBinding binding, MovieAdapter.Listener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }


    // Class methods *******************************************************************************
    public void bind(Layout layout) {
        this.binding.setLayout(layout);

        if (layout.isFavorite) {
            this.binding.imageViewMovieListItemFavorite.setImageResource(R.drawable.ic_favorite_remove);
        }
        else {
            this.binding.imageViewMovieListItemFavorite.setImageResource(R.drawable.ic_favorite_add);
        }

        this.binding.imageViewMovieListItemFavorite.setOnClickListener(v -> {
            if (this.listener != null) {
                this.listener.onFavoriteButtonClicked(layout);
            }
        });

        this.binding.getRoot().setOnClickListener(v -> {
            if (this.listener != null) {
                this.listener.onMovieClicked(layout);
            }
        });
    }
}