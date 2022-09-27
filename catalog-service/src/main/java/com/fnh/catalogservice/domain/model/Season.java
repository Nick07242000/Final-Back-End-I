package com.fnh.catalogservice.domain.model;

import java.util.Set;

public class Season {
    private Integer id;
    private Integer seasonNumber;
    private String genre;
    private Set<Chapter> chapters;

    public Season() {
    }

    public Integer getId() {
        return id;
    }
    public Integer getSeasonNumber() {
        return seasonNumber;
    }
    public String getGenre() {
        return genre;
    }
    public Set<Chapter> getChapters() {
        return chapters;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }

    @Override
    public String toString() {
        return "{\"Season\":{"
                + "\"id\":\"" + id + "\""
                + ", \"season\":\"" + seasonNumber + "\""
                + ", \"genre\":\"" + genre + "\""
                + ", \"chapter\":\"" + chapters + "\""
                + "}}";
    }
}