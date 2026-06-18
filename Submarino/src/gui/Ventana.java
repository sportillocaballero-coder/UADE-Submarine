package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import controlador.ControladorJuego;
import views.BarcoView;
import views.CargaView;
import views.JugadorView;
import views.SubmarinoView;

// Ventana principal del juego. Captura teclas y ejecuta el game-loop a 30 ms
public class Ventana extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int ANCHO = 900;
    private static final int ALTO = 600;

    private ControladorJuego controlador;
    private JButton btnReiniciar;

    public Ventana(String nombreJugador) {
        controlador = new ControladorJuego();
        controlador.iniciarJuego(nombreJugador);

        PanelJuego panel = new PanelJuego();
        btnReiniciar = new JButton("Empezar de nuevo");
        btnReiniciar.setEnabled(false);
        btnReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.reiniciarJuego();
                btnReiniciar.setEnabled(false);
                panel.repaint();
            }
        });

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.add(btnReiniciar, BorderLayout.SOUTH);
        this.setSize(ANCHO, ALTO);
        this.setTitle("Submarino - " + nombreJugador);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Tecla P: pausar / reanudar independientemente del estado actual
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    if (controlador.getJuego().isActivo()) controlador.pausar();
                    else controlador.reanudar();
                    return;
                }

                if (!controlador.getJugadorView().isJuegoActivo()) return;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:  controlador.procesarEntrada("izquierda", 8); break;
                    case KeyEvent.VK_RIGHT: controlador.procesarEntrada("derecha", 8);   break;
                    case KeyEvent.VK_UP:    controlador.procesarEntrada("arriba", 8);    break;
                    case KeyEvent.VK_DOWN:  controlador.procesarEntrada("abajo", 8);     break;
                }
            }
        });
        // ms
        Timer gameLoop = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean juegoActivo = controlador.getJugadorView().isJuegoActivo();
                boolean jugadorVivo = controlador.getJuego().getJugadorActual().estaVivo();
                btnReiniciar.setEnabled(!juegoActivo && !jugadorVivo);

                if (!juegoActivo) {
                    panel.repaint();
                    return;
                }
                controlador.ejecutarCiclo();
                panel.repaint();
            }
        });
        
        gameLoop.start();

        this.setVisible(true);
    }

    //Esto dibuja todos los elementos del juego
    class PanelJuego extends JPanel {

        private static final long serialVersionUID = 1L;

        @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Fondo agua
    g.setColor(new Color(0, 80, 160));
    g.fillRect(0, 80, ANCHO, ALTO - 80);

    // Cielo
    g.setColor(new Color(135, 206, 235));
    g.fillRect(0, 0, ANCHO, 80);

    // Linea del mar
    g.setColor(new Color(0, 120, 200));
    g.fillRect(0, 75, ANCHO, 10);

    // Barcos y cargas usando Views
    List<BarcoView> barcos = controlador.getBarcosView();
    for (BarcoView b : barcos) {
        int bx = (int) b.getX();
        int by = (int) b.getY();

        g.setColor(new Color(60, 130, 60));
        int[] xPuntos = { bx, bx + 80, bx + 70, bx + 10 };
        int[] yPuntos = { by + 30, by + 30, by, by };
        g.fillPolygon(xPuntos, yPuntos, 4);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 11));
        g.drawString("BARCO", bx + 15, by + 22);

        // Cargas usando CargaView
        for (CargaView c : b.getCargas()) {
            int cx = (int) c.getX();
            int cy = (int) c.getY();
            if (!c.isExplotada()) {
                g.setColor(new Color(200, 50, 50));
                g.fillOval(cx - 8, cy - 8, 16, 16);
                g.setColor(Color.YELLOW);
                g.drawOval(cx - 8, cy - 8, 16, 16);
            } else if (c.isExplotando()) {
                int t = c.getTiempoExplosion();
                int r = (26 - t) * 3;
                int alpha = Math.min(255, t * 10);
                g.setColor(new Color(255, 120, 0, alpha));
                g.fillOval(cx - r, cy - r, r * 2, r * 2);
                g.setColor(new Color(255, 255, 0, alpha));
                g.drawOval(cx - r, cy - r, r * 2, r * 2);
                int rc = Math.max(1, r / 3);
                g.setColor(new Color(255, 255, 255, alpha));
                g.fillOval(cx - rc, cy - rc, rc * 2, rc * 2);
            }
        }
    }

    // Submarino usando SubmarinoView
    SubmarinoView sub = controlador.getSubmarinoView();
    int sx = (int) sub.getX();
    int sy = (int) sub.getY();
    g.setColor(new Color(30, 30, 200));
    g.fillRoundRect(sx, sy, 90, 28, 20, 20);
    g.setColor(new Color(100, 100, 255));
    g.fillRect(sx + 60, sy - 10, 10, 10);
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 11));
    g.drawString("SUB", sx + 32, sy + 18);

    // Barra de vida
    int vida = sub.getVida();
    g.setColor(Color.DARK_GRAY);
    g.fillRect(sx, sy + 32, 90, 6);
    g.setColor(vida > 50 ? Color.GREEN : Color.RED);
    g.fillRect(sx, sy + 32, vida * 90 / 100, 6);

    // HUD usando JugadorView
    JugadorView jugador = controlador.getJugadorView();
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 15));
    g.drawString("Vidas: " + jugador.getVidas(), ANCHO - 130, 25);
    g.drawString("Puntaje: " + jugador.getPuntaje(), ANCHO - 130, 45);
    g.drawString("Nivel: " + jugador.getNivel(), ANCHO - 130, 65);

    // Pausa / Game over
    if (!jugador.isJuegoActivo()) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, ANCHO, ALTO);
        boolean jugadorVivo = controlador.getJuego().getJugadorActual().estaVivo();
        if (!jugadorVivo) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", ANCHO / 2 - 140, ALTO / 2);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Usar boton de abajo que dice \"Empezar de nuevo\"", ANCHO / 2 - 275, ALTO / 2 + 50);
        } else {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("PAUSADO", ANCHO / 2 - 110, ALTO / 2);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Presioná P para reanudar", ANCHO / 2 - 120, ALTO / 2 + 50);
        }
    }
}
    }
}
