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

    // Método para crear un mazo estándar con distribución fija de efectos
    public static Mazo crearMazoEstandar() {
        List<Carta> mazoCompleto = new java.util.ArrayList<>();

        // 1. Crear un mazo de póker estándar de 52 cartas
        for (Palo palo : Palo.values()) {
            for (int valor = 1; valor <= 13; valor++) {
                mazoCompleto.add(new Carta(valor, palo));
            }
        }

        // 2. Barajar el mazo para que las cartas a las que se les asignará efecto sean aleatorias
        java.util.Collections.shuffle(mazoCompleto);

        // 3. Asignar los 14 efectos especiales a las primeras 14 cartas
        TipoEfecto[] efectos = TipoEfecto.values();
        int cartaIndex = 0;
        for (TipoEfecto tipoEfecto : efectos) {
            // Asignar 2 de cada efecto
            for (int i = 0; i < 2; i++) {
                if (cartaIndex < mazoCompleto.size()) {
                    mazoCompleto.get(cartaIndex).setEfecto(EfectoEspecial.crear(tipoEfecto));
                    cartaIndex++;
                }
            }
        }

        // 4. Volver a barajar todo el mazo para que las cartas especiales queden distribuidas
        java.util.Collections.shuffle(mazoCompleto);

        return new Mazo(mazoCompleto);
    }
}
