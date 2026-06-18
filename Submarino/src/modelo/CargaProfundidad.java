package modelo;

import views.CargaView;

// El torpedo que cae verticalmente
public class CargaProfundidad {

    private Punto posicion;
    private double velocidad;
    private double profundidadDetonacion;
    private boolean explotada;
    // Ciclos restantes de animación de explosión (25 → 0).
    private int tiempoExplosion;

    // Explota de manera aleatoria a una profundidad entre 500 y 600 metros.
    public CargaProfundidad(Punto inicio, double velocidadCaida) {
        this.posicion = inicio;
        this.velocidad = velocidadCaida;
        this.profundidadDetonacion = Juego.PROFUNDIDAD_DETONACION_MIN
            + Math.random() * (Juego.PROFUNDIDAD_DETONACION_MAX - Juego.PROFUNDIDAD_DETONACION_MIN);
        this.explotada = false;
        this.tiempoExplosion = 0;
    }

    public Punto getPosicion() {
        return posicion;
    }

    public double getVelocidad() {
        return velocidad;
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

    //Marca la carga como explotada y inicia una animacion, el tiempoExplosion son los frames que dura la animacion  
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

    public int calcularDanio(Submarino sub) {
        double dist = calcularDistancia(sub);
        if (dist > 100) return 0;
            if (dist > 50)  return 30;
                if (dist > 10)  return 50;           
        return -1;
}
}
