package com.lorenzorigato.movies.ui.search;

public interface SearchView {
    enum Status {
        GENRES_NOT_LOADED_ERROR,
        INVALID_GENRE_ERROR,
        FAVORITE_NOT_SET_ERROR,
        FAVORITE_ADD_SUCCESS,
        FAVORITE_REMOVED_SUCCESS
    }

    class State {
        private boolean isLoadingVisible = false;

        public boolean isLoadingVisible() {
            return isLoadingVisible;
        }

        public void setLoadingVisible(boolean loadingVisible) {
            isLoadingVisible = loadingVisible;
        }
    }
}
