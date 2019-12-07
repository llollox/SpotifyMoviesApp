package com.lorenzorigato.movies.ui.detail;

public interface MovieDetailView {

    enum Status {
        FAVORITE_NOT_SET_ERROR
    }

    class Layout {
        String coverUrl;
        String title;
        String subtitle;
        String description;
        double rating;

        public Layout(String coverUrl, String title, String subtitle, String description, double rating) {
            this.coverUrl = coverUrl;
            this.title = title;
            this.subtitle = subtitle;
            this.description = description;
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

        public double getRating() {
            return rating;
        }
    }
}
