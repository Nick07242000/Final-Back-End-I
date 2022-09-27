package com.fnh.catalogservice.domain.model;

public class Movie {
    private Integer id;
    private String name;
    private String genre;
    private String urlStream;

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getGenre() {
        return genre;
    }
    public String getUrlStream() {
        return urlStream;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setUrlStream(String urlStream) {
        this.urlStream = urlStream;
    }

    @Override
    public String toString() {
        return "{\"Movie\":{"
                + "\"id\":\"" + id + "\""
                + ", \"name\":\"" + name + "\""
                + ", \"genre\":\"" + genre + "\""
                + ", \"urlStream\":\"" + urlStream + "\""
                + "}}";
    }
}
