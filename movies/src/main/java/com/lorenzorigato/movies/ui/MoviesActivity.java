package com.lorenzorigato.movies.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MoviesActivity extends DaggerAppCompatActivity {

    @Inject
    IAntiTampering antiTampering;

    @Inject
    IMoviesNavigator moviesNavigator;


    // Private class attributes ********************************************************************
    private DrawerLayout drawerLayout;
    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!BuildConfig.DEBUG && BuildConfig.FLAVOR.equals("prod")) {
            if (this.antiTampering.isAppTampered()) {
                this.finishAndRemoveTask();
                return;
            }
        }

        setContentView(R.layout.movies_activity);
        setupNavigationDrawer();
        setSupportActionBar(findViewById(R.id.toolbar));

        this.appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.search_fragment_dest, R.id.favorites_fragment_dest)
                        .setDrawerLayout(drawerLayout)
                        .build();

        NavigationUI.setupActionBarWithNavController(this, getNavController(), this.appBarConfiguration);

        NavigationView navigationView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navigationView, getNavController());
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(getNavController(), this.appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.info_menu) {
            this.moviesNavigator.goToInformation(this);
        }
        return super.onOptionsItemSelected(item);
    }

    // Private class methods ***********************************************************************
    private void setupNavigationDrawer() {
        this.drawerLayout = findViewById(R.id.drawer_layout);
        this.drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
    }

    private NavController getNavController() {
        return Navigation.findNavController(this, R.id.nav_host_fragment);
    }
}
