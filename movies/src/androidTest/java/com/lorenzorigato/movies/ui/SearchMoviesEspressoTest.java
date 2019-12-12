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
import com.lorenzorigato.movies.MoviesApplication;
import com.lorenzorigato.movies.R;

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
}
