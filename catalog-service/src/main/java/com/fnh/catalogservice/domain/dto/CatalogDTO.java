package com.fnh.catalogservice.domain.dto;

import com.fnh.catalogservice.domain.model.Movie;
import com.fnh.catalogservice.domain.model.Serie;

import java.util.List;

public class CatalogDTO {
    private String genre;
    private List<Movie> movies;
    private List<Serie> series;

    public CatalogDTO() {
    }

    public CatalogDTO(String genre, List<Movie> movies, List<Serie> series) {
        this.genre = genre;
        this.movies = movies;
        this.series = series;
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
                + ", \"genre\":\"" + genre + "\""
                + ", \"movies\":\"" + movies + "\""
                + ", \"series\":\"" + series + "\""
                + "}}";
    }
}
