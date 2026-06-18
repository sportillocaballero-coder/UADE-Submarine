package controlador;

import views.BarcoView;
import views.JugadorView;
import views.SubmarinoView;
import java.util.List;
import java.util.ArrayList;
import modelo.Juego;
import modelo.Barco;



public class ControladorJuego {

    private Juego juego;

    public void iniciarJuego(String nombre) {
        juego = Juego.iniciar(nombre);
    }

    // Procesa entrada del usuario (teclado/mouse)
    public void procesarEntrada(String accion, double valor) {
        juego.moverSubmarino(accion, valor);
    }


    // La lógica está en Juego.ejecutarCiclo()
    public void ejecutarCiclo() {
        juego.ejecutarCiclo();
    }


    // Consultas de estado para la GUI (sin exponer el modelo)
    public boolean isJuegoActivo() {
        return juego != null && juego.isActivo();
    }

    public boolean isJugadorVivo() {
        return juego != null && juego.getJugadorActual().estaVivo();
    }

    // control de estado pausar / reanudar / reiniciar
    public void pausar() {
        if (juego != null) juego.pausar();
    }

    public void reanudar() {
        if (juego != null) juego.reanudar();
    }

    public void reiniciarJuego() {
        if (juego == null) return;
        String nombreJugador = juego.getJugadorActual().getNombreClave();
        iniciarJuego(nombreJugador);
    }

    //Esto obtiene las vistas de la gui
    // Solo orquestan llamadas a toView()
    public SubmarinoView getSubmarinoView() {
        return juego.getJugadorActual().getSubmarino().toView();
    }

    public JugadorView getJugadorView() {
        return juego.getJugadorActual().toView(
            juego.getNivelActual(),
            juego.isActivo()
        );
    }

    public List<BarcoView> getBarcosView() {
        List<BarcoView> lista = new ArrayList<>();
        for (Barco b : juego.getSerieActual().getBarcosActivos()) {
            lista.add(b.toView());
        }
        return lista;
    }
}