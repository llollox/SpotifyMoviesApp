package com.lorenzorigato.movies_sample.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lorenzorigato.movies.ui.detail.MovieDetailActivity;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.startDetailActivity();
    }

    private void startDetailActivity() {
        Intent intent = MovieDetailActivity.getCallingIntent(this, 682);
        startActivity(intent);
        finish();
    }
}
