package Carta;

import EfectosEspeciales.EfectoEspecial;
import EfectosEspeciales.TipoEfecto;

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
        return crearMazoEstandar(0.15);
    }

    // Método para crear un mazo estándar con probabilidad configurable de efectos especiales
    public static Mazo crearMazoEstandar(double probabilidadEspeciales) {
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

        // 4. Asignar efectos especiales a las primeras N cartas según la probabilidad
        TipoEfecto[] efectos = TipoEfecto.values();
        for (int i = 0; i < numCartasEspeciales && i < mazoCompleto.size(); i++) {
            // Seleccionar un efecto aleatorio
            TipoEfecto efectoAleatorio = efectos[(int) (Math.random() * efectos.length)];
            mazoCompleto.get(i).setEfecto(EfectoEspecial.crear(efectoAleatorio));
        }

        // 5. Volver a barajar todo el mazo para que las cartas especiales queden distribuidas
        java.util.Collections.shuffle(mazoCompleto);

        return new Mazo(mazoCompleto);
    }
}
