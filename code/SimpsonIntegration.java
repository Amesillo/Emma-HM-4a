/**
 * Archivo: SimpsonIntegration.java
 * Autor: Emma Hernandez Mendoza
 * Fecha: 02/12/2025
 * Versión: 2.1
 * Descripción: Implementación lógica de la regla de Simpson para
 * calcular la integral de la distribución t-Student.
 * Desglosa el cálculo en pasos secuenciales (cálculo de W, Xi, coeficiente, etc.).
 */

public class SimpsonIntegration {

    // Variables de estado para el cálculo
    private int numSegmentos;        // n
    private double anchoSegmento;    // w
    private int gradosLibertad;      // dof
    private double limiteX;          // x

    // Estructuras de datos para almacenar pasos intermedios
    private double[] puntosXi;       // Valores de x en cada segmento
    private double[] terminosBase;   // Parte base de la fórmula: 1 + (xi^2 / dof)
    private double exponente;        // Exponente: (dof + 1) / 2
    private double coeficiente;      // Constante C calculada con Gamma
    private double[] valoresFxi;     // Evaluación de la función en cada punto
    private double[] terminosSimpson; // Valores ponderados (x1, x4, x2...)
    private double resultadoIntegral; // Resultado final acumulado

    /**
     * Método: integrate
     * Propósito: Orquestador principal. Ejecuta la secuencia completa de cálculo
     * requerida para obtener la integral acumulada P(T <= x).
     * * @param numSegmentos Cantidad de divisiones (debe ser par).
     * @param limiteX Valor hasta donde se integra.
     * @param gradosLibertad Parámetro de la distribución.
     * @return El valor aproximado de la integral.
     */
    public double integrate(int numSegmentos, double limiteX, int gradosLibertad) {

        this.numSegmentos = numSegmentos;
        this.limiteX = limiteX;
        this.gradosLibertad = gradosLibertad;

        // Ejecución secuencial de los pasos del algoritmo
        calcularAncho();
        generarPuntosXi();
        calcularTerminosBase();
        calcularExponente();
        calcularCoeficiente();
        evaluarFuncionFxi();
        aplicarMultiplicadoresSimpson();
        sumarYObtenerResultado();

        return resultadoIntegral;
    }

    /**
     * Paso 1: Calcular el ancho de cada segmento (w = x / n).
     */
    private void calcularAncho() {
        if (numSegmentos == 0) {
            anchoSegmento = 0.0;
        } else {
            anchoSegmento = limiteX / numSegmentos;
        }
    }

    /**
     * Paso 2: Generar los puntos Xi (i * w).
     */
    private void generarPuntosXi() {
        puntosXi = new double[numSegmentos + 1];
        for (int i = 0; i <= numSegmentos; i++) {
            puntosXi[i] = i * anchoSegmento;
        }
    }

    /**
     * Paso 3: Calcular el término base (1 + xi^2/dof).
     */
    private void calcularTerminosBase() {
        terminosBase = new double[numSegmentos + 1];
        for (int i = 0; i <= numSegmentos; i++) {
            double valXi = puntosXi[i];
            terminosBase[i] = 1.0 + (valXi * valXi / gradosLibertad);
        }
    }

    /**
     * Paso 4: Calcular el exponente común ((dof + 1) / 2).
     */
    private void calcularExponente() {
        exponente = (gradosLibertad + 1.0) / 2.0;
    }

    /**
     * Paso 5: Calcular el coeficiente constante usando la función Gamma.
     * C = Gamma((dof+1)/2) / [sqrt(dof*pi) * Gamma(dof/2)]
     */
    private void calcularCoeficiente() {
        GammaFunction funcionGamma = new GammaFunction();

        double numerador = funcionGamma.computeDblGamma((gradosLibertad + 1.0) / 2.0);
        double denominador = Math.sqrt(gradosLibertad * Math.PI) 
                           * funcionGamma.computeDblGamma(gradosLibertad / 2.0);

        coeficiente = numerador / denominador;
    }

    /**
     * Paso 6: Evaluar f(xi) = Coef * (Base ^ -Exponente).
     */
    private void evaluarFuncionFxi() {
        valoresFxi = new double[numSegmentos + 1];
        for (int i = 0; i <= numSegmentos; i++) {
            valoresFxi[i] = coeficiente * Math.pow(terminosBase[i], -exponente);
        }
    }

    /**
     * Paso 7: Aplicar patrón de Simpson (1, 4, 2, 4, ... 1).
     */
    private void aplicarMultiplicadoresSimpson() {
        terminosSimpson = new double[numSegmentos + 1];

        for (int i = 0; i <= numSegmentos; i++) {
            int multiplicador;

            // Extremos tienen peso 1
            if (i == 0 || i == numSegmentos) {
                multiplicador = 1;
            } 
            // Posiciones pares (interiores) tienen peso 2
            else if (i % 2 == 0) {
                multiplicador = 2;
            } 
            // Posiciones impares tienen peso 4
            else {
                multiplicador = 4;
            }

            terminosSimpson[i] = multiplicador * valoresFxi[i];
        }
    }

    /**
     * Paso 8: Suma final y multiplicación por w/3.
     */
    private void sumarYObtenerResultado() {
        double sumaTotal = 0.0;
        for (double val : terminosSimpson) {
            sumaTotal += val;
        }
        resultadoIntegral = (anchoSegmento / 3.0) * sumaTotal;
    }

    /**
     * Getter para obtener el resultado final almacenado.
     */
    public double getFinalValue() {
        return resultadoIntegral;
    }
}
