package views;

public class JugadorView {

    private int vidas;
    private int puntaje;
    private int nivel;
    private boolean juegoActivo;

    public JugadorView() {}

    public JugadorView(int vidas, int puntaje, int nivel, boolean juegoActivo) {
        this.vidas = vidas;
        this.puntaje = puntaje;
        this.nivel = nivel;
        this.juegoActivo = juegoActivo;
    }

    public int getVidas() { return vidas; }
    public void setVidas(int vidas) { this.vidas = vidas; }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public boolean isJuegoActivo() { return juegoActivo; }
    public void setJuegoActivo(boolean juegoActivo) { this.juegoActivo = juegoActivo; }
}