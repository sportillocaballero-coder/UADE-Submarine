package modelo;

// Jugador humano: nombre, puntaje, vidas y su submarino.
public class Jugador {

    private String nombreClave;
    private int puntaje;
    private int vidas;
    private Submarino sub;

    // Empieza con 0 puntos y 3 vidas.
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

    // Otorga una vida extra cada 1000 puntos exactos.
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
