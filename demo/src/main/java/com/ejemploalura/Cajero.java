package com.ejemploalura;

public class Cajero {
    private String nombre;
    private double dineroDisponible;

    void setNombre(String nombre){
        this.nombre = nombre;
    }
    String getNombre(){
        return this.nombre;
    }
        void setDineroDisponible(double dineroDisponible){
        this.dineroDisponible = dineroDisponible;
    }
    double getDineroDisponible(){
        return this.dineroDisponible;
    }

    void consultarDinero(){
        System.out.println("El dinero disponible es: " + getDineroDisponible());
    }

    void ingresarDinero(double cantidad){
        if(cantidad <= 0){
            System.out.println("La cantidad ingresada no es válida");
            return;
        }

        this.dineroDisponible += cantidad;
        System.out.println("Dinero ingresado con éxito");
        consultarDinero();
    }

    void retirarDinero(double cantidad){
        if (cantidad <= 0){
            System.out.println("La cantidad ingresada no es válida");
            return;
        }
        if (cantidad > dineroDisponible){
            System.out.println("No hay suficiente dinero");
            return;
        }
        dineroDisponible -= cantidad;
        System.out.println("Se ha retirado $" + cantidad + " con éxito");

        consultarDinero();
    }

}