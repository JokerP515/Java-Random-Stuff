package com.aluracinema.calc;

import com.aluracinema.models.*;

public class TimeCalc {
    private int totalTime;

    public int getTotalTime(){
        return totalTime;
    }

    public void sumTime(Title t){
        this.totalTime+=t.getDurationMinutes();
    }
}
