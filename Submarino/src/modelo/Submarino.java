package modelo;

import views.SubmarinoView;

// Submarino del jugador. Recibe daño de las cargas enemigas.
public class Submarino {

    private int vidaActual;
    private boolean activo;
    private Punto posicion;

    // Comienza con la vida configurada.
    public Submarino(Punto inicio) {
        this.posicion = inicio;
        this.vidaActual = Juego.VIDA_SUBMARINO_INICIAL;
        this.activo = true;
    }

    public void mover(double deltaX, double deltaY) {
        posicion.mover(deltaX, deltaY);
    }

    public void cambiarProfundidad(double metro) {
        posicion.mover(0, metro);
    }

    // Mantiene su propia lógica de daño
    public void recibirDanio(int puntos) {
        vidaActual -= puntos;
        if (vidaActual <= 0) {
            vidaActual = 0;
            activo = false;
        }
    }

    // Reinicia el submarino (delegado desde Jugador cuando muere)
    public void reiniciar(Punto nuevaPosicion) {
        this.vidaActual = Juego.VIDA_SUBMARINO_INICIAL;
        this.activo = true;
        this.posicion = nuevaPosicion;
    }

    public boolean estaActivo() {
        return activo;
    }

    public boolean impactadoPor(CargaProfundidad carga) {
        double cargaY = carga.getPosicion().getY();
        double subY = posicion.getY();
        return cargaY >= subY && (cargaY - carga.getVelocidad()) < subY;
    }

    public Punto getPosicion() {
        return posicion;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public SubmarinoView toView(){
        return new SubmarinoView(posicion.getX(), posicion.getY(), vidaActual);
    
}}