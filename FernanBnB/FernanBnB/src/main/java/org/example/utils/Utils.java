package org.example.utils;

import java.util.Scanner;

public class Utils {

    public static Scanner s = new Scanner(System.in);

    //Método para hacer la animación de cerrado
    public static void cerrarPrograma() {
        for (int i = 0; i < 4; i++) {
            System.out.print(".");
            try {
                Thread.sleep(800);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }

        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    //Método para parar y pulsar para continuar
    public static void pulseParaContinuar(){
        System.out.println("Pulse para continuar");
        s.nextLine();
        System.out.println();
        System.out.println();
        System.out.println();
    }


}
