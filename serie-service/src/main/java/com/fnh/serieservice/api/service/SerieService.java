package com.fnh.serieservice.api.service;

import com.fnh.serieservice.domain.model.Serie;
import com.fnh.serieservice.domain.repository.SerieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService  {

    public static Logger LOG = LoggerFactory.getLogger(SerieService.class);

    @Value("${queue.serie.name}")
    private String serieQueue;

    private final SerieRepository serieRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public SerieService(SerieRepository serieRepository, RabbitTemplate rabbitTemplate) {
        this.serieRepository = serieRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Serie> findByGenre(String genre) {
        List<Serie> series = serieRepository.findByGenre(genre);
        LOG.info("Found: " + series);
        return series;
    }
    //For testing
    public List<Serie> findByGenreError(String genre, Boolean throwError) {
        LOG.info("Getting series by genre");
        if (throwError){
            LOG.error("Error in searching series of: " + genre);
            throw new RuntimeException();
        }
        return serieRepository.findByGenre(genre);
    }

    public void save(Serie serie) {
        serieRepository.save(serie);
        LOG.info("Saved series: " + serie);
        rabbitTemplate.convertAndSend(serieQueue, serie);
        LOG.info("Serie sent to queue");
    }
}