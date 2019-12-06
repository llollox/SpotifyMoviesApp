package com.lorenzorigato.movies.ui.search;

public interface SearchView {
    enum Status {
        GENRES_NOT_LOADED_ERROR,
        INVALID_GENRE_ERROR,
        MOVIES_NOT_LOADED_ERROR,
        FAVORITE_NOT_SET_ERROR
    }

    class State {
        private boolean isLoadingVisible = false;
        private boolean isEmptyTextViewVisible = false;
        private boolean isRecyclerViewVisible = false;

        public boolean isLoadingVisible() {
            return isLoadingVisible;
        }

        public void setLoadingVisible(boolean loadingVisible) {
            isLoadingVisible = loadingVisible;
        }

        public boolean isEmptyTextViewVisible() {
            return isEmptyTextViewVisible;
        }

        public void setEmptyTextViewVisible(boolean emptyTextViewVisible) {
            isEmptyTextViewVisible = emptyTextViewVisible;
        }

        public boolean isRecyclerViewVisible() {
            return isRecyclerViewVisible;
        }

        public void setRecyclerViewVisible(boolean recyclerViewVisible) {
            isRecyclerViewVisible = recyclerViewVisible;
        }
    }
}
