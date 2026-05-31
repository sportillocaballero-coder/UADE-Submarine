package modelo;

// Coordenada 2D usada por todos los objetos del juego.
public class Punto {

    private double x;
    private double y;

    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void mover(double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
}
