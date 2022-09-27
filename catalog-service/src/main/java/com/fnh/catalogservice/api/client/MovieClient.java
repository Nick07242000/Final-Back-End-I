package com.fnh.catalogservice.api.client;

import com.fnh.catalogservice.domain.model.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "movie-service")
public interface MovieClient {
    @GetMapping("/movies/{genre}")
    ResponseEntity<List<Movie>> findMovieByGenre(@PathVariable("genre") String genre);

    //For testing
    @GetMapping("/movies/withErrors/{genre}")
    ResponseEntity<List<Movie>> findMovieByGenreError(@PathVariable(value = "genre") String genre, @RequestParam("throwError") boolean throwError);
}