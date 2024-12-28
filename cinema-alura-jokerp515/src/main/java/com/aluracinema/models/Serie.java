package com.aluracinema.models;

public class Serie extends Title{
    private int seasons;
    private int episodesPerSeason;
    private int minutesPerEpisode;

    public Serie(String name, int year){
        super(name,year);
    }

    public void setSeasons(int seasons){
        this.seasons = seasons;
    }
    public int getSeasons(){
        return this.seasons;
    }
    public void setEpisodesPerSeason(int episodesPerSeason){
        this.episodesPerSeason = episodesPerSeason;
    }
    public int getEpisodesPerSeason(){
        return this.episodesPerSeason;
    }
    
    public void setMinutesPerEpisode(int minutesPerEpisode){
        this.minutesPerEpisode = minutesPerEpisode;
    }

    @Override
    public int getDurationMinutes(){
        return seasons * episodesPerSeason * minutesPerEpisode;
    }

}
