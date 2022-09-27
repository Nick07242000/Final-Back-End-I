package com.fnh.catalogservice.domain.model;

import java.util.Set;

public class Serie {
    private String id;
    private String name;
    private String genre;
    private Set<Season> seasons;

    public Serie() {
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getGenre() {
        return genre;
    }
    public Set<Season> getSeasons() {
        return seasons;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
    }

    @Override
    public String toString() {
        return "{\"Series\":{"
                + "\"id\":\"" + id + "\""
                + ", \"name\":\"" + name + "\""
                + ", \"genre\":\"" + genre + "\""
                + ", \"seasons\":\"" + seasons + "\""
                + "}}";
    }
}