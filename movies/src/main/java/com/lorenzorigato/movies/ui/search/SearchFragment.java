package com.lorenzorigato.movies.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.lorenzorigato.movies.R;

public class SearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        view.findViewById(R.id.favorites_btn).setOnClickListener(v -> {
            getNavController().navigate(R.id.action_search_fragment_dest_to_detail_fragment_dest);
        });

        return view;
    }

    private NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }
}
