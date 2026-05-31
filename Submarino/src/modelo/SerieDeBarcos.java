package modelo;

import java.util.ArrayList;
import java.util.List;

// Maneja la oleada de barcos enemigos de un nivel.
public class SerieDeBarcos {

    private List<Barco> barcosActivos;
    private int max;
    private int barcosGenerados;
    private int totalBarcos;

    public SerieDeBarcos(int cantMax) {
        this.max = cantMax;
        this.totalBarcos = cantMax;
        this.barcosGenerados = 0;
        this.barcosActivos = new ArrayList<>();
    }

    // Genera un barco fuera del borde izquierdo en posición aleatoria.
    public void generarBarco() {
        if (puedeLanzarNuevoBarco()) {
            double offsetX = -(Math.random() * 300 + 80);
            Barco nuevo = new Barco(new Punto(offsetX, 30), 2.0);
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
