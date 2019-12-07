package com.lorenzorigato.movies.ui.detail.actors;

import androidx.recyclerview.widget.RecyclerView;

import com.lorenzorigato.movies.databinding.ViewActorListItemBinding;

import java.util.Objects;

public class ActorViewHolder extends RecyclerView.ViewHolder {


    // Static **************************************************************************************
    public static class Layout {
        String id;
        String name;
        String character;
        String photoUrl;

        public Layout(String id, String name, String character, String photoUrl) {
            this.id = id;
            this.name = name;
            this.character = character;
            this.photoUrl = photoUrl;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCharacter() {
            return character;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Layout layout = (Layout) o;
            return Objects.equals(id, layout.id) &&
                    Objects.equals(name, layout.name) &&
                    Objects.equals(character, layout.character) &&
                    Objects.equals(photoUrl, layout.photoUrl);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, character, photoUrl);
        }
    }


    // Private class attributes ********************************************************************
    private ViewActorListItemBinding binding;


    // Constructor *********************************************************************************
    public ActorViewHolder(ViewActorListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


    // Class methods *******************************************************************************
    public void bind(Layout layout) {
        this.binding.setLayout(layout);
    }
}
