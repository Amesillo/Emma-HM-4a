/**
 * Archivo: Output.java
 * Autor: Emma Hernandez Mendoza
 * Fecha: 02/12/2025
 * Versión: 2.1
 * Descripción: Clase utilitaria encargada de la persistencia de datos.
 * Maneja la escritura de cadenas de texto en archivos físicos del sistema.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Output {

    /**
     * Método: writeData
     * Propósito: Crea o sobrescribe un archivo con la información proporcionada.
     * * @param nombreArchivo La ruta o nombre del archivo destino.
     * @param contenido El texto completo que se desea almacenar.
     */
    public void writeData(String nombreArchivo, String contenido) {

        // Uso de try-with-resources para asegurar el cierre del flujo
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivo))) {
            
            escritor.write(contenido);
            
        } catch (IOException excepcionIO) {
            // Envolver la excepción para no obligar a try-catch externos excesivos,
            // pero reportando el error claramente.
            throw new RuntimeException(
                "Fallo crítico al intentar guardar en: " + nombreArchivo,
                excepcionIO
            );
        }
    }
}
