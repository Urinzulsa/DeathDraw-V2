import Exceptions.CartaNulaException;
import Exceptions.NombreInvalidoException;
import Exceptions.PartidaNoIniciadaException;
import Jugador.Jugador;
import Vista.InterfazConsola;
import Vista.MenuPrincipal;
import Vista.VistaJuego;
import Modelo.ResultadoApuesta;
import Modelo.HighscoreManager;

/**
 * Clase principal del juego DeathDraw.
 * <p>
 * Responsabilidades:
 * <ul>
 *   <li>Inicializar las vistas (MenuPrincipal, VistaJuego)</li>
 *   <li>Orquestar el flujo general del juego</li>
 *   <li>Coordinar entre la lógica de negocio (Partida) y la presentación (Vistas)</li>
 * </ul>
 * <p>
 * Esta clase NO contiene lógica de UI directa, delega todo a las clases Vista.
 * </p>
 *
 * @author DeathDraw-V2
 * @version 2.0
 */
public class Main {

    /**
     * Vista de menús principales
     */
    private static MenuPrincipal menu;

    /**
     * Vista del estado del juego
     */
    private static VistaJuego vista;

    /**
     * Interfaz de consola
     */
    private static InterfazConsola consola;

    /**
     * Punto de entrada principal del juego.
     *
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Inicializar vistas
        inicializarVistas();

        // Mostrar bienvenida
        menu.mostrarBienvenida();

        HighscoreManager hsManagerGlobal = new HighscoreManager();

        while (true) {
            // Seleccionar modo de juego
            String modoSeleccionadoStr = menu.seleccionarModo();

            if ("VER_TOP".equalsIgnoreCase(modoSeleccionadoStr)) {
                // Mostrar top 5 y volver al menú
                java.util.List<Modelo.HighscoreEntry> top = hsManagerGlobal.obtenerTop();
                StringBuilder tabla = new StringBuilder();
                tabla.append(String.format("%3s | %-20s | %s\n", "#", "NOMBRE", "PUNTAJE"));
                tabla.append("-----------------------------------------\n");
                for (int i = 0; i < 5; i++) {
                    if (i < top.size()) {
                        Modelo.HighscoreEntry e = top.get(i);
                        tabla.append(String.format("%3d | %-20s | %d\n", i + 1, e.getNombre(), e.getPuntaje()));
                    } else {
                        tabla.append(String.format("%3d | %-20s | %s\n", i + 1, "---", "-"));
                    }
                }
                consola.mostrarCaja(tabla.toString());
                consola.esperarEnter();
                continue; // volver a mostrar el menú
            }

            ModoJuego modoSeleccionado;
            try {
                modoSeleccionado = ModoJuego.fromString(modoSeleccionadoStr);
            } catch (IllegalArgumentException e) {
                menu.mostrarError("Error: " + e.getMessage());
                consola.cerrar();
                return;
            }

            Partida partida = new Partida(modoSeleccionado);

            // Registrar jugadores
            String[] nombresJugadores = new String[0];
            boolean ingresado = false;
            while (!ingresado) {
                try {
                    nombresJugadores = menu.solicitarNombresJugadores(
                            modoSeleccionado.equals(ModoJuego.SOLO));
                    ingresado = true;
                } catch (NombreInvalidoException e) {
                    System.out.println("ERROR: " + e.getMessage());
                    System.out.println("Por favor inténtelo nuevamente.");
                }
            }
            if (!agregarJugadoresAPartida(partida, nombresJugadores, modoSeleccionado)) {
                consola.cerrar();
                return; // Error al agregar jugadores
            }

            // Iniciar y jugar partida
            try {
                String resultado = partida.iniciarPartida();
                menu.mostrarInicioPartida(resultado);
                jugarPartida(partida, modoSeleccionado);
            } catch (Exception e) {
                menu.mostrarError("Error al iniciar la partida: " + e.getMessage());
            }

            break; // Tras jugar una partida salimos del loop principal y cerramos
        }

        // Cerrar recursos
        consola.cerrar();
    }


    /**
     * Convierte un String a ModoJuego.
     *
     * @param modo String del modo ("CLASICO", "SOBRECARGA", etc.)
     * @return El enum ModoJuego correspondiente
     * @deprecated Usar ModoJuego.fromString() directamente
     */
    @Deprecated
    private static ModoJuego convertirAModoJuego(String modo) {
        return switch (modo) {
            case "CLASICO" -> ModoJuego.CLASICO;
            case "SOBRECARGA" -> ModoJuego.SOBRECARGA;
            case "MUERTE_SUBITA" -> ModoJuego.MUERTE_SUBITA;
            case "SOLO" -> ModoJuego.SOLO;
            default -> ModoJuego.CLASICO;
        };
    }

