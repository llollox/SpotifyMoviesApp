package com.lorenzorigato.movies.ui.component;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.lorenzorigato.base.components.image.transformation.GradientTransformation;
import com.lorenzorigato.movies.R;
import com.squareup.picasso.Picasso;

public class DataBindingAdapter {

    @BindingAdapter("url")
    public static void loadImage(ImageView imageView, String url) {
        Picasso.get().load(url).placeholder(R.drawable.loading).into(imageView);
    }

    @BindingAdapter(value = "urlWithBottomGradient")
    public static void loadImageWithBottomGradient(ImageView imageView, String url) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.loading)
                .transform(new GradientTransformation())
                .into(imageView);
    }
}
