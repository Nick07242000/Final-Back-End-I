package com.fnh.movieservice.api.service;

import com.fnh.movieservice.domain.model.Movie;
import com.fnh.movieservice.domain.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private static final Logger LOG = LoggerFactory.getLogger(MovieService.class);

    @Value("${queue.movie.name}")
    private String movieQueue;

    private final MovieRepository repository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MovieService(MovieRepository movieRepository, RabbitTemplate rabbitTemplate) {
        this.repository = movieRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Movie> findByGenre(String genre) {
        List<Movie> movies = repository.findByGenre(genre);
        LOG.info("Found: " + movies);
        return movies;
    }
    //For testing
    public List<Movie> findByGenre(String genre, Boolean throwError) {
        LOG.info("Getting movies by genre");
        if (throwError) {
            LOG.error("Error in searching movies");
            throw new RuntimeException();
        }
        return repository.findByGenre(genre);
    }

    public void save(Movie movie) {
        repository.save(movie);
        LOG.info("Saved movie: " + movie);
        rabbitTemplate.convertAndSend(movieQueue, movie);
        LOG.info("Movie sent to queue");
    }

}