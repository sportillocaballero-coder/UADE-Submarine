package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import controlador.ControladorJuego;
import modelo.Barco;
import modelo.CargaProfundidad;
import modelo.Jugador;
import modelo.Juego;
import modelo.Submarino;

public class Ventana extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int ANCHO = 900;
    private static final int ALTO = 600;

    private ControladorJuego controlador;

    public Ventana(String nombreJugador) {
        controlador = new ControladorJuego();
        controlador.iniciarJuego(nombreJugador);

        PanelJuego panel = new PanelJuego();
        this.add(panel);
        this.setSize(ANCHO, ALTO);
        this.setTitle("Submarino - " + nombreJugador);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:  controlador.procesarEntrada("izquierda", 8); break;
                    case KeyEvent.VK_RIGHT: controlador.procesarEntrada("derecha", 8);   break;
                    case KeyEvent.VK_UP:    controlador.procesarEntrada("arriba", 8);    break;
                    case KeyEvent.VK_DOWN:  controlador.procesarEntrada("abajo", 8);     break;
                }
            }
        });

        Timer gameLoop = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.ejecutarCiclo();
                panel.repaint();
            }
        });
        gameLoop.start();

        this.setVisible(true);
    }

    class PanelJuego extends JPanel {

        private static final long serialVersionUID = 1L;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Juego juego = controlador.getJuego();
            if (juego == null) return;

            // Fondo agua
            g.setColor(new Color(0, 80, 160));
            g.fillRect(0, 80, ANCHO, ALTO - 80);

            // Cielo
            g.setColor(new Color(135, 206, 235));
            g.fillRect(0, 0, ANCHO, 80);

            // Linea del mar
            g.setColor(new Color(0, 120, 200));
            g.fillRect(0, 75, ANCHO, 10);

            // Barcos y cargas
            List<Barco> barcos = juego.getSerieActual().getBarcosActivos();
            for (Barco b : barcos) {
                int bx = (int) b.getPosicion().getX();
                int by = (int) b.getPosicion().getY();

                g.setColor(new Color(60, 130, 60));
                int[] xPuntos = { bx, bx + 80, bx + 70, bx + 10 };
                int[] yPuntos = { by + 30, by + 30, by, by };
                g.fillPolygon(xPuntos, yPuntos, 4);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 11));
                g.drawString("BARCO", bx + 15, by + 22);

                // Cargas de profundidad
                for (CargaProfundidad c : b.getCargas()) {
                    if (!c.estaExplotada()) {
                        int cx = (int) c.getPosicion().getX();
                        int cy = (int) c.getPosicion().getY();
                        g.setColor(new Color(200, 50, 50));
                        g.fillOval(cx - 8, cy - 8, 16, 16);
                        g.setColor(Color.YELLOW);
                        g.drawOval(cx - 8, cy - 8, 16, 16);
                    }
                }
            }

            // Submarino
            Submarino sub = juego.getJugadorActual().getSubmarino();
            int sx = (int) sub.getPosicion().getX();
            int sy = (int) sub.getPosicion().getY();
            g.setColor(new Color(30, 30, 200));
            g.fillRoundRect(sx, sy, 90, 28, 20, 20);
            g.setColor(new Color(100, 100, 255));
            g.fillRect(sx + 60, sy - 10, 10, 10);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 11));
            g.drawString("SUB", sx + 32, sy + 18);

            // Barra de vida del submarino
            int vida = sub.getVidaActual();
            g.setColor(Color.DARK_GRAY);
            g.fillRect(sx, sy + 32, 90, 6);
            g.setColor(vida > 50 ? Color.GREEN : Color.RED);
            g.fillRect(sx, sy + 32, vida * 90 / 100, 6);

            // HUD
            Jugador jugador = juego.getJugadorActual();
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString("Vidas: " + jugador.getVidas(), ANCHO - 130, 25);
            g.drawString("Puntaje: " + jugador.getPuntaje(), ANCHO - 130, 45);
            g.drawString("Nivel: " + juego.getNivelActual(), ANCHO - 130, 65);

            // Game over
            if (!juego.isActivo()) {
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, ANCHO, ALTO);
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 48));
                g.drawString("GAME OVER", ANCHO / 2 - 140, ALTO / 2);
            }
        }
    }
}
