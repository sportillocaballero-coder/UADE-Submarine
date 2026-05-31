package modelo;

public class MovimientoHorizontal extends Movimiento {

    public MovimientoHorizontal(Area area, int x, int y, int ancho, int alto, int velocidadX) {
        super(area, x, y, ancho, alto, velocidadX);
    }

    public void disparar() {
        System.out.println("Disparo desde X = " + this.posicionX);
    }
 
}
