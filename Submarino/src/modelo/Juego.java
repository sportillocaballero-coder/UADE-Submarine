package modelo;

// Estado de la partida: nivel, jugador y serie de barcos actual.
public class Juego {

    private int nivelActual;
    private int mejorPuntaje;
    private boolean activo;
    private Jugador jugadorActual;
    private SerieDeBarcos serieActual;

    // Límites del área jugable: pantalla 900×600, agua desde y=85.
    public static final double X_MIN = 0;
    public static final double X_MAX = 810;  // 900 - ancho del submarino (90)
    public static final double Y_MIN = 85;   // justo bajo la línea del mar
    public static final double Y_MAX = 562;  // 600 - alto del submarino + barra (38)

    public Juego(String nombreJugador) {
        this.nivelActual = 1;
        this.mejorPuntaje = 0;
        this.activo = true;
        this.jugadorActual = new Jugador(nombreJugador);
        jugadorActual.setSubmarino(new Submarino(new Punto(400, 350)));
        this.serieActual = new SerieDeBarcos(12, 3, calcularVelocidad(), generarDireccion());
    }

    public void actualizarJuego() {
        serieActual.actualizarSerie();
    }

    public void subirNivel() {
        nivelActual++;
        serieActual = new SerieDeBarcos(12, 3, calcularVelocidad(), generarDireccion());
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

    // Velocidad base 2 px/ciclo, +20% acumulado por cada nivel.
    private double calcularVelocidad() {
        return 2.0 * Math.pow(1.2, nivelActual - 1);
    }

    // Dirección aleatoria para cada nueva serie.
    private String generarDireccion() {
        return Math.random() < 0.5 ? "derecha" : "izquierda";
    }
}
