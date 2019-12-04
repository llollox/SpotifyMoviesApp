package com.lorenzorigato.movies.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.lorenzorigato.base.security.ISecurityManager;
import com.lorenzorigato.movies.R;
import com.lorenzorigato.movies.databinding.MoviesActivityBinding;
import com.lorenzorigato.movies.ui.util.NavigationUIExtended;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MoviesActivity extends DaggerAppCompatActivity {


    // Class attributes ****************************************************************************
    @Inject
    ISecurityManager securityManager;

    @Inject
    IMoviesNavigator moviesNavigator;


    // Private class attributes ********************************************************************
    private AppBarConfiguration appBarConfiguration;
    private MoviesActivityBinding binding;


    // Class methods *******************************************************************************
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!this.securityManager.canAppExecute()) {
            this.finishAndRemoveTask();
            return;
        }

        this.binding = DataBindingUtil.setContentView(this, R.layout.movies_activity);
        this.setupNavigationDrawer();
        this.setupActionBar();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(getNavController(), this.appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    // Private class methods ***********************************************************************
    private NavController getNavController() {
        return Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    private void setupNavigationDrawer() {
        this.binding.drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

        NavigationUIExtended.setupWithNavController(this.binding.navView, getNavController(), item -> {
            if (item.getItemId() == R.id.action_about) {
                MoviesActivity.this.moviesNavigator.goToAbout(MoviesActivity.this);
                return true;
            }
            return false;
        });
    }

    private void setupActionBar() {
        setSupportActionBar(this.binding.toolbar);

        this.appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.search_fragment_dest, R.id.favorites_fragment_dest)
                        .setDrawerLayout(this.binding.drawerLayout)
                        .build();

        NavigationUI.setupActionBarWithNavController(this, getNavController(), this.appBarConfiguration);
    }
}
