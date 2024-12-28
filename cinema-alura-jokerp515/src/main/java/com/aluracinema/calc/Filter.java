package com.aluracinema.calc;

public class Filter {
    public void filter(Clasification c){
        if (c.getClasificacion() >= 4){
            System.out.println("Quite nice at the momment");
        }else System.out.println("Regular");
    }
}
