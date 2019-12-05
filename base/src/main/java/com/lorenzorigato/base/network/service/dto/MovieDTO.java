package com.lorenzorigato.base.network.service.dto;

import java.util.List;

public class MovieDTO {

    // Class attributes ****************************************************************************
    int id;
    String title;
    double rating;
    PictureDTO cover;
    List<GenreDTO> genres;


    // Constructor *********************************************************************************
    public MovieDTO(int id, String title, double rating, PictureDTO cover, List<GenreDTO> genres) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.cover = cover;
        this.genres = genres;
    }


    // Class methods *******************************************************************************
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public PictureDTO getCover() {
        return cover;
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", cover=" + cover +
                ", genres=" + genres +
                '}';
    }
}
