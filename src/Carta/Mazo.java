package Carta;

import EfectosEspeciales.EfectoEspecial;
import EfectosEspeciales.TipoEfecto;
import Exceptions.MazoVacioException;

import java.util.List;

public class Mazo {
    private final List<Carta> cartas;
    private final List<Carta> usadas;

    public Mazo(List<Carta> cartas) {
        this.cartas = cartas;
        this.usadas = new java.util.ArrayList<>();
    }

    public Mazo() {
        this.cartas = new java.util.ArrayList<>();
        this.usadas = new java.util.ArrayList<>();
    }

    public Carta robarCarta() {

        if (cartas.isEmpty()) {
            if (usadas.isEmpty()) {
                throw new MazoVacioException("No hay cartas disponibles en el mazo ni en las usadas.");
            }
            cartas.addAll(usadas);
            usadas.clear();
            java.util.Collections.shuffle(cartas);
        }
        Carta cartaRobada = cartas.removeFirst();
        usadas.add(cartaRobada);
        return cartaRobada;
    }

    public void mezclar() {
        java.util.Collections.shuffle(cartas);
    }

    public List<Carta> verProximasCartas(int n) {
        return cartas.subList(0, Math.min(n, cartas.size()));
    }

    // Método para crear un mazo estándar con distribución fija de efectos (15% por defecto)
    public static Mazo crearMazoEstandar() {
        return crearMazoEstandar(0.15, true);
    }

    // Método para crear un mazo estándar con probabilidad configurable de efectos especiales
    public static Mazo crearMazoEstandar(double probabilidadEspeciales) {
        return crearMazoEstandar(probabilidadEspeciales, true);
    }

    /**
     * Método para crear un mazo estándar con probabilidad configurable de efectos especiales
     * y filtrado por modo de juego (1v1 o 2v2)
     * 
     * @param probabilidadEspeciales Probabilidad de que una carta tenga efecto especial (0.0 a 1.0)
     * @param esModo2v2 true si es modo 2v2 (todos los efectos), false si es modo 1v1 (solo efectos que no requieren segundo jugador)
     * @return Mazo configurado según los parámetros
     */
    public static Mazo crearMazoEstandar(double probabilidadEspeciales, boolean esModo2v2) {
        List<Carta> mazoCompleto = new java.util.ArrayList<>();

        // 1. Crear un mazo de póker estándar de 52 cartas
        for (Palo palo : Palo.values()) {
            for (int valor = 1; valor <= 13; valor++) {
                mazoCompleto.add(new Carta(valor, palo));
            }
        }

        // 2. Calcular cuántas cartas especiales debe haber según la probabilidad
        int numCartasEspeciales = (int) Math.round(mazoCompleto.size() * probabilidadEspeciales);
        
        // 3. Barajar el mazo para que las cartas a las que se les asignará efecto sean aleatorias
        java.util.Collections.shuffle(mazoCompleto);

        // 4. Seleccionar los efectos disponibles según el modo de juego
        TipoEfecto[] efectosDisponibles = esModo2v2 ? TipoEfecto.getEfectos2v2() : TipoEfecto.getEfectos1v1();

        // 5. Asignar efectos especiales a las primeras N cartas según la probabilidad
        for (int i = 0; i < numCartasEspeciales && i < mazoCompleto.size(); i++) {
            // Seleccionar un efecto aleatorio de los disponibles para este modo de juego
            TipoEfecto efectoAleatorio = efectosDisponibles[(int) (Math.random() * efectosDisponibles.length)];
            mazoCompleto.get(i).setEfecto(EfectoEspecial.crear(efectoAleatorio));
        }

        // 6. Volver a barajar todo el mazo para que las cartas especiales queden distribuidas
        java.util.Collections.shuffle(mazoCompleto);

        return new Mazo(mazoCompleto);
    }
}
