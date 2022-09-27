package com.fnh.catalogservice.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnh.catalogservice.domain.dto.CatalogDTO;
import com.fnh.catalogservice.domain.model.Catalog;
import com.fnh.catalogservice.domain.model.Movie;
import com.fnh.catalogservice.domain.model.Serie;
import com.fnh.catalogservice.domain.repository.CatalogRepository;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatalogService {

    public static Logger LOG = LoggerFactory.getLogger(CatalogService.class);
    private final SerieService serieService;
    private final MovieService movieService;
    private final CatalogRepository catalogRepository;
    private final ObjectMapper mapper;

    @Autowired
    public CatalogService(SerieService serieService, MovieService movieService, CatalogRepository catalogRepository,ObjectMapper mapper) {
        this.serieService = serieService;
        this.movieService = movieService;
        this.catalogRepository = catalogRepository;
        this.mapper = mapper;
    }

    @RabbitListener(queues = {"${queue.movie.name}"})
    public void saveMovie(Movie movie) {
        LOG.info("Movie Received: " + movie.toString());
        Catalog catalog;
        List<Movie> movies;
        Optional<Catalog> catalogOptional = catalogRepository.findByGenre(movie.getGenre());
        if (catalogOptional.isEmpty()){
            catalog = new Catalog();
            catalog.setGenre(movie.getGenre());
            catalog.setSeries(new ArrayList<>());
            movies = new ArrayList<>();
        } else {
            catalog = catalogOptional.get();
            movies = catalog.getMovies();
        }
        movies.add(movie);
        catalog.setMovies(movies);
        catalogRepository.save(catalog);
        LOG.info("Catalog registered: " + catalog);
    }

    @RabbitListener(queues = {"${queue.serie.name}"})
    public void saveSerie(Serie serie) {
        LOG.info("Se recibio una serie a traves de Rabbitmq " + serie.toString());
        Catalog catalog;
        List<Serie> series;
        Optional<Catalog> catalogOptional = catalogRepository.findByGenre(serie.getGenre());
        if (catalogOptional.isEmpty()){
            catalog = new Catalog();
            catalog.setGenre(serie.getGenre());
            catalog.setMovies(new ArrayList<>());
            series = new ArrayList<>();
        }else {
            catalog = catalogOptional.get();
            series = catalog.getSeries();
        }
        series.add(serie);
        catalog.setSeries(series);
        catalogRepository.save(catalog);
        LOG.info("Catalog registered: " + catalog);
    }

    public void save(CatalogDTO catalogDto) {
        Catalog catalog;
        Optional<Catalog> catalogOptional = catalogRepository.findByGenre(catalogDto.getGenre());
        if (catalogOptional.isEmpty()){
            catalog = new Catalog();
            catalog.setGenre(catalogDto.getGenre());
        } else {
            catalog = catalogOptional.get();
        }
        catalog.setSeries(catalogDto.getSeries());
        catalog.setMovies(catalogDto.getMovies());
        catalogRepository.save(catalog);
        LOG.info("Catalog registered: " + catalogDto);
    }

    public CatalogDTO getCatalogByGenreDB(String genre) {
        CatalogDTO catalogDto= new CatalogDTO();
        Optional<Catalog> catalogOptional = catalogRepository.findByGenre(genre);
        if (catalogOptional.isPresent()){
            Catalog catalog = catalogRepository.findByGenre(genre).get();
            catalogDto = mapper.convertValue(catalog,CatalogDTO.class);
            LOG.info("Search successful: " + catalogDto);
            return catalogDto;
        }
        return null;
    }

    @CircuitBreaker(name = "catalog", fallbackMethod = "catalogFallbackMethod")
    public CatalogDTO getCatalogByGenreFeign(String genre) {
        List<Movie> movies = movieService.findMovieByGenre(genre);
        List<Serie> series = serieService.findSerieByGenre(genre);
        CatalogDTO catalogDto = new CatalogDTO(genre, movies, series);
        LOG.info("Search successful: " + catalogDto);
        save(catalogDto);
        return catalogDto;
    }

    //For testing
    @CircuitBreaker(name = "catalog", fallbackMethod = "catalogFallbackMethod")
    public CatalogDTO getCatalogByGenreFeignError(String genre, Boolean movieErrors, Boolean serieErrors) {
        List<Movie> movies = movieService.findMovieByGenreError(genre, movieErrors);
        List<Serie> series = serieService.findSerieByGenreError(genre,serieErrors);
        CatalogDTO catalogDto = new CatalogDTO(genre, movies, series);
        LOG.info("Search successful: " + catalogDto);
        save(catalogDto);
        return catalogDto;
    }

    private void catalogFallbackMethod(CallNotPermittedException exception) {
        LOG.info("Circuitbreaker activated");
    }

}