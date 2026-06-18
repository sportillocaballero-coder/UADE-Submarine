package modelo;

import java.util.ArrayList;
import java.util.List;
import views.BarcoView;
import views.CargaView;


// Barco enemigo que cruza la pantalla lanzando cargas de profundidad que son como torpedos.
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
            ? posicion.getX() > Juego.ANCHO_PANTALLA
            : posicion.getX() < -Juego.BORDE_OFFSCREEN;
        if (salio) {
            activo = false;
        }
    }

    public boolean puedeLanzar() {
        return cooldownDisparo <= 0;
    }

    // Cooldown aleatorio entre disparos.
    public CargaProfundidad lanzarCarga() {
        CargaProfundidad carga = new CargaProfundidad(
            new Punto(posicion.getX() + 40, posicion.getY() + 30), velocidadCarga
        );
        cargas.add(carga);
        cooldownDisparo = Juego.COOLDOWN_DISPARO_MIN + Math.random() * (Juego.COOLDOWN_DISPARO_MAX - Juego.COOLDOWN_DISPARO_MIN);
        return carga;
    }

    public boolean estaActivo() {
        return activo;
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
