package com.fnh.catalogservice.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Catalog {
    @Id
    private String id;
    private String genre;
    private List<Movie> movies;
    private List<Serie> series;

    public Catalog() {
    }

    public String getId() {
        return id;
    }
    public String getGenre() {
        return genre;
    }
    public List<Movie> getMovies() {
        return movies;
    }
    public List<Serie> getSeries() {
        return series;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return "{\"Catalog\":{"
                + "\"id\":\"" + id + "\""
                + ", \"genre\":\"" + genre + "\""
                + ", \"movies\":\"" + movies + "\""
                + ", \"series\":\"" + series + "\""
                + "}}";
    }
}
