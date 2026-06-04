package modelo;

import views.JugadorView;

// Jugador humano: nombre, puntaje, vidas y su submarino.
public class Jugador {

    private String nombreClave;
    private int puntaje;
    private int vidas;
    private Submarino sub;
    // Último umbral de 500 puntos por el que ya se otorgó una vida extra.
    private int puntajeUltimaVida;

    // Empieza con 0 puntos y 3 vidas.
    public Jugador(String nombre) {
        this.nombreClave = nombre;
        this.puntaje = 0;
        this.vidas = 3;
        this.puntajeUltimaVida = 0;
    }

    public String getNombreClave() {
        return nombreClave;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getVidas() {
        return vidas;
    }

    public void setSubmarino(Submarino sub) {
        this.sub = sub;
    }

    /**
     * Otorga una vida extra por cada 500 puntos alcanzados.
     * Usa un umbral acumulativo para no perderse saltos de puntuación.
     * Devuelve true si se otorgó al menos una vida.
     */
    public boolean verificaVidaExtra() {
        boolean gano = false;
        while (puntaje >= puntajeUltimaVida + 500) {
            puntajeUltimaVida += 500;
            vidas++;
            gano = true;
        }
        return gano;
    }

    public void perderVida() {
        vidas--;
    }

    public boolean estaVivo() {
        return vidas > 0;
    }

    public Submarino getSubmarino() {
        return sub;
    }

    public void sumarPuntaje(int puntos) {
        puntaje += puntos;
    }

    public JugadorView toView(int nivel,boolean juegoActivo) {
        return new JugadorView(vidas, puntaje, nivel, juegoActivo);
    }
}
