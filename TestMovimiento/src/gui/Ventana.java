package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import controlador.Controlador;
import views.MovimientoView;

public class Ventana extends JFrame {

	private static final long serialVersionUID = -5360041225007601188L;

	private JLabel submarino, barco;
	
	public Ventana() {
		configurar();
		this.setVisible(true);
		int ancho = Controlador.getInstance().getAnchoArea();
		int alto = Controlador.getInstance().getAltoArea();
		this.setSize(ancho, alto);
		this.setTitle("Objetos Moviles");
		eventos();
	}
	
	private void configurar() {
		Container c = this.getContentPane();
		c.setLayout(null);
		MovimientoView auxSubmarino = Controlador.getInstance().getSubmarino();
		submarino = new JLabel("Submarino");
		submarino.setHorizontalAlignment(SwingConstants.CENTER);
		submarino.setForeground(Color.WHITE);
		submarino.setBounds(auxSubmarino.getPosicionX(), auxSubmarino.getPosicionY(), auxSubmarino.getAncho(), auxSubmarino.getAlto());
		submarino.setOpaque(true);
		submarino.setBackground(Color.BLUE);
		MovimientoView auxBarco = Controlador.getInstance().getBarco();
		barco = new JLabel("Barco");
		barco.setHorizontalAlignment(SwingConstants.CENTER);
		barco.setBounds(auxBarco.getPosicionX(), auxBarco.getPosicionY(), auxBarco.getAncho(), auxBarco.getAlto());
		barco.setOpaque(true);
		barco.setBackground(Color.GREEN);
		c.add(barco);
		c.add(submarino);
	}
	
	private void eventos() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) { }
			
			@Override
			public void keyReleased(KeyEvent e) { }
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 37)
						Controlador.getInstance().moverIzquierdaMB();
				else
					if(e.getKeyCode() == 38)
						Controlador.getInstance().moverArribaMB();
					else
						if(e.getKeyCode() == 39)
							Controlador.getInstance().moverDerechaMB();
						else
							if(e.getKeyCode() == 40)
								Controlador.getInstance().moverAbajoMB();
				if(e.getKeyCode() >= 37 && e.getKeyCode() <= 40)
					moverBarco();
			}
		});
		//Timer de swing para requerir al juego que ejecute algun proceso a intervalores regulares
		Timer gameLoop = new Timer(10, new MuevoBarco());
		gameLoop.start();
		
	}
	
	
	private void moverBarco() {
		MovimientoView auxSubmarino = Controlador.getInstance().getSubmarino();
		submarino.setBounds(auxSubmarino.getPosicionX(), auxSubmarino.getPosicionY(), auxSubmarino.getAncho(), auxSubmarino.getAlto());
	}
	
	class MuevoBarco implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Controlador.getInstance().moverDerechaMH();
			MovimientoView auxBarco = Controlador.getInstance().getBarco();
			barco.setBounds(auxBarco.getPosicionX(), auxBarco.getPosicionY(), auxBarco.getAncho(), auxBarco.getAlto());
		}
	}
}
