package modelo;

import java.util.ArrayList;
import java.util.List;

public class Barco {

    private boolean activo;
    private double velocidad;
    private double cooldownDisparo;
    private Punto posicion;
    private String direccion;
    private List<CargaProfundidad> cargas;

    public Barco(Punto inicio, double velocidad) {
        this.posicion = inicio;
        this.velocidad = velocidad;
        this.activo = true;
        this.cooldownDisparo = 0;
        this.direccion = "derecha";
        this.cargas = new ArrayList<>();
    }

    public void mover(double deltaX, double deltaY) {
        posicion.mover(deltaX, deltaY);
    }

    public void actualizar() {
        if (cooldownDisparo > 0) {
            cooldownDisparo--;
        }
        if (posicion.getX() > 900) {
            activo = false;
        }
    }

    public boolean puedeLanzar() {
        return cooldownDisparo <= 0;
    }

    public CargaProfundidad lanzarCarga() {
        CargaProfundidad carga = new CargaProfundidad(
            new Punto(posicion.getX() + 40, posicion.getY() + 30), 3.0
        );
        cargas.add(carga);
        cooldownDisparo = 120;
        return carga;
    }

    public boolean isActivo() {
        return activo;
    }

    public Punto getPosicion() {
        return posicion;
    }

    public List<CargaProfundidad> getCargas() {
        return cargas;
    }
}
