package controlador;

import modelo.Barco;
import modelo.CargaProfundidad;
import modelo.Juego;
import modelo.SerieDeBarcos;
import modelo.Submarino;

// Controlador del juego: coordina barcos, cargas, colisiones y niveles.
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

    // Un ciclo del juego: mueve barcos, cae cargas, chequea colisiones y nivel.
    public void ejecutarCiclo() {
        ciclos++;

        SerieDeBarcos serie = juego.getSerieActual();

        if (ciclos % 80 == 0 && serie.puedeLanzarNuevoBarco()) {
            serie.generarBarco();
        }

        for (Barco b : serie.getBarcosActivos()) {
            b.mover(2, 0);
            b.actualizar();
            if (b.puedeLanzar()) {
                b.lanzarCarga();
            }
            for (CargaProfundidad c : b.getCargas()) {
                if (!c.estaExplotada()) {
                    c.caer();
                    c.explotar();
                }
            }
        }

        juego.actualizarJuego();
        verificarColisiones();
        verificarCambioNivel();
        evaluarFinDeJuego();
    }

    // Si una carga toca el sub (< 30 px), le hace daño y explota.
    public void verificarColisiones() {
        Submarino sub = juego.getJugadorActual().getSubmarino();
        for (Barco b : juego.getSerieActual().getBarcosActivos()) {
            for (CargaProfundidad c : b.getCargas()) {
                if (!c.estaExplotada() && c.calcularDistancia(sub) < 30) {
                    sub.recibirDanio(34);
                    c.explotar();
                    if (!sub.estaActivo()) {
                        juego.getJugadorActual().perderVida();
                        sub = new modelo.Submarino(new modelo.Punto(400, 350));
                        juego.getJugadorActual().setSubmarino(sub);
                    }
                }
            }
        }
    }

    public void verificarCambioNivel() {
        if (juego.getSerieActual().serieFinalizada()) {
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
