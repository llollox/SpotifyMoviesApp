package com.lorenzorigato.movies.ui.util;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import com.google.android.material.navigation.NavigationView;

import java.lang.ref.WeakReference;

import static androidx.navigation.ui.NavigationUI.onNavDestinationSelected;

public class NavigationUIExtended {

    public interface OnNavigationItemSelectedListener {
        boolean onNavigationItemSelected(@NonNull MenuItem item);
    }

    public static void setupWithNavController(@NonNull final NavigationView navigationView,
                                              @NonNull final NavController navController,
                                              final OnNavigationItemSelectedListener listener) {

        navigationView.setNavigationItemSelectedListener(
                item -> {
                    if (listener != null && listener.onNavigationItemSelected(item)) {
                        return false;
                    }
                    else {
                        boolean handled = onNavDestinationSelected(item, navController);
                        if (handled) {
                            ViewParent parent = navigationView.getParent();
                            if (parent instanceof DrawerLayout) {
                                ((DrawerLayout) parent).closeDrawer(navigationView);
                            }
                        }
                        return handled;
                    }
                });

        final WeakReference<NavigationView> weakReference = new WeakReference<>(navigationView);
        navController.addOnDestinationChangedListener(
                new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(@NonNull NavController controller,
                                                     @NonNull NavDestination destination, @Nullable Bundle arguments) {
                        NavigationView view = weakReference.get();
                        if (view == null) {
                            navController.removeOnDestinationChangedListener(this);
                            return;
                        }
                        Menu menu = view.getMenu();
                        for (int h = 0, size = menu.size(); h < size; h++) {
                            MenuItem item = menu.getItem(h);
                            item.setChecked(matchDestination(destination, item.getItemId()));
                        }
                    }
                });
    }

    static boolean matchDestination(@NonNull NavDestination destination,
                                    @IdRes int destId) {
        NavDestination currentDestination = destination;
        while (currentDestination.getId() != destId && currentDestination.getParent() != null) {
            currentDestination = currentDestination.getParent();
        }
        return currentDestination.getId() == destId;
    }
}
