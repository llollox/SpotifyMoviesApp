package com.lorenzorigato.movies.ui.component.movielist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lorenzorigato.movies.databinding.ViewMovieListItemBinding;
import com.lorenzorigato.movies.databinding.ViewMovieListLoadingItemBinding;

public class MovieAdapter extends PagedListAdapter<MovieViewHolder.Layout, RecyclerView.ViewHolder> {


    // Static **************************************************************************************
    public interface Listener {
        void onMovieClicked(MovieViewHolder.Layout layout);
        void onFavoriteButtonClicked(MovieViewHolder.Layout layout);
    }

    private static DiffUtil.ItemCallback<MovieViewHolder.Layout> DIFF_CALLBACK = new DiffUtil.ItemCallback<MovieViewHolder.Layout>() {
        @Override
        public boolean areItemsTheSame(@NonNull MovieViewHolder.Layout oldItem, @NonNull MovieViewHolder.Layout newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MovieViewHolder.Layout oldItem, @NonNull MovieViewHolder.Layout newItem) {
            return oldItem.equals(newItem);
        }
    };

    enum ViewType { MOVIE, LOADING }


    // Private class attributes ********************************************************************
    private Listener listener;
    private boolean isLoadingVisible = false;


    // Constructor *********************************************************************************
    public MovieAdapter(Listener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }


    // ListAdapter methods *************************************************************************
    @Override
    public int getItemViewType(int position) {
        if (this.hasExtraRow() && position == getItemCount()  - 1) {
            return ViewType.LOADING.ordinal();
        }
        else {
            return ViewType.MOVIE.ordinal();
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (viewType == ViewType.LOADING.ordinal()) {
            ViewMovieListLoadingItemBinding binding = ViewMovieListLoadingItemBinding.inflate(layoutInflater, parent, false);
            return new LoadingViewHolder(binding);
        }
        else {
            ViewMovieListItemBinding binding = ViewMovieListItemBinding.inflate(layoutInflater, parent, false);
            return new MovieViewHolder(binding, this.listener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieViewHolder) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            MovieViewHolder.Layout layout = this.getItem(position);
            if (layout != null) {
                movieViewHolder.bind(layout);
            }
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (this.hasExtraRow() ? 1 : 0);
    }

    public void showLoading(boolean isLoadingVisible) {
        this.isLoadingVisible = isLoadingVisible;
        if (isLoadingVisible) {
            this.notifyItemInserted(super.getItemCount());
        }
        else {
            this.notifyItemRemoved(super.getItemCount());
        }
    }

    public boolean hasExtraRow() {
        return this.isLoadingVisible;
    }
}