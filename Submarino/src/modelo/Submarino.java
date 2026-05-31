package modelo;

public class Submarino {

    private int vidaActual;
    private boolean activo;
    private Punto posicion;

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

    public void recibirDanio(double porcentaje) {
        vidaActual -= (int) (vidaActual * porcentaje / 100.0);
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
