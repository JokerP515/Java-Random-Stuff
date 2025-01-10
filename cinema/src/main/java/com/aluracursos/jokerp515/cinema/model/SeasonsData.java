package com.aluracursos.jokerp515.cinema.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonsData(
    @JsonAlias("Season") int numSeason,
    @JsonAlias("Episodes") List<EpisodeData> episodes
) {
    
}
