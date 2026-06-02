package controlador;

import modelo.Barco;
import modelo.CargaProfundidad;
import modelo.Jugador;
import modelo.Juego;
import modelo.SerieDeBarcos;
import modelo.Submarino;

// Controlador del juego: coordina barcos, cargas, explosiones y niveles.
public class ControladorJuego {

    private Juego juego;
    private int ciclos;

    public void iniciarJuego(String nombre) {
        juego = new Juego(nombre);
        ciclos = 0;
    }

    public void procesarEntrada(String accion, double valor) {
        juego.moverSubmarino(accion, valor);
    }

    // Un ciclo del juego: mueve barcos, cae cargas y procesa explosiones.
    public void ejecutarCiclo() {
        ciclos++;

        SerieDeBarcos serie = juego.getSerieActual();

        if (ciclos % 80 == 0 && serie.puedeLanzarNuevoBarco()) {
            serie.generarBarco();
        }

        for (Barco b : serie.getBarcosActivos()) {
            b.avanzar();
            b.actualizar();
            if (b.puedeLanzar()) {
                b.lanzarCarga();
            }
            for (CargaProfundidad c : b.getCargas()) {
                if (!c.estaExplotada()) {
                    c.caer();
                    Submarino sub = juego.getJugadorActual().getSubmarino();
                    // Explota al alcanzar la profundidad del sub o el fondo (y=560)
                    if (c.getPosicion().getY() >= sub.getPosicion().getY() || c.explotar()) {
                        c.forzarExplosion();
                        aplicarEfectoExplosion(c);
                    }
                } else if (c.estaExplotando()) {
                    c.actualizarExplosion(); // avanza la animación frame a frame
                }
            }
        }

        juego.actualizarJuego();
        verificarCambioNivel();
        evaluarFinDeJuego();
    }

    /**
     * Aplica el efecto de una carga que acaba de explotar según su distancia al submarino:
     *  > 100 px  →  +30 puntos, sin daño
     *  50–100 px →  +10 puntos, -30 vida
     *  10–50 px  →   0 puntos, -50 vida
     *  < 10 px   →   0 puntos, pierde una vida
     */
    public void aplicarEfectoExplosion(CargaProfundidad c) {
        Jugador jugador = juego.getJugadorActual();
        Submarino sub = jugador.getSubmarino();
        double dist = c.calcularDistancia(sub);

        if (dist > 100) {
            jugador.sumarPuntaje(30);
        } else if (dist > 50) {
            jugador.sumarPuntaje(10);
            sub.recibirDanio(30);
            if (!sub.estaActivo()) {
                jugador.perderVida();
                reiniciarSubmarino();
            }
        } else if (dist > 10) {
            sub.recibirDanio(50);
            if (!sub.estaActivo()) {
                jugador.perderVida();
                reiniciarSubmarino();
            }
        } else {
            jugador.perderVida();
            reiniciarSubmarino();
        }

        jugador.verificaVidaExtra();
    }

    // Crea un submarino nuevo en la posición inicial.
    private void reiniciarSubmarino() {
        juego.getJugadorActual().setSubmarino(
            new modelo.Submarino(new modelo.Punto(400, 350))
        );
    }

    public void verificarCambioNivel() {
        if (juego.getSerieActual().serieFinalizada()) {
            juego.getJugadorActual().sumarPuntaje(200);
            juego.getJugadorActual().verificaVidaExtra();
            juego.subirNivel();
            ciclos = 0;
        }
    }

    public void evaluarFinDeJuego() {
        if (!juego.getJugadorActual().estaVivo()) {
            juego.cerrarJuego();
        }
    }

    public Juego getJuego() {
        return juego;
    }
}
