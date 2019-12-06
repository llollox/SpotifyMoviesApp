package com.lorenzorigato.movies.ui.detail;

public interface MovieDetailView {

    class Layout {
        String coverUrl;
        String subtitle;
        String description;
        double rating;

        public Layout(String coverUrl, String subtitle, String description, double rating) {
            this.coverUrl = coverUrl;
            this.subtitle = subtitle;
            this.description = description;
            this.rating = rating;
        }

        public String getCoverUrl() {
            return coverUrl;
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
