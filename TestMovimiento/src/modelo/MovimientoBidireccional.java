package modelo;

public class MovimientoBidireccional extends Movimiento implements MovimientoVertical {

    private int velocidadY;

    public MovimientoBidireccional(Area area, int x, int y, int ancho, int alto, int velocidadX, int velocidadY) {
        super(area, x, y, ancho, alto, velocidadX);
        this.velocidadY = velocidadY;
    }

    @Override
    public int moverArriba() {
        int nuevaY = posicionY - velocidadY;
        if (areaJuego.estaDentroVertical(nuevaY, alto)) {
            posicionY = nuevaY;
        }
        return posicionY;
    }

    @Override
    public int moverAbajo() {
        int nuevaY = posicionY + velocidadY;
        if (areaJuego.estaDentroVertical(nuevaY, alto)) {
            posicionY = nuevaY;
        }
        return posicionY;
    }
}
