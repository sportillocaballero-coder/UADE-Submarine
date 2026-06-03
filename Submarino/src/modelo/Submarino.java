package modelo;

// El submarino recibe el daño de las cargas
public class Submarino {

    private int vidaActual;
    private boolean activo;
    private Punto posicion;

    // Comienza con 100 puntos de vida de vida.
    public Submarino(Punto inicio) {
        this.posicion = inicio;
        this.vidaActual = 100;
        this.activo = true;
    }

    public void mover(double deltaX, double deltaY) {
        posicion.mover(deltaX, deltaY);
    }

    public void cambiarProfundidad(double metro) {
        posicion.mover(0, metro);
    }

    // El daño es una cantidad fija de puntos.
    public void recibirDanio(int puntos) {
        vidaActual -= puntos;
        if (vidaActual <= 0) {
            vidaActual = 0;
            activo = false;
        }
    }

    public boolean estaActivo() {
        return activo;
    }

    public Punto getPosicion() {
        return posicion;
    }

    public int getVidaActual() {
        return vidaActual;
    }
}
