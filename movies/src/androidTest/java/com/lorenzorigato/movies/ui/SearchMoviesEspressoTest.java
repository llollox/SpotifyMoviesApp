package com.lorenzorigato.movies.ui;

import android.app.Instrumentation;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

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
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class SearchMoviesEspressoTest {


    // Static **************************************************************************************
    public static List<Genre> VALID_GENRES = new ArrayList<>();
    static {
        VALID_GENRES.add(new Genre(1, "Action"));
        VALID_GENRES.add(new Genre(2, "Adventure"));
        VALID_GENRES.add(new Genre(3, "Animation"));
    }


    // Private class attributes ********************************************************************
    private Context context;
    private MockWebServer webServer;

    @Rule
    public ActivityTestRule<MoviesActivity> activityRule = new ActivityTestRule<>(MoviesActivity.class, true, false);

    @Before
    public void setup() throws InterruptedException {

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        this.context = instrumentation.getTargetContext().getApplicationContext();
        MoviesApplication moviesApplication = (MoviesApplication) this.context;

        SpotifyMoviesDatabase database = moviesApplication.getComponent().getDatabase();
        database.clearAllTables();

        this.webServer = new MockWebServer();
        Thread.sleep(200);
    }

    @After
    public void tearDown() throws IOException, InterruptedException {
        this.webServer.shutdown();
        Thread.sleep(200);
    }


    // Test methods ********************************************************************************
    @Test
    public void search__whenGenreLoadSucceeds__verifyInitialMessageShown() throws IOException {
        MockResponse genresResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(new Gson().toJson(VALID_GENRES));

        this.webServer.enqueue(genresResponse);
        this.webServer.start(8080);
        this.activityRule.launchActivity(null);

        String initialMessage = this.context.getString(R.string.search_empty_place_holder);

        // Verify the initial message is shown to the user
        onView(withId(R.id.movieList_emptyPlaceHolder__textView))
                .check(matches(withText(initialMessage)));
    }

    @Test
    public void search__whenGenreLoadFails__verifyErrorMessageShown() throws IOException {
        MockResponse genresResponse = new MockResponse()
                .setResponseCode(500);

        this.webServer.enqueue(genresResponse);
        this.webServer.start(8080);
        this.activityRule.launchActivity(null);

        View decorView = this.activityRule.getActivity().getWindow().getDecorView();
        String toastMessage = this.context.getString(R.string.search_error_genres_not_loaded);

        // Verify a toast is shown with error message
        onView(withText(toastMessage))
                .inRoot(withDecorView(not(is(decorView))))
            .check(matches(isDisplayed()));
    }

    @Test
    public void search__whenTypeWrongGenre__verifyErrorMessageShown() throws IOException, InterruptedException {
        Thread.sleep(1000);

        MockResponse genresResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(new Gson().toJson(VALID_GENRES));

        this.webServer.enqueue(genresResponse);
        this.webServer.start(8080);
        this.activityRule.launchActivity(null);

        // Click on search button
        onView(withId(R.id.action_search)).perform(click());

        onView(isAssignableFrom(EditText.class))
                .perform(typeText("Unknown Genre"), pressKey(KeyEvent.KEYCODE_ENTER));

        View decorView = this.activityRule.getActivity().getWindow().getDecorView();
        String toastMessage = this.context.getString(R.string.search_invalid_genre_error);

        // Verify a toast is shown with error message
        onView(withText(toastMessage))
                .inRoot(withDecorView(not(is(decorView))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void search__whenProperGenreChosen__verifyMoviesAreShown() throws IOException {
        MockResponse genresResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(new Gson().toJson(VALID_GENRES));

        ArrayList<GenreDTO> genreDTOS = new ArrayList<>();
        genreDTOS.add(new GenreDTO(1, "Action"));
        ArrayList<ActorDTO> actorDTOS = new ArrayList<>();
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
        MovieEnvelope envelope = new MovieEnvelope(metadataDTO, movieDTOS);

        MockResponse moviesResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(new Gson().toJson(envelope));

        this.webServer.enqueue(genresResponse);
        this.webServer.enqueue(moviesResponse);
        this.webServer.start(8080);
        this.activityRule.launchActivity(null);

        // Click on search button
        onView(withId(R.id.action_search)).perform(click());

        // Type Genre on Search View
        onView(isAssignableFrom(EditText.class))
                .perform(typeText("Action"), pressKey(KeyEvent.KEYCODE_ENTER));

        // Without this sleep Espresso assert the recycler view too early and assertion fails
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Recycler View is Visible
        onView(withId(R.id.movieList_recyclerView)).check(matches(isDisplayed()));

        // Verify count number items in Recycler View
        onView(withId(R.id.movieList_recyclerView)).check(new RecyclerViewItemCountAssertion(1));
    }

    private void loadGenres() {

    }
}
