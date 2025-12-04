/*
 * Archivo: Logic.java
 * Autor: Emma Hernandez Mendoza
 * Fecha: 02/12/2025
 * Versión: 2.1
 * Descripción: Clase encargada de la lógica de control. 
 * Obtiene parámetros del usuario y refina el cálculo de la integral.
 */

import java.util.Scanner;

public class Logic {

    // Variables de instancia renombradas para mayor claridad
    private int numSegmentos;    // Antes intNumSeg
    private double errorLimite;  // Antes dblE (Epsilon)
    private int gradosLibertad;  // Antes intDOF
    private double limiteX;      // Antes dblX

    /**
     * Método: logic1a
     * Función: Ejecuta el flujo principal de solicitud de datos y 
     * el ciclo de aproximación de la integral.
     */
    public void logic1a() {
        
        Scanner entrada = new Scanner(System.in);

        // 1. Obtención de datos por consola
        System.out.println("--- Configuración de la Integral ---");
        
        System.out.print("Introduce el valor de x: ");
        limiteX = entrada.nextDouble();

        System.out.print("Introduce los grados de libertad (dof): ");
        gradosLibertad = entrada.nextInt();

        System.out.print("Introduce el error permitido (epsilon): ");
        errorLimite = entrada.nextDouble();

        entrada.close();

        // 2. Preparación del cálculo
        System.out.println("\n--- Iniciando cálculo ---");
        System.out.printf("Error objetivo: %.1E%n", errorLimite);

        SimpsonIntegration integrador = new SimpsonIntegration();
        numSegmentos = 10; // Valor inicial según especificación

        double resultadoPrevio = 0.0;
        double resultadoActual = integrador.integrate(numSegmentos, limiteX, gradosLibertad);

        // Imprimir estado inicial
        mostrarEstado(numSegmentos, resultadoActual);

        // 3. Ciclo de refinamiento
        // Se repite mientras la diferencia sea mayor al error permitido
        while (Math.abs(resultadoActual - resultadoPrevio) > errorLimite) {
            
            resultadoPrevio = resultadoActual;
            numSegmentos *= 2; // Duplicar segmentos (10 -> 20 -> 40...)

            resultadoActual = integrador.integrate(numSegmentos, limiteX, gradosLibertad);

            mostrarEstado(numSegmentos, resultadoActual);
        }

        // 4. Resultado Final
        System.out.println("\n=== Resultado Final ===");
        System.out.printf("x = %.4f | dof = %d | p = %.5f%n", 
                          limiteX, gradosLibertad, resultadoActual);
    }

    /**
     * Método auxiliar para imprimir el progreso y no repetir código.
     */
    private void mostrarEstado(int seg, double p) {
        System.out.println("Seg: " + seg + " -> p: " + p);
    }
}
