package com.lorenzorigato.movies.ui.detail.actors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.lorenzorigato.movies.databinding.ViewActorListItemBinding;

public class ActorAdapter extends ListAdapter<ActorViewHolder.Layout, ActorViewHolder> {

    private static DiffUtil.ItemCallback<ActorViewHolder.Layout> DIFF_CALLBACK = new DiffUtil.ItemCallback<ActorViewHolder.Layout>() {
        @Override
        public boolean areItemsTheSame(@NonNull ActorViewHolder.Layout oldItem, @NonNull ActorViewHolder.Layout newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ActorViewHolder.Layout oldItem, @NonNull ActorViewHolder.Layout newItem) {
            return oldItem.equals(newItem);
        }
    };


    // Constructor *********************************************************************************
    public ActorAdapter() {
        super(DIFF_CALLBACK);
    }


    // ListAdapter methods *************************************************************************
    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewActorListItemBinding binding = ViewActorListItemBinding.inflate(layoutInflater, parent, false);
        return new ActorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        ActorViewHolder.Layout layout = this.getItem(position);
        if (layout != null) {
            holder.bind(layout);
        }
    }
}