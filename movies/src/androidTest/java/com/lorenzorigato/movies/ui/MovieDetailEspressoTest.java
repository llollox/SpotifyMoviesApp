package com.lorenzorigato.movies.ui;

import android.app.Instrumentation;
import android.content.Context;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.test.espresso.contrib.RecyclerViewActions;
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
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.lorenzorigato.movies.matcher.DrawableMatcher.withDrawable;

public class MovieDetailEspressoTest {

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
    private MovieDTO movieDTO;

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
        this.movieDTO = new MovieDTO(
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

        // Click on first item of Recycler View to go to Movie Detail
        onView(withId(R.id.movieList_recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @After
    public void tearDown() throws IOException {
        this.webServer.shutdown();
    }


    // Tests methods *******************************************************************************
    @Test
    public void detail__verifyInformationShownProperly() {

        // Verify movie detail screen shows proper information
        onView(withId(R.id.movie_subtitle_textview))
                .check(matches(withText(this.movieDTO.getSubtitle())));

        onView(withId(R.id.movie_rating_textview))
                .check(matches(withText(String.valueOf(this.movieDTO.getRating()))));

        onView(withId(R.id.movie_description_textview))
                .check(matches(withText(this.movieDTO.getDescription())));

        onView(withId(R.id.movie_detail_actors_recycler_view))
                .check(new RecyclerViewItemCountAssertion(this.movieDTO.getCast().size()));

        onView(withId(R.id.fab))
                .check(matches(withDrawable(R.drawable.ic_favorite_border_white_24dp)));
    }

    @Test
    public void detail__whenClickOnFavorite__verifySnackbarShown() {

        // Verify movie detail screen shows proper information
        onView(withId(R.id.fab))
                .perform(click());

        // Verify fab changes icon
        onView(withId(R.id.fab))
                .check(matches(withDrawable(R.drawable.ic_favorite_white_24dp)));

        // Snackbar shown
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(R.string.favorites_movie_added_success)));
    }
}
