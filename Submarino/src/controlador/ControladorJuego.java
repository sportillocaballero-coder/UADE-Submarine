package controlador;

import modelo.Barco;
import modelo.CargaProfundidad;
import modelo.Jugador;
import modelo.Juego;
import modelo.Punto;
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
        Submarino sub = juego.getJugadorActual().getSubmarino();
        switch (accion) {
            case "izquierda": sub.mover(-valor, 0); break;
            case "derecha":   sub.mover(valor, 0);  break;
            case "arriba":    sub.mover(0, -valor);  break;
            case "abajo":     sub.mover(0, valor);   break;
        }
        // Corrige si el submarino salió del área de agua
        Punto pos = sub.getPosicion();
        double cx = Math.max(Juego.X_MIN, Math.min(pos.getX(), Juego.X_MAX)) - pos.getX();
        double cy = Math.max(Juego.Y_MIN, Math.min(pos.getY(), Juego.Y_MAX)) - pos.getY();
        if (cx != 0 || cy != 0) {
            sub.mover(cx, cy);
        }
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
                    // Explota solo si acaba de cruzar la profundidad del sub este ciclo
                    double cargaY = c.getPosicion().getY();
                    double subY = sub.getPosicion().getY();
                    boolean alcanzoProfundidad = cargaY >= subY && (cargaY - c.getVelocidad()) < subY;
                    if (alcanzoProfundidad || c.explotar()) {
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

    // El daño y sus umbrales son responsabilidad de CargaProfundidad (Information Expert).
    public void aplicarEfectoExplosion(CargaProfundidad c) {
        Jugador jugador = juego.getJugadorActual();
        Submarino sub = jugador.getSubmarino();
        int danio = c.calcularDanio(sub);

        if (danio == 0) {
            jugador.sumarPuntaje(30);
        } else if (danio > 0) {
            if (danio == 30) jugador.sumarPuntaje(10);
            sub.recibirDanio(danio);
            if (!sub.estaActivo()) {
                jugador.perderVida();
                reiniciarSubmarino();
            }
        } else { // -1: colisión directa, pierde una vida
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
