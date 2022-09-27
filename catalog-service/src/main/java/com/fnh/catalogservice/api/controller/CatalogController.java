package com.fnh.catalogservice.api.controller;

import com.fnh.catalogservice.api.service.CatalogService;
import com.fnh.catalogservice.domain.dto.CatalogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("/catalogs")
public class CatalogController {

    private CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/db/{genre}")
    ResponseEntity<CatalogDTO> getCatalogByGenreDB(@PathVariable String genre, HttpServletResponse response) {
        CatalogDTO catalogDto = catalogService.getCatalogByGenreDB(genre);
        return Objects.isNull(catalogDto)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(catalogDto, HttpStatus.OK);
    }

    @GetMapping("/{genre}")
    ResponseEntity<CatalogDTO> getCatalogByGenre(@PathVariable String genre, HttpServletResponse response) {
        CatalogDTO catalogDto = catalogService.getCatalogByGenreFeign(genre);
        return Objects.isNull(catalogDto)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(catalogDto, HttpStatus.OK);
    }

    //For testing
    @GetMapping("/errors/{genre}")
    ResponseEntity<CatalogDTO> getCatalogByGenreError(@PathVariable String genre, @RequestParam("movieErrors") Boolean movieErrors, @RequestParam("serieErrors") Boolean serieErrors, HttpServletResponse response) {
        CatalogDTO catalogDto = catalogService.getCatalogByGenreFeignError(genre,movieErrors,serieErrors);
        return Objects.isNull(catalogDto)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(catalogDto, HttpStatus.OK);
    }
}