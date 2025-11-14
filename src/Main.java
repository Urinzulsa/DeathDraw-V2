import Exceptions.CartaNulaException;
import Exceptions.NombreInvalidoException;
import Exceptions.PartidaNoIniciadaException;
import Jugador.Jugador;
import Vista.InterfazConsola;
import Vista.MenuPrincipal;
import Vista.VistaJuego;
import Modelo.ResultadoApuesta;
import Modelo.GestorPuntuaciones;

public class Main {

    
    private static MenuPrincipal menu;

    
    private static InterfazConsola consola;

    
    public static void main(String[] args) {
        // Inicializar vistas
        inicializarVistas();

        // Mostrar bienvenida
        menu.mostrarBienvenida();

        GestorPuntuaciones gestorPuntuacionesGlobal = new GestorPuntuaciones();

        while (true) {
            // Seleccionar modo de juego
            String modoSeleccionadoStr = menu.seleccionarModo();

            if ("VER_TOP".equalsIgnoreCase(modoSeleccionadoStr)) {
                // Mostrar top 5 y volver al menú
                // Usamos un controlador temporal para mostrar la tabla y mantener el código DRY
                new ControladorJuego(null, null).mostrarTablaPuntuaciones(gestorPuntuacionesGlobal);
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

    
    private static void inicializarVistas() {
        consola = InterfazConsola.obtenerInstancia();
        menu = new MenuPrincipal();
    }

    
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
