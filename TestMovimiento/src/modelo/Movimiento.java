package modelo;

import views.MovimientoView;

public abstract class Movimiento {
	
    protected int posicionX;
    protected int posicionY;
    protected int velocidadX;
    protected Area areaJuego;
    protected int ancho;
    protected int alto;

    public Movimiento(Area areaJuego, int x, int y, int ancho, int alto, int velocidadX) {
        this.areaJuego = areaJuego;
        this.posicionX = x;
        this.posicionY = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidadX = velocidadX;
    }

    public int moverDerecha() {
        int nuevaX = posicionX + velocidadX;
        if (areaJuego.estaDentroHorizontal(nuevaX, ancho)) {
            posicionX = nuevaX;
        }
        else
        	velocidadX *= -1;
        return posicionX;
    }

    public int moverIzquierda() {
        int nuevaX = posicionX - velocidadX;
        if (areaJuego.estaDentroHorizontal(nuevaX, ancho)) {
            posicionX = nuevaX;
        }
        else
        	velocidadX *= -1;
        return posicionX;
    }
    
    public MovimientoView toView() {
    	return new MovimientoView(posicionX, posicionY, ancho, alto);
    }
}
