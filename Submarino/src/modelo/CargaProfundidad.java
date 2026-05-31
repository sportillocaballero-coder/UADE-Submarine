package modelo;

// Proyectil que cae verticalmente desde un barco enemigo.
public class CargaProfundidad {

    private Punto posicion;
    private double velocidad;
    private double profundidadDetonacion;
    private boolean explotada;

    // Explota automáticamente al llegar a y = 560.
    public CargaProfundidad(Punto inicio, double velocidadCaida) {
        this.posicion = inicio;
        this.velocidad = velocidadCaida;
        this.profundidadDetonacion = 560;
        this.explotada = false;
    }

    public Punto getPosicion() {
        return posicion;
    }

    public void caer() {
        posicion.mover(0, velocidad);
    }

    public boolean explotar() {
        if (posicion.getY() >= profundidadDetonacion) {
            explotada = true;
        }
        return explotada;
    }

    public double calcularDistancia(Submarino sub) {
        double dx = posicion.getX() - sub.getPosicion().getX();
        double dy = posicion.getY() - sub.getPosicion().getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean estaExplotada() {
        return explotada;
    }
}
