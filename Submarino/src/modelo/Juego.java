package modelo;

// Estado de la partida: nivel, jugador y serie de barcos actual.
public class Juego {

    private int nivelActual;
    private int mejorPuntaje;
    private boolean activo;
    private Jugador jugadorActual;
    private SerieDeBarcos serieActual;

    public Juego(String nombreJugador) {
        this.nivelActual = 1;
        this.mejorPuntaje = 0;
        this.activo = true;
        this.jugadorActual = new Jugador(nombreJugador);
        jugadorActual.setSubmarino(new Submarino(new Punto(400, 350)));
        this.serieActual = new SerieDeBarcos(nivelActual * 3);
    }

    public void actualizarJuego() {
        serieActual.actualizarSerie();
    }

    public void subirNivel() {
        nivelActual++;
        serieActual = new SerieDeBarcos(nivelActual * 3);
    }

    public void cerrarJuego() {
        activo = false;
        if (jugadorActual.getPuntaje() > mejorPuntaje) {
            mejorPuntaje = jugadorActual.getPuntaje();
        }
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public SerieDeBarcos getSerieActual() {
        return serieActual;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public boolean isActivo() {
        return activo;
    }

    public void moverSubmarino(String accion, double valor) {
        Submarino sub = jugadorActual.getSubmarino();
        switch (accion) {
            case "izquierda": sub.mover(-valor, 0); break;
            case "derecha":   sub.mover(valor, 0);  break;
            case "arriba":    sub.mover(0, -valor);  break;
            case "abajo":     sub.mover(0, valor);   break;
        }
    }
}
