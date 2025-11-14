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
 * @version 2.1
 */
public class Main {

    /**
     * Vista de menús principales
     */
    private static MenuPrincipal menu;

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
                // Usamos un controlador temporal para mostrar la tabla y mantener el código DRY
                new ControladorJuego(null, null).mostrarTablaHighscore(hsManagerGlobal);
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
            if (!registrarJugadores(partida, modoSeleccionado)) {
                consola.cerrar();
                return; // Salir si hay error
            }


            // Iniciar y jugar partida
            try {
                String resultado = partida.iniciarPartida();
                menu.mostrarInicioPartida(resultado);

                // El controlador se encarga de la partida
                ControladorJuego controlador = new ControladorJuego(partida, modoSeleccionado);
                controlador.jugar();

            } catch (Exception e) {
                menu.mostrarError("Error durante la partida: " + e.getMessage());
            }

            break; // Tras jugar una partida salimos del loop principal y cerramos
        }

        // Cerrar recursos
        consola.cerrar();
    }

    /**
     * Inicializa las vistas básicas.
     */
    private static void inicializarVistas() {
        consola = InterfazConsola.obtenerInstancia();
        menu = new MenuPrincipal();
    }

    /**
     * Solicita nombres y agrega jugadores a la partida.
     *
     * @param partida Partida a la que agregar jugadores
     * @param modo    Modo de juego seleccionado
     * @return true si se agregaron correctamente, false si hubo error
     */
    private static boolean registrarJugadores(Partida partida, ModoJuego modo) {
        String[] nombresJugadores;
        while (true) {
            try {
                nombresJugadores = menu.solicitarNombresJugadores(modo.equals(ModoJuego.SOLO));
                break; // Nombres válidos, salimos del bucle
            } catch (NombreInvalidoException e) {
                menu.mostrarError("ERROR: " + e.getMessage() + ". Por favor inténtelo nuevamente.");
            }
        }

        try {
            for (String nombre : nombresJugadores) {
                menu.mostrarJugadorAgregado(partida.agregarJugador(nombre));
            }
            return true;
        } catch (Exception e) {
            menu.mostrarError("Error al agregar jugadores: " + e.getMessage());
            return false;
        }
    }
}
