package com.aluracursos.jokerp515.cinema.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeData(
    @JsonAlias("Title") String title,
    @JsonAlias("Episode") int numEpisode,
    @JsonAlias("imdbRating") String rating,
    @JsonAlias("Released") String released
) {
    
}
