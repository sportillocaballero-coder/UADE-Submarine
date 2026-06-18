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


    public Juego getJuego() {
        return juego;
    }

    // controld e estado pausar / reanudar
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