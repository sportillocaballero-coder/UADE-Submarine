package modelo;

import java.util.ArrayList;
import java.util.List;

// Oleada de barcos enemigos en el nivel
public class SerieDeBarcos {

    private List<Barco> barcosActivos;
    private int max;            // máximo de barcos simultáneos en pantalla
    private int barcosGenerados;
    private int totalBarcos;    // total de barcos en la serie
    private double velocidad;
    private String direccion;   // "derecha" o "izquierda"

    /**
     * @param total         barcos totales de la serie (siempre 12)
     * @param maxSimultaneo máximo en pantalla al mismo tiempo (siempre 3)
     * @param velocidad     píxeles por ciclo que avanza cada barco
     * @param direccion     Derecha izquierda o viceversa
     */
    public SerieDeBarcos(int total, int maxSimultaneo, double velocidad, String direccion) {
        this.totalBarcos = total;
        this.max = maxSimultaneo;
        this.barcosGenerados = 0;
        this.barcosActivos = new ArrayList<>();
        this.velocidad = velocidad;
        this.direccion = direccion;
    }

    // Genera un barco fuera del borde
    public void generarBarco() {
        if (puedeLanzarNuevoBarco()) {
            double x;
            if (direccion.equals("derecha")) {
                x = -(Math.random() * 300 + 80);   // fuera del borde izquierdo
            } else {
                x = 900 + Math.random() * 300 + 80; // fuera del borde derecho
            }
            Barco nuevo = new Barco(new Punto(x, 30), velocidad, direccion);
            barcosActivos.add(nuevo);
            barcosGenerados++;
        }
    }

    public void actualizarSerie() {
        barcosActivos.removeIf(b -> !b.isActivo());
    }

    // La serie termina cuando se generaron todos los barcos y ninguno sigue activo.
    public boolean serieFinalizada() {
        return barcosGenerados >= totalBarcos && barcosActivos.isEmpty();
    }

    public List<Barco> getBarcosActivos() {
        return barcosActivos;
    }

    public boolean puedeLanzarNuevoBarco() {
        return barcosGenerados < totalBarcos && barcosActivos.size() < max;
    }
}
