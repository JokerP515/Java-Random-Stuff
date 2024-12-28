package com.aluracinema.models;

import com.aluracinema.calc.Clasification;

public class Movie extends Title implements Clasification{
    private String director;

    public Movie(String name, int year) {
        super(name,year);
    }

    public void setDirector(String director){
        this.director = director;
    }
    public String getDirector(){
        return this.director;
    }
    @Override
    public int getClasificacion() {
        return (int)(calculateAverageNote()/2);
    }

    @Override
    public String toString(){
        return "Movie: " + this.getName() + " ("+this.getYear()+")";
    }
}
