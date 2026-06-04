package modelo;

import views.CargaView;

// Proyectil que cae verticalmente desde un barco enemigo.
public class CargaProfundidad {

    private Punto posicion;
    private double velocidad;
    private double profundidadDetonacion;
    private boolean explotada;
    // Ciclos restantes de animación de explosión (25 → 0).
    private int tiempoExplosion;

    // Explota de manera aleatroria a una profundidad entre 500 y 600 metros.
    public CargaProfundidad(Punto inicio, double velocidadCaida) {
        this.posicion = inicio;
        this.velocidad = velocidadCaida;
        this.profundidadDetonacion = 500 + Math.random() * 100;
        this.explotada = false;
        this.tiempoExplosion = 0;
    }

    public Punto getPosicion() {
        return posicion;
    }

    public void caer() {
        posicion.mover(0, velocidad);
    }

    public boolean explotar() {
        if (posicion.getY() >= profundidadDetonacion) {
            forzarExplosion();
        }
        return explotada;
    }

    public double calcularDistancia(Submarino sub) {
        double dx = posicion.getX() - sub.getPosicion().getX();
        double dy = posicion.getY() - sub.getPosicion().getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Marca la carga como explotada e inicia la animación de 25 frames.
    public void forzarExplosion() {
        if (!explotada) {
            explotada = true;
            tiempoExplosion = 25;
        }
    }

    // Descuenta un frame de la animación de explosión.
    public void actualizarExplosion() {
        if (tiempoExplosion > 0) {
            tiempoExplosion--;
        }
    }

    public boolean estaExplotada() {
        return explotada;
    }

    // True mientras la animación de explosión siga visible.
    public boolean estaExplotando() {
        return tiempoExplosion > 0;
    }

    public int getTiempoExplosion() {
        return tiempoExplosion;
    }

    public double getProfundidadDetonacion() {
        return profundidadDetonacion;
    }

    public CargaView toView() {
    return new CargaView(posicion.getX(), posicion.getY(),explotada, estaExplotando(), tiempoExplosion);
    }
}