    /**
     * Convierte un String a TipoApuesta.
     *
     * @param apuesta String de la apuesta ("MAYOR" o "MENOR")
     * @return El enum TipoApuesta correspondiente
     * @deprecated Usar TipoApuesta.fromString() directamente
     */
    @Deprecated
    private static TipoApuesta convertirATipoApuesta(String apuesta) {
        return switch (apuesta) {
            case "MAYOR" -> TipoApuesta.MAYOR;
            case "MENOR" -> TipoApuesta.MENOR;
            default -> TipoApuesta.MAYOR;
        };
    }

    /**
     * Inicializa todas las vistas del juego.
     */
    private static void inicializarVistas() {
        consola = InterfazConsola.obtenerInstancia();
        menu = new MenuPrincipal();
        vista = new VistaJuego();
    }

    /**
     * Agrega jugadores a la partida según el modo de juego.
     *
     * @param partida Partida a la que agregar jugadores
     * @param nombres Nombres de los jugadores
     * @param modo    Modo de juego seleccionado
     * @return true si se agregaron correctamente, false si hubo error
     */
    private static boolean agregarJugadoresAPartida(Partida partida, String[] nombres, ModoJuego modo) {
        try {
            if (!modo.equals(ModoJuego.SOLO)) {
                menu.mostrarJugadorAgregado(partida.agregarJugador(nombres[0]));
                menu.mostrarJugadorAgregado(partida.agregarJugador(nombres[1]));
            } else {
                menu.mostrarJugadorAgregado(partida.agregarJugador(nombres[0]));
            }
            return true;
        } catch (Exception e) {
            menu.mostrarError("Error al agregar jugadores: " + e.getMessage());
            return false;
        }
    }

    /**
     * Ejecuta el loop principal del juego según el modo seleccionado.
     *
     * @param partida Partida a jugar
     * @param modo    Modo de juego seleccionado
     * @throws PartidaNoIniciadaException si la partida no está iniciada
     * @throws CartaNulaException         si hay error con las cartas
     */
    private static void jugarPartida(Partida partida, ModoJuego modo)
            throws PartidaNoIniciadaException, CartaNulaException {

        if (modo.equals(ModoJuego.SOLO)) {
            jugarPartidaSolo(partida);
        } else {
            jugarPartidaMultijugador(partida);
        }
    }

