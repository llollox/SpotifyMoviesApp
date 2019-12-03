package com.lorenzorigato.movies.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.lorenzorigato.base.security.IAntiTampering;
import com.lorenzorigato.movies.BuildConfig;
import com.lorenzorigato.movies.R;
import com.lorenzorigato.movies.ui.util.NavigationUIExtended;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MoviesActivity extends DaggerAppCompatActivity {


    // Class attributes ****************************************************************************
    @Inject
    IAntiTampering antiTampering;

    @Inject
    IMoviesNavigator moviesNavigator;


    // Private class attributes ********************************************************************
    private DrawerLayout drawerLayout;
    private AppBarConfiguration appBarConfiguration;


    // Class methods *******************************************************************************
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!BuildConfig.DEBUG && BuildConfig.FLAVOR.equals("prod")) {
            if (this.antiTampering.isAppTampered()) {
                this.finishAndRemoveTask();
                return;
            }
        }

        this.setContentView(R.layout.movies_activity);
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
        this.drawerLayout = findViewById(R.id.drawer_layout);
        this.drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

        NavigationView navigationView = findViewById(R.id.nav_view);
        NavigationUIExtended.setupWithNavController(navigationView, getNavController(), item -> {
            if (item.getItemId() == R.id.action_settings) {
                MoviesActivity.this.moviesNavigator.goToSettings(MoviesActivity.this);
                return true;
            }
            return false;
        });
    }

    private void setupActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar));

        this.appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.search_fragment_dest, R.id.favorites_fragment_dest)
                        .setDrawerLayout(drawerLayout)
                        .build();

        NavigationUI.setupActionBarWithNavController(this, getNavController(), this.appBarConfiguration);
    }
}
