package controlador;

import views.BarcoView;
import views.JugadorView;
import views.SubmarinoView;
import java.util.List;
import java.util.ArrayList;
import modelo.Juego;
import modelo.Barco;

// Controlador del juego: FACHADA que orquesta.
// NO contiene lógica de juego, solo delega.
public class ControladorJuego {

    private Juego juego;

    public void iniciarJuego(String nombre) {
        juego = Juego.iniciar(nombre);
    }

    // Procesa entrada del usuario (teclado/mouse)
    public void procesarEntrada(String accion, double valor) {
        juego.moverSubmarino(accion, valor);
    }

    // Un ciclo completo del juego
    // La lógica está en Juego.ejecutarCiclo()
    public void ejecutarCiclo() {
        juego.ejecutarCiclo();
    }

    // ========== OBTENER JUEGO ==========
    public Juego getJuego() {
        return juego;
    }

    // ========== OBTENER VISTAS (para la GUI) ==========
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