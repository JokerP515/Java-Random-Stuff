package com.ejemploalura;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.UK);
        Cajero cajero = new Cajero();
        cajero.setNombre("Juan");
        cajero.setDineroDisponible(1000.0);
            
        String nombre = cajero.getNombre();
        double dineroDisponible = cajero.getDineroDisponible();

        System.out.println("Bienvenido "+nombre+"\nSu saldo actual es: "+dineroDisponible);

        while(true){
            System.out.println("Opciones del cajero:\n1. Ingresar dinero\n2. Retirar dinero\n3. Consultar saldo\n9. Salir\nIngrese su opción: ");
            int opcion = sc.nextInt();
            double cantidad = 0.0;
            dineroDisponible = cajero.getDineroDisponible();
            switch(opcion){
                case 1:
                    System.out.println("Ingrese la cantidad de dinero a consignar: ");
                    cantidad = sc.nextDouble();
                    cajero.ingresarDinero(cantidad);
                    System.out.println("");
                    break;

                case 2:
                    System.out.println("Ingrese la cantidad de dinero a retirar:");
                    cantidad = sc.nextDouble();
                    if (dineroDisponible <= 0){
                        System.out.println("No hay dinero disponible");
                        System.out.println("");
                        break;
                    }
                    cajero.retirarDinero(cantidad);
                    System.out.println("");
                    break;
                case 3:
                    cajero.consultarDinero();
                    System.out.println("");
                    break;
                case 9:
                    System.out.println("Gracias por utilizar nuestros servicios");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida");
                    System.out.println("");
                    break;
            }
        }
    }
}