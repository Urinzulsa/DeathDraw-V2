package Moneda;

import Jugador.Jugador;
import java.util.Scanner;

public class Moneda {

    public static ResultadoMoneda lanzar(Lado eleccionJugador) {
        Lado resultado = Math.random() < 0.5 ? Lado.CARA : Lado.CRUZ;
        boolean acierto = eleccionJugador == resultado;

        return new ResultadoMoneda(eleccionJugador, resultado, acierto);
    }

    public static Lado solicitarEleccion(Jugador jugador, Scanner scanner) {
        System.out.println("\n" + jugador.getNombre() + ", lanza una moneda para determinar el efecto:");
        System.out.println("1. CARA");
        System.out.println("2. CRUZ");

        int opcion = 0;
        while (opcion < 1 || opcion > 2) {
            System.out.print("Tu elección (1-2): ");
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion < 1 || opcion > 2) {
                    System.out.println("Opción inválida. Elige 1 o 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingresa un número válido.");
            }
        }
        return (opcion == 1) ? Lado.CARA : Lado.CRUZ;
    }
}