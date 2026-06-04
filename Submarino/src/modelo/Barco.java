package modelo;

import java.util.ArrayList;
import java.util.List;

import views.BarcoView;
import views.CargaView;
import views.SubmarinoView;

// Barco enemigo que cruza la pantalla lanzando cargas de profundidad.
public class Barco {

    private boolean activo;
    private double velocidad;
    private double cooldownDisparo;
    private Punto posicion;
    private String direccion;
    private List<CargaProfundidad> cargas;
    private double velocidadCarga;

    /**
     * @param inicio     posición inicial (fuera del borde según dirección)
     * @param velocidad  píxeles por ciclo que avanza
     * @param direccion  "derecha" (izq→der) o "izquierda" (der→izq)
     * @param velocidadCarga  velocidad de las cargas de profundidad
     */
    public Barco(Punto inicio, double velocidad, String direccion, double velocidadCarga) {
        this.posicion = inicio;
        this.velocidad = velocidad;
        this.activo = true;
        this.cooldownDisparo = 0;
        this.direccion = direccion;
        this.velocidadCarga = velocidadCarga;
        this.cargas = new ArrayList<>();
    }

    /** Avanza el barco en su dirección a su propia velocidad. */
    public void avanzar() {
        if (direccion.equals("derecha")) {
            posicion.mover(velocidad, 0);
        } else {
            posicion.mover(-velocidad, 0);
        }
    }

    public void actualizar() {
        if (cooldownDisparo > 0) {
            cooldownDisparo--;
        }
        // Se desactiva al salir por el lado contrario al que entró
        boolean salio = direccion.equals("derecha")
                ? posicion.getX() > 900
                : posicion.getX() < -80;
        if (salio) {
            activo = false;
        }
    }

    public boolean puedeLanzar() {
        return cooldownDisparo <= 0;
    }

    // Cooldown de 120 ciclos entre disparos (~3,6 s a 30 fps).
    public CargaProfundidad lanzarCarga() {
        CargaProfundidad carga = new CargaProfundidad(
            new Punto(posicion.getX() + 40, posicion.getY() + 30), velocidadCarga
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

    public String getDireccion() {
        return direccion;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public List<CargaProfundidad> getCargas() {
        return cargas;
    }
    
    public BarcoView toView() {
        List<CargaView> cargasView = new ArrayList<>();
        for (CargaProfundidad carga : cargas) {
            cargasView.add(carga.toView());
        }
        return new BarcoView(posicion.getX(), posicion.getY(), cargasView);
    }
}
