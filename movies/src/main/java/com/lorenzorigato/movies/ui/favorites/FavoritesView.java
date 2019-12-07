package com.lorenzorigato.movies.ui.favorites;

import com.lorenzorigato.movies.ui.component.movielist.MovieViewHolder;

import java.util.List;

public class FavoritesView {

    enum Status {
        FAVORITE_NOT_SET_ERROR
    }

    public static class State {

        private List<MovieViewHolder.Layout> layouts;
        private boolean isEmptyTextViewVisible = true;
        private boolean isRecyclerViewVisible = false;

        public State(List<MovieViewHolder.Layout> layouts, boolean isEmptyTextViewVisible, boolean isRecyclerViewVisible) {
            this.layouts = layouts;
            this.isEmptyTextViewVisible = isEmptyTextViewVisible;
            this.isRecyclerViewVisible = isRecyclerViewVisible;
        }

        public List<MovieViewHolder.Layout> getLayouts() { return layouts; }

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
