package com.lorenzorigato.movies.ui.detail;

public interface MovieDetailView {

    enum Status {
        FAVORITE_ADD_SUCCESS,
        FAVORITE_REMOVED_SUCCESS,
        FAVORITE_NOT_SET_ERROR
    }

    class State {
        String coverUrl;
        String title;
        String subtitle;
        String description;
        boolean isFavorite;
        double rating;

        public State(String coverUrl, String title, String subtitle, String description, boolean isFavorite, double rating) {
            this.coverUrl = coverUrl;
            this.title = title;
            this.subtitle = subtitle;
            this.description = description;
            this.isFavorite = isFavorite;
            this.rating = rating;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public String getDescription() {
            return description;
        }

        public boolean isFavorite() {
            return isFavorite;
        }

        public double getRating() {
            return rating;
        }
    }
}