    /**
     * Ejecuta el loop de juego para modo multijugador.
     *
     * @param partida Partida a jugar
     * @throws PartidaNoIniciadaException si la partida no está iniciada
     * @throws CartaNulaException         si hay error con las cartas
     */
    private static void jugarPartidaMultijugador(Partida partida)
            throws PartidaNoIniciadaException, CartaNulaException {
        Jugador jugador1 = partida.getJugador1();
        Jugador jugador2 = partida.getJugador2();

        while (jugador1.getVidas() > 0 && jugador2.getVidas() > 0) {
            Jugador jugadorActual = (partida.getTurnosContador() % 2 == 1) ? jugador1 : jugador2;

            // Mostrar turno
            vista.mostrarEncabezadoTurno(jugadorActual.getNombre());
            vista.mostrarEstadoJuegoMultijugador(jugador1, jugador2, partida.getCartaActual());

            // Solicitar y procesar apuesta
            String apuestaStr = menu.solicitarApuesta(jugadorActual.getNombre());
            TipoApuesta apuesta;

            try {
                apuesta = TipoApuesta.fromString(apuestaStr);
            } catch (IllegalArgumentException e) {
                vista.mostrarError("Error: " + e.getMessage());
                continue;
            }

            try {
                ResultadoApuesta resultado = partida.procesarApuesta(jugadorActual, apuesta);
                vista.mostrarResultadoApuesta(resultado);
            } catch (PartidaNoIniciadaException e) {
                vista.mostrarError("ERROR AL REALIZAR APUESTA: " + e.getMessage());
            } catch (CartaNulaException e) {
                throw new RuntimeException(e);
            }

            partida.incrementarTurno();
            vista.esperarContinuar();
        }

        // Mostrar ganador
        Jugador ganador = jugador1.getVidas() > 0 ? jugador1 : jugador2;
        vista.mostrarVictoria(ganador.getNombre());
    }

    /**
     * Ejecuta el loop de juego para modo SOLO.
     *
     * @param partida Partida a jugar
     * @throws PartidaNoIniciadaException si la partida no está iniciada
     * @throws CartaNulaException         si hay error con las cartas
     */
    private static void jugarPartidaSolo(Partida partida)
            throws PartidaNoIniciadaException, CartaNulaException {
        Jugador jugador1 = partida.getJugador1();
        int racha = 0;

        while (!partida.getEstado().equals(Estado.FINALIZADO)) {
            // Mostrar turno
            vista.mostrarEncabezadoTurnoSolo(jugador1.getNombre());
            vista.mostrarEstadoJuegoSolo(jugador1, partida.getCartaActual(), racha);

            // Solicitar y procesar apuesta
            String apuestaStr = menu.solicitarApuesta(jugador1.getNombre());
            TipoApuesta apuesta;

            try {
                apuesta = TipoApuesta.fromString(apuestaStr);
            } catch (IllegalArgumentException e) {
                vista.mostrarError("Error: " + e.getMessage());
                continue;
            }

            ResultadoApuesta resultado = partida.procesarApuesta(jugador1, apuesta);
            vista.mostrarResultadoApuesta(resultado);

            if (partida.getEstado().equals(Estado.FINALIZADO)) {
                break;
            }

            racha++;
            vista.esperarContinuar();
        }

        // Mostrar resultado final
        vista.mostrarFinSolo(racha);

        // ====== Highscore: actualizar top5 para modo SOLO ======
        HighscoreManager hsManager = new HighscoreManager();
        boolean nuevo = hsManager.actualizarHighscore(jugador1.getNombre(), racha);
        if (nuevo) {
            consola.mostrarExito("¡Felicidades! Has conseguido un nuevo HIGH SCORE en modo SOLO: " + racha);
        } else {
            consola.mostrarMensaje("No alcanzaste el Highscore. Intenta de nuevo para entrar en el top 5.");
        }

        // Mostrar tabla del top 5 (nombre - puntaje)
        StringBuilder tabla = new StringBuilder();
        tabla.append(String.format("%3s | %-20s | %s\n", "#", "NOMBRE", "PUNTAJE"));
        tabla.append("-----------------------------------------\n");
        java.util.List<Modelo.HighscoreEntry> top = hsManager.obtenerTop();
        for (int i = 0; i < 5; i++) {
            if (i < top.size()) {
                Modelo.HighscoreEntry e = top.get(i);
                tabla.append(String.format("%3d | %-20s | %d\n", i + 1, e.getNombre(), e.getPuntaje()));
            } else {
                tabla.append(String.format("%3d | %-20s | %s\n", i + 1, "---", "-"));
            }
        }
        consola.mostrarCaja(tabla.toString());

    }
}
