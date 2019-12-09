package com.lorenzorigato.movies.ui;

import android.app.Instrumentation;
import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.gson.Gson;
import com.lorenzorigato.base.database.SpotifyMoviesDatabase;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.movies.MoviesApplication;
import com.lorenzorigato.movies.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class SearchMoviesEspressoTest {


    // Static **************************************************************************************
    public static List<Genre> VALID_GENRES = new ArrayList<>();
    static {
        VALID_GENRES.add(new Genre(1, "Action"));
        VALID_GENRES.add(new Genre(2, "Adventure"));
        VALID_GENRES.add(new Genre(3, "Animation"));
    }


    private Context context;
    private MockWebServer webServer;

    @Rule
    public ActivityTestRule<MoviesActivity> activityRule = new ActivityTestRule<>(MoviesActivity.class, true, false);

    @Before
    public void setup() throws IOException {

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
        this.webServer.start(8080);
        this.activityRule.launchActivity(null);
    }


    // Test methods ********************************************************************************
    @Test
    public void verifyInitialMessageShown() {
        String initialMessage = this.context.getString(R.string.search_empty_place_holder);

        // Verify the initial message is shown to the user
        onView(withId(R.id.movieList_emptyPlaceHolder__textView))
                .check(matches(withText(initialMessage)));
    }
}
