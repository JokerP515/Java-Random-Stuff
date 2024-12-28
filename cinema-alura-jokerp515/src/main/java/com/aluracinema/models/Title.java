package com.aluracinema.models;

import com.google.gson.annotations.SerializedName;

public class Title {
    private String name;
    private int year;
    private int durationMinutes;
    private boolean includedInPlan;
    private double sumNotes=0;
    private int countNotes=0;

    public Title(String name, int year){
        this.name = name;
        this.year = year;
    }

    public Title(Omdb omdb) {
        this.name = omdb.title();
        this.year = Integer.valueOf(omdb.year());

        if(omdb.runtime().equals("N/A")){
            this.durationMinutes = 0;
        }else this.durationMinutes = Integer.valueOf(omdb.runtime().replaceAll("[\\D]", ""));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public boolean isIncludedInPlan() {
        return includedInPlan;
    }

    public void setIncludedInPlan(boolean includedInPlan) {
        this.includedInPlan = includedInPlan;
    }

    public double getSumNotes() {
        return sumNotes;
    }

    public int getCountNotes(){
        return countNotes;
    }

    public void printInfo(){
        System.out.println("Name: " + name);
        System.out.println("Year: " + year);
        System.out.println("Duration: " + getDurationMinutes() + " minutes");
        System.out.println("Note: "+calculateAverageNote());
    }

    public void note(double userNote){
        sumNotes += userNote;
        countNotes++;
    }

    public double calculateAverageNote(){
        return (countNotes != 0) ? sumNotes/countNotes : 0;
    }

    @Override
    public String toString(){
        return "Name: " + name + " - Year: " + year + " - Duration: " + durationMinutes + " minutes";
    }
}
