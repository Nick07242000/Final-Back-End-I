package com.fnh.catalogservice.api.service;

import com.fnh.catalogservice.api.client.SerieClient;
import com.fnh.catalogservice.domain.model.Serie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    private final SerieClient serieClient;

    @Autowired
    public SerieService(SerieClient serieFeignClient) {
        this.serieClient = serieFeignClient;
    }

    public List<Serie> findSerieByGenre(String genre){
        ResponseEntity<List<Serie>> serieResponse = serieClient.findSerieByGenre(genre);
        if (serieResponse.getStatusCode().is2xxSuccessful()){
            return serieResponse.getBody();
        }
        return null;
    }

    public List<Serie> findSerieByGenreError(String genre, Boolean serieErrors){
        ResponseEntity<List<Serie>> serieResponse = serieClient.findSerieByGenreError(genre,serieErrors);
        if (serieResponse.getStatusCode().is2xxSuccessful()){
            return serieResponse.getBody();
        }
        return null;
    }
}
