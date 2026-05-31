package modelo;

public class Jugador {

    private String nombreClave;
    private int puntaje;
    private int vidas;
    private Submarino sub;

    public Jugador(String nombre) {
        this.nombreClave = nombre;
        this.puntaje = 0;
        this.vidas = 3;
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

    public void verificaVidaExtra() {
        if (puntaje > 0 && puntaje % 1000 == 0) {
            vidas++;
        }
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
}
