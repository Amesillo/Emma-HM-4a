/**
* Autor: Emma Hernandez Mendoza
 * Clase: App
 * Propósito: Clase conductora (Driver) que sirve como punto de entrada.
 * Fecha: 02/12/2025
 * Notas: Esta clase inicializa el objeto Logic para comenzar la integración.
 */

public class App {

    /**
     * Método main
     * Encargado de arrancar la aplicación.
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Se crea la instancia del controlador de lógica
        Logic procesoPrincipal = new Logic(); 
        
        // Se delega el flujo al método de integración
        procesoPrincipal.logic1a(); 
    }
}
