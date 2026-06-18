package test;

import gui.Ventana;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestView {
    public static void main(String[] args) {
        String jugador = "Jugador1";
        System.out.println("INFO: TestView iniciado para jugador: " + jugador);
        Ventana v = new Ventana(jugador);
        v.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("INFO: TestView cerrado — sesión finalizada");
            }
        });
    }
}
