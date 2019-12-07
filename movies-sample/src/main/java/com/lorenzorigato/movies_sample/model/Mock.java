package com.lorenzorigato.movies_sample.model;

import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;

import java.util.ArrayList;
import java.util.UUID;

public class Mock {

    public static Movie getBatmanBegins(String serverUrl) {
        return new Movie(1,
                "Batman Begins",
                "Evil fears the knight.",
                "Driven by tragedy, billionaire Bruce Wayne dedicates his life to uncovering and defeating the corruption that plagues his home, Gotham City.  Unable to work within the system, he instead creates a new identity, a symbol of fear for the criminal underworld - The Batman.",
                7.5,
                false,
                serverUrl + "system/pictures/photos/000/000/274/medium/open-uri20191202-4543-1fu33hi?1575302477");
    }

    public static Movie getCasinoRoyale(String serverUrl) {
        return new Movie(2,
                "Casino Royale",
                "Everyone has a past. Every legend has a beginning.",
                "Le Chiffre, a banker to the world's terrorists, is scheduled to participate in a high-stakes poker game in Montenegro, where he intends to use his winnings to establish his financial grip on the terrorist market. M sends Bond – on his maiden mission as a 00 Agent – to attend this game and prevent Le Chiffre from winning. With the help of Vesper Lynd and Felix Leiter, Bond enters the most important poker game in his already dangerous career.",
                7.3,
                false,
                serverUrl + "system/pictures/photos/000/000/247/medium/open-uri20191202-4543-e895d6?1575302465");
    }

    public static MovieWithActors getCasinoRoyaleWithActors(String serverUrl) {
        ArrayList<Actor> actors = new ArrayList<>();
        Movie casinoRoyale = getCasinoRoyale(serverUrl);
        Actor actor1 = new Actor(UUID.randomUUID().toString(), "Daniel Craig", 2, "James Bond", "https://image.tmdb.org/t/p/w185/mr6cdu6lLRscfFUv8onVWZqaRdZ.jpg", casinoRoyale.getId());
        Actor actor2 = new Actor(UUID.randomUUID().toString(), "Eva Green", 1, "Vesper Lynd", "https://image.tmdb.org/t/p/w185/wqK0BhMuNBvDqIg1bwT9RhYMy6L.jpg", casinoRoyale.getId());
        Actor actor3 = new Actor(UUID.randomUUID().toString(), "Mads Mikkelsen", 2, "Le Chiffre", "https://image.tmdb.org/t/p/w185/8F1dY2rjZ1YDEKH0imDs21xdTDX.jpg", casinoRoyale.getId());
        Actor actor4 = new Actor(UUID.randomUUID().toString(), "Judi Dench", 1, "M", "https://image.tmdb.org/t/p/w185/2is9RvJ3BQAku2EtCmyk5EZoxzT.jpg", casinoRoyale.getId());
        Actor actor5 = new Actor(UUID.randomUUID().toString(), "Jeffrey Wright", 2, "Felix Leiter", "https://image.tmdb.org/t/p/w185/wBh9rwK3aRr1hCrSRLxxPHKzGeU.jpg", casinoRoyale.getId());
        Actor actor6 = new Actor(UUID.randomUUID().toString(), "Caterina Murino", 1, "Solange Dimitrios", "https://image.tmdb.org/t/p/w185/4jGqHO8driLwa4rU19J0tif5ck8.jpg", casinoRoyale.getId());
        actors.add(actor1);
        actors.add(actor2);
        actors.add(actor3);
        actors.add(actor4);
        actors.add(actor5);
        actors.add(actor6);
        return new MovieWithActors(casinoRoyale, actors);
    }
}
