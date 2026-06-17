package modelo;

// Estado de la partida: nivel, jugador y serie de barcos actual.
// Responsable de la lógica principal del juego.
// Implementa el patrón Singleton: existe una única instancia del juego.
public class Juego {

    // ========== PATRÓN SINGLETON ==========
    private static Juego instancia;

    private int nivelActual;
    private int mejorPuntaje;
    private boolean activo;
    private int ciclos;
    private Jugador jugadorActual;
    private SerieDeBarcos serieActual;

    // Límites del área jugable: pantalla 900×600, agua desde y=85.
    public static final double X_MIN = 0;
    public static final double X_MAX = 810;  // 900 - ancho del submarino (90)
    public static final double Y_MIN = 85;   // justo bajo la línea del mar
    public static final double Y_MAX = 562;  // 600 - alto del submarino + barra (38)

    // Constructor privado: nadie puede crear un Juego desde afuera.
    private Juego(String nombreJugador) {
        this.nivelActual = 1;
        this.mejorPuntaje = 0;
        this.activo = true;
        this.ciclos = 0;
        this.jugadorActual = new Jugador(nombreJugador);
        jugadorActual.setSubmarino(new Submarino(new Punto(400, 350)));
        this.serieActual = new SerieDeBarcos(12, 3, calcularVelocidad(), generarDireccion(), calcularVelocidadCarga());
    }

    /**
     * Crea (o reinicia) la única instancia del juego con un nombre de jugador.
     * Cada nueva partida reemplaza la instancia anterior.
     */
    public static Juego iniciar(String nombreJugador) {
        instancia = new Juego(nombreJugador);
        return instancia;
    }

    /**
     * Devuelve la instancia única del juego.
     * Debe haberse llamado a iniciar() antes.
     */
    public static Juego getInstance() {
        return instancia;
    }

    // ========== LÓGICA PRINCIPAL DEL JUEGO ==========
    public void ejecutarCiclo() {
        ciclos++;

        // Genera nuevos barcos cada 80 ciclos
        if (ciclos % 80 == 0 && serieActual.puedeLanzarNuevoBarco()) {
            serieActual.generarBarco();
        }

        // Procesa todos los barcos y sus cargas
        for (Barco b : serieActual.getBarcosActivos()) {
            b.avanzar();
            b.actualizar();
            
            if (b.puedeLanzar()) {
                b.lanzarCarga();
            }
            
            // Procesa cada carga de profundidad
            for (CargaProfundidad c : b.getCargas()) {
                if (!c.estaExplotada()) {
                    c.caer();
                    Submarino sub = jugadorActual.getSubmarino();

                    double cargaY = c.getPosicion().getY();
                    double subY = sub.getPosicion().getY();
                    boolean alcanzoProfundidad = cargaY >= subY && (cargaY - c.getVelocidad()) < subY;
                    
                    if (alcanzoProfundidad || c.explotar()) {
                        c.forzarExplosion();
                        // Delega efectos de explosión
                        aplicarEfectoExplosion(c);
                    }
                } else if (c.estaExplotando()) {
                    c.actualizarExplosion();
                }
            }
        }

        // Actualiza la serie (elimina barcos inactivos)
        actualizarJuego();
        
        // Verifica cambio de nivel
        verificarCambioNivel();
        
        // Verifica fin de juego
        evaluarFinDeJuego();
    }

    /**
     * Aplica el efecto de una carga que explotó.
     * Delega cálculo de daño a CargaProfundidad y aplicación a Jugador.
     */
    public void aplicarEfectoExplosion(CargaProfundidad c) {
        Jugador jugador = jugadorActual;
        Submarino sub = jugador.getSubmarino();
        
        int danio = c.calcularDanio(sub);

        if (danio == 0) {
            jugador.sumarPuntaje(30);
        } else if (danio > 0) {
            if (danio == 30) jugador.sumarPuntaje(10);
            jugador.recibirDanoEnSubmarino(danio);
            
            if (!jugador.isSubmarinoActivo()) {
                jugador.perderVida();
                reiniciarSubmarino();
            }
        } else {
            jugador.perderVida();
            reiniciarSubmarino();
        }

        jugador.verificaVidaExtra();
    }

    private void reiniciarSubmarino() {
        jugadorActual.reiniciarSubmarino(new Punto(400, 350));
    }

    public void verificarCambioNivel() {
        if (serieActual.serieFinalizada()) {
            jugadorActual.sumarPuntaje(200);
            jugadorActual.verificaVidaExtra();
            subirNivel();
            ciclos = 0;
        }
    }

    private void evaluarFinDeJuego() {
        if (!jugadorActual.estaVivo()) {
            cerrarJuego();
        }
    }

    // ========== MÉTODOS DE ESTADO ==========
    public void actualizarJuego() {
        serieActual.actualizarSerie();
    }

    public void subirNivel() {
        nivelActual++;
        serieActual = new SerieDeBarcos(12, 3, calcularVelocidad(), generarDireccion(), calcularVelocidadCarga());
    }

    public void cerrarJuego() {
        activo = false;
        if (jugadorActual.getPuntaje() > mejorPuntaje) {
            mejorPuntaje = jugadorActual.getPuntaje();
        }
    }

    public void moverSubmarino(String accion, double valor) {
        jugadorActual.moverSubmarino(accion, valor);
        jugadorActual.aplicarLimitesSubmarino(X_MIN, X_MAX, Y_MIN, Y_MAX);
    }

    // ========== GETTERS ==========
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

    // Cálculos privados
    private double calcularVelocidad() {
        return 2.0 * Math.pow(1.2, nivelActual - 1);
    }

    private double calcularVelocidadCarga() {
        return 3.0 * Math.pow(1.2, nivelActual - 1);
    }

    private String generarDireccion() {
        return Math.random() < 0.5 ? "derecha" : "izquierda";
    }
}