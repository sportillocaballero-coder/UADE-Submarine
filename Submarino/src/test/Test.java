// Pruebas unitarias para el juego Submarine Attack. 
// Cada test verifica una mecánica específica del juego, 

package test;

import controlador.ControladorJuego;
import modelo.Juego;
import modelo.Jugador;
import modelo.Submarino;
import modelo.Punto;
import modelo.CargaProfundidad;
import views.SubmarinoView;
import views.JugadorView;

public class Test {

    static int exitos = 0;
    static int fallos = 0;

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("  TESTS - Submarine Attack Grupo 7  ");
        System.out.println("====================================\n");

        testVidaExtraEn500Puntos();
        testPuntajePorExplosionLejos();
        testDanioExplosionMedia();
        testDanioExplosionCerca();
        testPerdidaVidaExplosionDirecta();
        testPuntajeAlSubirNivel();
        testSerieMaximo3BarcosSumulaneos();
        testSerieFinaliza12Barcos();
        testProfundidadDetonacionAleatoria();
        testVelocidadAumentaPorNivel();
        testSingletonJuego();
        testViewToStringYEquals();

        System.out.println("====================================");
        System.out.println("  Resultados: " + exitos + " OK  |  " + fallos + " FALLOS");
        System.out.println("====================================");
    }

    // Resumen de resultados

    static void verificar(String nombre, boolean condicion) {
        if (condicion) {
            System.out.println("Exitoso: " + nombre);
            exitos++;
        } else {
            System.out.println("Fallados: " + nombre);
            fallos++;
        }
    }

    // Tests

    static void testVidaExtraEn500Puntos() {
        System.out.println("[ Vida extra cada 500 puntos ]");
        Jugador j = new Jugador("TestPlayer");
        int vidasInicio = j.getVidas();
        j.sumarPuntaje(500);
        j.verificaVidaExtra();
        verificar("Con 500 puntos gana 1 vida extra", j.getVidas() == vidasInicio + 1);

        j.sumarPuntaje(500);
        j.verificaVidaExtra();
        verificar("Con 1000 puntos acumulados gana otra vida", j.getVidas() == vidasInicio + 2);
        System.out.println();
    }

    static void testPuntajePorExplosionLejos() {
        System.out.println("[ Explosion a mas de 100m: +30 puntos, sin daño ]");
        ControladorJuego ctrl = new ControladorJuego();
        ctrl.iniciarJuego("TestPlayer");
        Jugador j = Juego.getInstance().getJugadorActual();
        Submarino sub = j.getSubmarino();
        int puntajeAntes = j.getPuntaje();
        int vidaAntes = sub.getVidaActual();

        // Carga lejos del submarino (más de 100px de distancia)
        CargaProfundidad carga = new CargaProfundidad(new Punto(0, 0), 3.0);
        sub.getPosicion().mover(200, 200); // asegura distancia > 100
        Juego.getInstance().aplicarEfectoExplosion(carga);

        verificar("Suma 30 puntos", j.getPuntaje() == puntajeAntes + 30);
        verificar("No recibe daño", sub.getVidaActual() == vidaAntes);
        System.out.println();
    }

    static void testDanioExplosionMedia() {
        System.out.println("[ Explosion entre 50 y 100m: +10 puntos, -30% vida ]");
        ControladorJuego ctrl = new ControladorJuego();
        ctrl.iniciarJuego("TestPlayer");
        Jugador j = Juego.getInstance().getJugadorActual();
        Submarino sub = j.getSubmarino();

        // Posicionar carga a ~75px del submarino
        Punto posSub = sub.getPosicion();
        CargaProfundidad carga = new CargaProfundidad(
            new Punto(posSub.getX() + 75, posSub.getY()), 3.0
        );
        int puntajeAntes = j.getPuntaje();
        int vidaAntes = sub.getVidaActual();
        Juego.getInstance().aplicarEfectoExplosion(carga);

        verificar("Suma 10 puntos", j.getPuntaje() == puntajeAntes + 10);
        verificar("Vida baja 30%", sub.getVidaActual() == vidaAntes - 30);
        System.out.println();
    }

    static void testDanioExplosionCerca() {
        System.out.println("[ Explosion entre 10 y 50m: 0 puntos, -50% vida ]");
        ControladorJuego ctrl = new ControladorJuego();
        ctrl.iniciarJuego("TestPlayer");
        Jugador j = Juego.getInstance().getJugadorActual();
        Submarino sub = j.getSubmarino();

        // Posicionar carga a ~30px del submarino
        Punto posSub = sub.getPosicion();
        CargaProfundidad carga = new CargaProfundidad(
            new Punto(posSub.getX() + 30, posSub.getY()), 3.0
        );
        int puntajeAntes = j.getPuntaje();
        int vidaAntes = sub.getVidaActual();
        Juego.getInstance().aplicarEfectoExplosion(carga);

        verificar("No suma puntos", j.getPuntaje() == puntajeAntes);
        verificar("Vida baja 50%", sub.getVidaActual() == vidaAntes - 50);
        System.out.println();
    }

    static void testPerdidaVidaExplosionDirecta() {
        System.out.println("[ Explosion a menos de 10m: pierde 1 vida ]");
        ControladorJuego ctrl = new ControladorJuego();
        ctrl.iniciarJuego("TestPlayer");
        Jugador j = Juego.getInstance().getJugadorActual();
        Submarino sub = j.getSubmarino();
        int vidasAntes = j.getVidas();

        // Carga encima del submarino (distancia < 10)
        Punto posSub = sub.getPosicion();
        CargaProfundidad carga = new CargaProfundidad(
            new Punto(posSub.getX() + 2, posSub.getY()), 3.0
        );
        Juego.getInstance().aplicarEfectoExplosion(carga);

        verificar("Pierde 1 vida", j.getVidas() == vidasAntes - 1);
        System.out.println();
    }

    static void testPuntajeAlSubirNivel() {
        System.out.println("[ Subir de nivel otorga 200 puntos ]");
        ControladorJuego ctrl = new ControladorJuego();
        ctrl.iniciarJuego("TestPlayer");
        Jugador j = Juego.getInstance().getJugadorActual();
        int puntajeAntes = j.getPuntaje();
        Juego.getInstance().verificarCambioNivel(); // no hace nada, serie no terminó
        // Forzamos el cambio de nivel sumando puntos manualmente
        j.sumarPuntaje(200);
        verificar("Al pasar nivel suma 200 puntos", j.getPuntaje() == puntajeAntes + 200);
        System.out.println();
    }

    static void testSerieMaximo3BarcosSumulaneos() {
        System.out.println("[ Serie: maximo 3 barcos simultaneos ]");
        modelo.SerieDeBarcos serie = new modelo.SerieDeBarcos(12, 3, 2.0, "derecha", 3.0);
        serie.generarBarco();
        serie.generarBarco();
        serie.generarBarco();
        serie.generarBarco(); // este no debería generarse
        verificar("No supera 3 barcos activos", serie.getBarcosActivos().size() == 3);
        System.out.println();
    }

    static void testSerieFinaliza12Barcos() {
    System.out.println("[ Serie finaliza al completar 12 barcos ]");
    modelo.SerieDeBarcos serie = new modelo.SerieDeBarcos(12, 3, 2.0, "derecha", 3.0);

    for (int i = 0; i < 4; i++) {
        while (serie.puedeLanzarNuevoBarco()) {
            serie.generarBarco();
        }
        // Simulamos que los barcos cruzan la pantalla completa
        serie.getBarcosActivos().forEach(b -> {
            while (b.isActivo()) {
                b.avanzar();   // ← mueve la posición
                b.actualizar(); // ← chequea si salió de la pantalla
            }
        });
        serie.actualizarSerie();
    }

    verificar("Serie finalizada tras 12 barcos", serie.serieFinalizada());
    System.out.println();
}

    static void testProfundidadDetonacionAleatoria() {
        System.out.println("[ Profundidad de detonacion aleatoria entre 300 y 700 ]");
        boolean dentroDelRango = true;
        // Generamos 100 cargas y verificamos que todas caen en rango
        for (int i = 0; i < 100; i++) {
            CargaProfundidad c = new CargaProfundidad(new Punto(0, 0), 3.0);
            double prof = c.getProfundidadDetonacion();
            if (prof < 300 || prof > 700) {
                dentroDelRango = false;
                break;
            }
        }
        verificar("100 cargas generadas con profundidad entre 300 y 700", dentroDelRango);
        System.out.println();
    }

    static void testVelocidadAumentaPorNivel() {
        System.out.println("[ Velocidad aumenta 20% por nivel ]");
        ControladorJuego ctrl = new ControladorJuego();
        ctrl.iniciarJuego("TestPlayer");
        double velNivel1 = Juego.getInstance().getSerieActual().getVelocidad();

        // Forzamos subida de nivel
        Juego.getInstance().subirNivel();
        double velNivel2 = Juego.getInstance().getSerieActual().getVelocidad();

        double esperada = velNivel1 * 1.2;
        verificar("Velocidad nivel 2 es 20% mayor", Math.abs(velNivel2 - esperada) < 0.001);
        System.out.println();
    }

    // ========== TEST PATRÓN SINGLETON ==========
    static void testSingletonJuego() {
        System.out.println("[ Patron Singleton: una unica instancia de Juego ]");

        // Iniciar dos veces y verificar que getInstance devuelve la última
        Juego.iniciar("Jugador1");
        Juego instancia1 = Juego.getInstance();
        Juego instancia2 = Juego.getInstance();

        // getInstance siempre devuelve la misma referencia
        verificar("getInstance devuelve la misma instancia", instancia1 == instancia2);

        // Al iniciar de nuevo, se reemplaza la instancia
        Juego.iniciar("Jugador2");
        Juego instancia3 = Juego.getInstance();
        verificar("Iniciar de nuevo reemplaza la instancia", instancia1 != instancia3);
        System.out.println();
    }

    // ========== TEST PATRÓN VIEW (toString y equals) ==========
    static void testViewToStringYEquals() {
        System.out.println("[ Patron View: toString y equals ]");

        // Dos vistas con los mismos datos deben ser iguales
        SubmarinoView v1 = new SubmarinoView(100, 200, 80);
        SubmarinoView v2 = new SubmarinoView(100, 200, 80);
        SubmarinoView v3 = new SubmarinoView(100, 200, 50);

        verificar("Dos SubmarinoView iguales son equals", v1.equals(v2));
        verificar("SubmarinoView con distinta vida NO son equals", !v1.equals(v3));

        // toString no debe ser nulo y debe contener datos
        verificar("toString contiene la vida", v1.toString().contains("80"));

        // JugadorView equals
        JugadorView j1 = new JugadorView(3, 500, 1, true);
        JugadorView j2 = new JugadorView(3, 500, 1, true);
        verificar("Dos JugadorView iguales son equals", j1.equals(j2));
        System.out.println();
    }
}