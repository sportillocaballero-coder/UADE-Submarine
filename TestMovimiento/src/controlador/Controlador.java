package controlador;

import modelo.Area;
import modelo.MovimientoBidireccional;
import modelo.MovimientoHorizontal;
import views.MovimientoView;

public class Controlador {

	private static Controlador instance;
	private MovimientoBidireccional mb;
	private MovimientoHorizontal mh;
	private Area area;
	
	private Controlador() {
		area = new Area(1000, 900);
		mh = new MovimientoHorizontal(area, 10, 10, 90, 30, 1);
		mb = new MovimientoBidireccional(area, 100, 300, 90, 30, 2, 2);
	}
	
	public static Controlador getInstance() {
		if(instance == null)
			instance = new Controlador();
		return instance;
	}
	
	public void moverDerechaMB() {
		mb.moverDerecha();
	}
	
	public void moverIzquierdaMB() {
		mb.moverIzquierda();
	}
	
	public void moverArribaMB() {
		mb.moverArriba();
	}
	
	public void moverAbajoMB() {
		mb.moverAbajo();
	}
	
	public void moverDerechaMH() {
		mh.moverDerecha();
	}
	
	public void moverIzquierdaMH() {
		mh.moverDerecha();
	}
	
	public int getAnchoArea() {
		return this.area.getAncho();
	}
	
	public int getAltoArea() {
		return this.area.getAlto();
	}
	
	public MovimientoView getBarco() {
		return mh.toView();	
	}
	
	public MovimientoView getSubmarino() {
		return mb.toView();	
	} 

}
