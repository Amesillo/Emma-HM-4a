/**
 * Archivo: GammaFunction.java
 * Autor: Emma Hernandez Mendoza
 * Fecha: 02/12/2025
 * Versión: 2.1
 * Descripción: Clase auxiliar matemática para calcular la función Gamma.
 * Soporta cálculos para enteros (factoriales) y números reales
 * (usando aproximación recursiva y propiedades de Gamma).
 */

public class GammaFunction {

    private double ultimoValorGamma; // Almacena el resultado más reciente

    /**
     * Calcula Gamma para un valor entero dado.
     * Matemáticamente equivalente al factorial: Gamma(n) = (n-1)!
     * @param entradaEntera El valor entero de entrada.
     * @return El factorial del valor de entrada.
     */
    public double computeIntGamma(int entradaEntera) {
        
        // Caso base para 0 o 1
        if (entradaEntera <= 1) {
            ultimoValorGamma = 1.0;
        } else {
            // Cálculo iterativo del factorial
            double acumulado = 1.0;
            for (int k = 2; k <= entradaEntera; k++) {
                acumulado *= k;
            }
            ultimoValorGamma = acumulado;
        }

        return ultimoValorGamma;
    }

    /**
     * Punto de entrada para calcular Gamma con números decimales (doubles).
     * @param valorDecimal El valor z para evaluar Gamma(z).
     * @return El resultado de la función Gamma.
     */
    public double computeDblGamma(double valorDecimal) {
        ultimoValorGamma = calcularRecursivo(valorDecimal);
        return ultimoValorGamma;
    }

    /**
     * Método privado recursivo para la lógica interna de Gamma.
     * Utiliza la propiedad Gamma(z) = (z-1) * Gamma(z-1).
     */
    private double calcularRecursivo(double z) {
        
        final double TOLERANCIA = 1e-12; // Constante para comparaciones de punto flotante

        // Caso base: Gamma(1) = 1
        if (Math.abs(z - 1.0) < TOLERANCIA) {
            return 1.0;
        }

        // Caso base: Gamma(1/2) = Raíz de Pi
        if (Math.abs(z - 0.5) < TOLERANCIA) {
            return Math.sqrt(Math.PI);
        }

        // Si z es efectivamente un entero, derivar al cálculo factorial
        // Nota: Gamma(z) para entero z es (z-1)!
        if (Math.abs(z - Math.rint(z)) < TOLERANCIA) {
            int n = (int) Math.round(z);
            return computeIntGamma(n - 1);
        }

        // Paso recursivo general
        return (z - 1.0) * calcularRecursivo(z - 1.0);
    }

    /**
     * Getter para recuperar el último valor calculado sin recalcular.
     */
    public double getGammaValue() {
        return ultimoValorGamma;
    }
}
