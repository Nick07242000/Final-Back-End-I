package com.fnh.serieservice.api.controller;

import com.fnh.serieservice.api.service.SerieService;
import com.fnh.serieservice.domain.model.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    private final SerieService serieService;

    @Autowired
    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    @GetMapping("/{genre}")
    public ResponseEntity<List<Serie>> getSerieByGenre(@PathVariable String genre) {
        return ResponseEntity.ok().body(serieService.findByGenre(genre));
    }

    //For testing circuit breaker
    @GetMapping("/withErrors/{genre}")
    public ResponseEntity<List<Serie>> getSerieByGenreError(@PathVariable String genre, @RequestParam("throwError") boolean throwError) {
        return ResponseEntity.ok().body(serieService.findByGenreError(genre, throwError));
    }

    @PostMapping
    public ResponseEntity<String> saveSerie(@RequestBody Serie serie) {
        serieService.save(serie);
        return ResponseEntity.ok().body("Serie: "+ serie.getName() + " added");
    }
}