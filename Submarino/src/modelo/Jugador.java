package modelo;

import views.JugadorView;


//Responsable del submarino
public class Jugador {

    private String nombreClave;
    private int puntaje;
    private int vidas;
    private Submarino sub;
    // Último umbral de 500 puntos por el que ya se otorgó una vida extra.
    private int puntajeUltimaVida;

    // Empieza con 0 puntos y vidas iniciales configuradas.
    public Jugador(String nombre) {
        this.nombreClave = nombre;
        this.puntaje = 0;
        this.vidas = Juego.VIDAS_INICIALES;
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

    //Esto tiene la responsabilidad de mover el submarino
    public void moverSubmarino(String accion, double valor) {
        switch (accion) {
            case "izquierda": sub.mover(-valor, 0); break;
            case "derecha":   sub.mover(valor, 0);  break;
            case "arriba":    sub.mover(0, -valor);  break;
            case "abajo":     sub.mover(0, valor);   break;
        }
    }

    // Corrige si el submarino salió del área de agua (delegado por Juego)
    public void aplicarLimitesSubmarino(double xMin, double xMax, double yMin, double yMax) {
        Punto pos = sub.getPosicion();
        double cx = Math.max(xMin, Math.min(pos.getX(), xMax)) - pos.getX();
        double cy = Math.max(yMin, Math.min(pos.getY(), yMax)) - pos.getY();
        if (cx != 0 || cy != 0) {
            sub.mover(cx, cy);
        }
    }

    // Reinicia el submarino (delegado desde Juego cuando muere)
    public void reiniciarSubmarino(Punto nuevaPosicion) {
        sub.reiniciar(nuevaPosicion);
    }

    //recibe daño
    public void recibirDanoEnSubmarino(int danio) {
        sub.recibirDanio(danio);
    }


    public boolean isSubmarinoActivo() {
        return sub.estaActivo();
    }

    /**
     * Otorga una vida extra por cada 500 puntos alcanzados.
     * Usa un umbral acumulativo para no perderse saltos de puntuación.
     * Devuelve true si se otorgó al menos una vida.
     */
    public boolean verificaVidaExtra() {
        boolean gano = false;
        while (puntaje >= puntajeUltimaVida + Juego.PUNTOS_PARA_VIDA_EXTRA) {
            puntajeUltimaVida += Juego.PUNTOS_PARA_VIDA_EXTRA;
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