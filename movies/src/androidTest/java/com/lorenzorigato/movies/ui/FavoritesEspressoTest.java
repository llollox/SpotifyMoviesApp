package com.lorenzorigato.movies.ui;

import android.app.Instrumentation;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.gson.Gson;
import com.lorenzorigato.base.database.SpotifyMoviesDatabase;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.network.service.dto.ActorDTO;
import com.lorenzorigato.base.network.service.dto.GenreDTO;
import com.lorenzorigato.base.network.service.dto.MetadataDTO;
import com.lorenzorigato.base.network.service.dto.MovieDTO;
import com.lorenzorigato.base.network.service.dto.PictureDTO;
import com.lorenzorigato.base.network.service.envelope.MovieEnvelope;
import com.lorenzorigato.movies.MoviesApplication;
import com.lorenzorigato.movies.R;
import com.lorenzorigato.movies.assertion.RecyclerViewItemCountAssertion;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.lorenzorigato.movies.action.RecyclerViewActions.clickChildViewWithId;

public class FavoritesEspressoTest {

    // Static **************************************************************************************
    private static List<Genre> VALID_GENRES = new ArrayList<>();
    private static MovieEnvelope VALID_MOVIE_ENVELOPER = getMovieEnvelope();

    static {
        VALID_GENRES.add(new Genre(1, "Action"));
        VALID_GENRES.add(new Genre(2, "Adventure"));
        VALID_GENRES.add(new Genre(3, "Animation"));
    }

    private static MovieEnvelope getMovieEnvelope() {
        ArrayList<GenreDTO> genreDTOS = new ArrayList<>();
        genreDTOS.add(new GenreDTO(1, "Action"));
        ArrayList<ActorDTO> actorDTOS = new ArrayList<>();

        ActorDTO actor1 = new ActorDTO(1, "Daniel Craig", 2, "James Bond", "https://image.tmdb.org/t/p/w185/mr6cdu6lLRscfFUv8onVWZqaRdZ.jpg");
        ActorDTO actor2 = new ActorDTO(2, "Eva Green", 1, "Vesper Lynd", "https://image.tmdb.org/t/p/w185/wqK0BhMuNBvDqIg1bwT9RhYMy6L.jpg");
        ActorDTO actor3 = new ActorDTO(3, "Mads Mikkelsen", 2, "Le Chiffre", "https://image.tmdb.org/t/p/w185/8F1dY2rjZ1YDEKH0imDs21xdTDX.jpg");
        ActorDTO actor4 = new ActorDTO(4, "Judi Dench", 1, "M", "https://image.tmdb.org/t/p/w185/2is9RvJ3BQAku2EtCmyk5EZoxzT.jpg");

        actorDTOS.add(actor1);
        actorDTOS.add(actor2);
        actorDTOS.add(actor3);
        actorDTOS.add(actor4);

        ArrayList<MovieDTO> movieDTOS = new ArrayList<>();
        MovieDTO movieDTO = new MovieDTO(
                1,
                "Batman Begins",
                "Subtitle",
                "Description",
                8.0,
                new PictureDTO(
                        "thumb.jpg",
                        "medium.jpg",
                        "big.jpg"),
                genreDTOS,
                actorDTOS);
        movieDTOS.add(movieDTO);

        MetadataDTO metadataDTO = new MetadataDTO(true, 1, 1);
        return new MovieEnvelope(metadataDTO, movieDTOS);
    }


    // Private class attributes ********************************************************************
    private Context context;
    private MockWebServer webServer;

    @Rule
    public ActivityTestRule<MoviesActivity> activityRule = new ActivityTestRule<>(MoviesActivity.class, true, false);

    @Before
    public void setup() {

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        this.context = instrumentation.getTargetContext().getApplicationContext();
        MoviesApplication moviesApplication = (MoviesApplication) this.context;

        SpotifyMoviesDatabase database = moviesApplication.getComponent().getDatabase();
        database.clearAllTables();

        this.webServer = new MockWebServer();

        MockResponse genresResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(new Gson().toJson(VALID_GENRES));

        this.webServer.enqueue(genresResponse);
    }

    @After
    public void tearDown() throws IOException {
        this.webServer.shutdown();
    }


    // Test methods ********************************************************************************
    @Test
    public void favorites__verifyInitialMessage() throws IOException {
        this.webServer.start(8080);
        this.activityRule.launchActivity(null);

        this.goToFavorites();

        String initialMessage = this.context.getString(R.string.favorites_empty_placehoder);

        // Verify the initial message is shown to the user
        onView(withId(R.id.movieList_emptyPlaceHolder__textView))
                .check(matches(withText(initialMessage)));
    }


    @Test
    public void favorites__whenSetFavoriteFromDetail__seeMovieInFavorites() throws IOException {
        this.searchForActionMovies();

        // Click on first item of Recycler View to go to Movie Detail
        onView(withId(R.id.movieList_recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Click on fab to set movie as favorite
        onView(withId(R.id.fab)).perform(click());

        // Press back button to return to search screen
        onView(ViewMatchers.isRoot()).perform(pressBack());

        this.closeSearchView();

        this.goToFavorites();

        onView(withId(R.id.movieList_recyclerView))
                .check(new RecyclerViewItemCountAssertion(1));
    }

    @Test
    public void favorites__whenSetFavoriteFromList__seeMovieInFavorites() throws IOException {
        this.searchForActionMovies();

        // Set first movie as favorite
        onView(withId(R.id.movieList_recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.imageView_movieListItem_favorite)));

        this.closeSearchView();

        this.goToFavorites();

        onView(withId(R.id.movieList_recyclerView))
                .check(new RecyclerViewItemCountAssertion(1));

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click remove favorites from first item
        onView(withId(R.id.movieList_recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.imageView_movieListItem_favorite)));

        // Wait for ListAdapter update that are triggered in a background thread
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.movieList_recyclerView))
                .check(new RecyclerViewItemCountAssertion(0));
    }


    // Private class methods ***********************************************************************
    private void closeSearchView() {
        // Press back button to return to close search bar
        // There is a bug into the SearchView and I have to call pressBack twice in order to close it.
        // https://stackoverflow.com/questions/49574935/android-espresso-closing-the-searchview
        onView(ViewMatchers.isRoot()).perform(pressBack());
        onView(ViewMatchers.isRoot()).perform(pressBack());
    }

    private void goToFavorites() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(DrawerActions.open()); // Open Drawer

        // Start the screen of your activity.
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.favorites_fragment_dest));
    }

    private void searchForActionMovies() throws IOException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MockResponse moviesResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(new Gson().toJson(VALID_MOVIE_ENVELOPER));

        this.webServer.enqueue(moviesResponse);
        this.webServer.start(8080);
        this.activityRule.launchActivity(null);

        // Wait for genres request
        try {
            Thread.sleep(1300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on search button
        onView(withId(R.id.action_search)).perform(click());

        // Type Genre on Search View
        onView(isAssignableFrom(EditText.class))
                .perform(typeText("Action"), pressKey(KeyEvent.KEYCODE_ENTER));

        // Wait for movies request
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
