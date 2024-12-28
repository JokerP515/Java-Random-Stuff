package com.aluracinema.models;

import com.aluracinema.calc.Clasification;

public class Episode implements Clasification{
    private int numEp;
    private String name;
    private Serie serie;
    private int totalVisualizations = 0;

    public void setNumEp(int numEp){
        this.numEp = numEp;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSerie(Serie serie){
        this.serie = serie;
    }
    public int getNumEp() {
        return numEp;
    }
    public String getName() {
        return name;
    }
    public Serie getSerie() {
        return serie;
    }

    public int getTotalVisualizations() {
        return totalVisualizations;
    }
    public void setTotalVisualizations(int totalVisualizations) {
        this.totalVisualizations = totalVisualizations;
    }

    @Override
    public int getClasificacion() {
        return (this.totalVisualizations > 100) ? 4 : 2;
    }

}
