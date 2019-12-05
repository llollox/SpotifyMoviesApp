package com.lorenzorigato.movies.ui.component.movielist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.lorenzorigato.movies.databinding.ViewMovieListItemBinding;

public class MovieAdapter extends ListAdapter<MovieViewHolder.Layout, MovieViewHolder> {


    // Static **************************************************************************************
    public interface Listener {
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


    // Private class attributes ********************************************************************
    private Listener listener;


    // Constructor *********************************************************************************
    public MovieAdapter(Listener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }


    // ListAdapter methods *************************************************************************
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewMovieListItemBinding binding = ViewMovieListItemBinding.inflate(layoutInflater, parent, false);
        return new MovieViewHolder(binding, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieViewHolder.Layout layout = this.getItem(position);
        if (layout != null) {
            holder.bind(layout);
        }
    }
}