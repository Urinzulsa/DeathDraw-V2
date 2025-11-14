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
        
        // Bucle principal del menú
        boolean continuarJugando = true;
        
        while (continuarJugando) {
            // Seleccionar modo de juego
            String modoSeleccionadoStr = menu.seleccionarModo();

            // Opción para salir del juego
            if ("SALIR".equalsIgnoreCase(modoSeleccionadoStr)) {
                consola.mostrarMensaje("\n¡Gracias por jugar a DEATH DRAW! ¡Hasta la próxima!");
                continuarJugando = false;
                break;
            }

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
                continue; // Volver al menú en vez de salir
            }

            // Bucle para jugar el mismo modo múltiples veces
            boolean jugarMismoModo = true;
            
            while (jugarMismoModo) {
                Partida partida = new Partida(modoSeleccionado);

                // Registrar jugadores
                if (!registrarJugadores(partida, modoSeleccionado)) {
                    break; // Salir del bucle del modo si hay error
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
                
                // Preguntar si quiere jugar de nuevo o volver al menú
                jugarMismoModo = menu.preguntarJugarDeNuevo();
            }
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
