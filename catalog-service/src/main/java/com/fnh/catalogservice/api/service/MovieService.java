package com.fnh.catalogservice.api.service;

import com.fnh.catalogservice.api.client.MovieClient;
import com.fnh.catalogservice.domain.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieClient movieClient;

    @Autowired
    public MovieService(MovieClient movieFeignClient) {
        this.movieClient = movieFeignClient;
    }

    public List<Movie>  findMovieByGenre(String genre){
        ResponseEntity<List<Movie>> movieResponse = movieClient.findMovieByGenre(genre);
        if (movieResponse.getStatusCode().is2xxSuccessful())
            return movieResponse.getBody();
        return null;
    }

    public List<Movie> findMovieByGenreError(String genre, Boolean movieErrors){
        ResponseEntity<List<Movie>> movieResponse = movieClient.findMovieByGenreError(genre, movieErrors);
        if (movieResponse.getStatusCode().is2xxSuccessful())
            return movieResponse.getBody();
        return null;
    }
}