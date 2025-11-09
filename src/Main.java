import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ModoJuego modoSeleccionado;

    public static void main(String[] args) {
        mostrarBienvenida();

        ModoJuego modoSeleccionado = seleccionarModo();
        Partida partida = new Partida(modoSeleccionado);

        String[] nombresJugadores = solicitarNombresJugadores();
        if (!modoSeleccionado.equals(ModoJuego.SOLO)) {
            try {
                System.out.println(partida.agregarJugador(nombresJugadores[0]));
                System.out.println(partida.agregarJugador(nombresJugadores[1]));
            } catch (Exception e) {
                System.err.println("Error al agregar jugadores: " + e.getMessage());
                return;
            }
        } else {
            try {
                System.out.println(partida.agregarJugador(nombresJugadores[0]));
            } catch (Exception e) {
                System.err.println("Error al agregar jugador: " + e.getMessage());
                return;
            }
        }
        try {
            String resultado = partida.iniciarPartida();
            System.out.println("\n" + "=".repeat(60));
            // Extraer solo la parte del modo y jugadores, sin la carta
            String[] lineas = resultado.split("\n");
            for (int i = 0; i < lineas.length - 1; i++) { // Excluir la última línea (la carta)
                System.out.println(lineas[i]);
            }
            System.out.println("=".repeat(60) + "\n");

            jugarPartida(partida);

        } catch (Exception e) {
            System.err.println("Error al iniciar la partida: " + e.getMessage());
        }

        scanner.close();
    }

    private static void mostrarBienvenida() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("          DEATH DRAW");
        System.out.println("=".repeat(30));
    }

    private static ModoJuego seleccionarModo() {
        System.out.println("Selecciona el modo de juego:");
        System.out.println("1.  MODO CLÁSICO");
        System.out.println("   - Vidas: 3 por jugador");
        System.out.println("   - Balas iniciales: 1");
        System.out.println("   - Cartas especiales: 15%");
        System.out.println();
        System.out.println("2.  MODO SOBRECARGA");
        System.out.println("   - Vidas: 5 por jugador");
        System.out.println("   - Balas iniciales: 2");
        System.out.println("   - Cartas especiales: 20%");
        System.out.println();
        System.out.println("3.  MODO MUERTE SÚBITA");
        System.out.println("   - Vidas: 1 por jugador");
        System.out.println("   - Balas iniciales: 6 (revólver lleno)");
        System.out.println("   - Cartas especiales: 5%");
        System.out.println();
        System.out.println("4.  MODO SOLO");
        System.out.println("   - Vidas: 1 ");
        System.out.println("   - Balas iniciales: 1 (SI PIERDE EL JUEGO TERMINA)");
        System.out.println("   - Cartas especiales: 20%");
        System.out.println();

        int opcion = 0;
        while (opcion < 1 || opcion > 4) {
            System.out.print("Ingresa tu elección (1-4): ");
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion < 1 || opcion > 4) {
                    System.out.println("Opción inválida. Elige entre 1 y 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingresa un número válido.");
            }
        }

        ModoJuego modo = switch (opcion) {
            case 1 -> ModoJuego.CLASICO;
            case 2 -> ModoJuego.SOBRECARGA;
            case 3 -> ModoJuego.MUERTE_SUBITA;
            case 4 -> ModoJuego.SOLO;
            default -> ModoJuego.CLASICO;
        };
        modoSeleccionado = modo;
        System.out.println("\nModo seleccionado: " + modo);
        return modoSeleccionado;
    }

    private static String[] solicitarNombresJugadores() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("REGISTRO DE JUGADORES");
        System.out.println("-".repeat(60));
        System.out.print("Nombre del Jugador 1: ");
        String jugador1 = scanner.nextLine().trim();
        while (jugador1.isEmpty()) {
            System.out.print("El nombre no puede estar vacío. Intenta de nuevo: ");
            jugador1 = scanner.nextLine().trim();
        }
        if (!modoSeleccionado.equals(ModoJuego.SOLO)) {
            System.out.print("Nombre del Jugador 2: ");
            String jugador2 = scanner.nextLine().trim();
            while (jugador2.isEmpty()) {
                System.out.print("El nombre no puede estar vacío. Intenta de nuevo: ");
                jugador2 = scanner.nextLine().trim();
            }
            return new String[]{jugador1, jugador2};
        }

        return new String[]{jugador1};

    }

    private static void jugarPartida(Partida partida) {
        Jugador.Jugador jugador1 = partida.getJugador1();

        if (!modoSeleccionado.equals(ModoJuego.SOLO)) {
            Jugador.Jugador jugador2 = partida.getJugador2();

            while (jugador1.getVidas() > 0 && jugador2.getVidas() > 0) {
                Jugador.Jugador jugadorActual = (partida.getTurnosContador() % 2 == 1) ? jugador1 : jugador2;

                System.out.println("\n" + "=".repeat(60));
                System.out.println("TURNO DE: " + jugadorActual.getNombre().toUpperCase());
                System.out.println("=".repeat(60));
                mostrarEstadoJuego(jugador1, jugador2, partida, 0);

                TipoApuesta apuesta = solicitarApuesta(jugadorActual);
                partida.Apuesta(jugadorActual, apuesta);

                partida.incrementarTurno();

                System.out.println("\nPresiona ENTER para continuar...");
                scanner.nextLine();

            }
            System.out.println("\n" + "🏆".repeat(30));
            Jugador.Jugador ganador = jugador1.getVidas() > 0 ? jugador1 : jugador2;
            System.out.println("      ¡PARTIDA FINALIZADA!");
            System.out.println("      GANADOR: " + ganador.getNombre().toUpperCase());
            System.out.println("🏆".repeat(30) + "\n");
        } else {
            int racha = 0;
            while (!partida.getEstado().equals(Estado.FINALIZADO)) {
                System.out.println("\n" + "=".repeat(60));
                System.out.println("TURNO DE: " + jugador1.getNombre().toUpperCase() + " (SOLO)");
                System.out.println("=".repeat(60));
                mostrarEstadoJuego(jugador1, null, partida, racha); // Solo un jugador

                TipoApuesta apuesta = solicitarApuesta(jugador1);
                partida.Apuesta(jugador1, apuesta);
                if (partida.getEstado().equals(Estado.FINALIZADO)) {
                    break;
                }
                racha++; // contar aciertos consecutivos
                System.out.println("\nPresiona ENTER para continuar...");
                scanner.nextLine();
            }

            System.out.println("\n" + "💀".repeat(30));
            System.out.println("      ¡FIN DEL JUEGO SOLO!");
            System.out.println("      Racha máxima: " + racha + " aciertos");
            System.out.println("💀".repeat(30) + "\n");

            }
        }

    private static void mostrarEstadoJuego(Jugador.Jugador jugador1, Jugador.Jugador jugador2, Partida partida, int rachaActual) {
        System.out.println("\nESTADO DEL JUEGO:");
        System.out.println("┌" + "─".repeat(58) + "┐");
        if (!partida.getModoJuego().equals(ModoJuego.SOLO)) {
            System.out.println(String.format("│ %-20s ❤️  Vidas: %d      🔫 Balas: %d      │",
                    jugador1.getNombre(), jugador1.getVidas(), contarBalas(jugador1.getRevolver())));
            System.out.println(String.format("│ %-20s ❤️  Vidas: %d      🔫 Balas: %d      │",
                    jugador2.getNombre(), jugador2.getVidas(), contarBalas(jugador2.getRevolver())));
            System.out.println("└" + "─".repeat(58) + "┘");
        } else {
            System.out.println(" ".repeat(20) + jugador1.getNombre() + "| Racha actual: " + rachaActual + "|" );
            System.out.println("└" + "─".repeat(58) + "┘");
        }
        // Mostrar la carta actual
        if (partida.getCartaActual() != null) {
            System.out.println("\nCARTA ACTUAL EN MESA: " + partida.getCartaActual().obtenerRepresentacion());
        }
        System.out.println();
    }

    private static int contarBalas(Jugador.Revolver revolver) {
        return revolver.contarBalas();
    }

    private static TipoApuesta solicitarApuesta(Jugador.Jugador jugador) {
        System.out.println(jugador.getNombre() + ", ¿qué apuestas?");
        System.out.println("1. > MAYOR - La siguiente carta será MAYOR");
        System.out.println("2. < MENOR - La siguiente carta será MENOR");

        int opcion = 0;
        while (opcion < 1 || opcion > 2) {
            System.out.print("\nTu apuesta (1-2): ");
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion < 1 || opcion > 3) {
                    System.out.println("Opción inválida. Elige entre 1 y 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingresa un número válido.");
            }
        }

        return switch (opcion) {
            case 1 -> TipoApuesta.MAYOR;
            case 2 -> TipoApuesta.MENOR;
            default -> TipoApuesta.MAYOR; 
        };
    }
}
