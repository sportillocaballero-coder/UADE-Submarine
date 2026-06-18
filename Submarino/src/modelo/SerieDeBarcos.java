package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// Maneja la oleada de barcos enemigos de un nivel.
public class SerieDeBarcos {

    private List<Barco> barcosActivos;
    private int max;            // máximo de barcos simultáneos en pantalla
    private int barcosGenerados;
    private int totalBarcos;    // total de barcos en la serie
    private double velocidad;
    private String direccion;   // "derecha" o "izquierda"
    private double velocidadCarga;

    /**
     * @param total         barcos totales de la serie (siempre 12)
     * @param maxSimultaneo máximo en pantalla al mismo tiempo (siempre 3)
     * @param velocidad     píxeles por ciclo que avanza cada barco
     * @param direccion     "derecha" (izq→der) o "izquierda" (der→izq)
     * @param velocidadCarga  velocidad de las cargas de profundidad
     */
    public SerieDeBarcos(int total, int maxSimultaneo, double velocidad, String direccion, double velocidadCarga) {
        this.totalBarcos = total;
        this.max = maxSimultaneo;
        this.barcosGenerados = 0;
        this.barcosActivos = new ArrayList<>();
        this.velocidad = velocidad;
        this.velocidadCarga = velocidadCarga;
        this.direccion = direccion;
    }

    // Genera un barco fuera del borde correspondiente según la dirección.
    public void generarBarco() {
        if (puedeLanzarNuevoBarco()) {
            double x;
            if (direccion.equals("derecha")) {
                x = -(Math.random() * 300 + Juego.BORDE_OFFSCREEN);   // fuera del borde izquierdo
            } else {
                x = Juego.ANCHO_PANTALLA + Math.random() * 300 + Juego.BORDE_OFFSCREEN; // fuera del borde derecho
            }
            Barco nuevo = new Barco(new Punto(x, 30), velocidad, direccion, velocidadCarga);
            barcosActivos.add(nuevo);
            barcosGenerados++;
        }
    }

    public void actualizarSerie() {
        barcosActivos.removeIf(b -> !b.isActivo());
    }

    public void procesarCiclo(int ciclos, Submarino sub, Consumer<CargaProfundidad> efectoExplosion) {
        if (ciclos % Juego.CICLOS_GENERAR_BARCO == 0 && puedeLanzarNuevoBarco()) {
            generarBarco();
        }

        for (Barco b : barcosActivos) {
            b.avanzar();
            b.actualizar();

            if (b.puedeLanzar()) {
                b.lanzarCarga();
            }

            for (CargaProfundidad c : b.getCargas()) {
                if (!c.estaExplotada()) {
                    c.caer();
                    if (sub.impactadoPor(c) || c.explotar()) {
                        c.forzarExplosion();
                        efectoExplosion.accept(c);
                    }
                } else if (c.estaExplotando()) {
                    c.actualizarExplosion();
                }
            }
        }
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

    public double getVelocidad() {
        return velocidad;
    }
}
