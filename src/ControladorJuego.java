import Exceptions.CartaNulaException;
import Exceptions.PartidaNoIniciadaException;
import Jugador.Jugador;
import Modelo.GestorPuntuaciones;
import Modelo.ResultadoApuesta;
import Vista.InterfazConsola;
import Vista.MenuPrincipal;
import Vista.VistaJuego;

public class ControladorJuego {

    private final Partida partida;
    private final ModoJuego modo;
    private final VistaJuego vista;
    private final MenuPrincipal menu;
    private final InterfazConsola consola;

    public ControladorJuego(Partida partida, ModoJuego modo) {
        this.partida = partida;
        this.modo = modo;
        this.vista = new VistaJuego();
        this.menu = new MenuPrincipal();
        this.consola = InterfazConsola.obtenerInstancia();
    }

    public void jugar() throws PartidaNoIniciadaException, CartaNulaException {
        if (modo.equals(ModoJuego.SOLO)) {
            jugarPartidaSolo();
        } else {
            jugarPartidaMultijugador();
        }
    }

    private void jugarPartidaMultijugador() throws PartidaNoIniciadaException, CartaNulaException {
        Jugador jugador1 = partida.getJugador1();
        Jugador jugador2 = partida.getJugador2();

        while (jugador1.getVidas() > 0 && jugador2.getVidas() > 0) {
            Jugador jugadorActual = (partida.getTurnosContador() % 2 == 1) ? jugador1 : jugador2;

            vista.mostrarEncabezadoTurno(jugadorActual.getNombre());
            vista.mostrarEstadoJuegoMultijugador(jugador1, jugador2, partida.getCartaActual());

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

        Jugador ganador = jugador1.getVidas() > 0 ? jugador1 : jugador2;
        vista.mostrarVictoria(ganador.getNombre());
    }

    private void jugarPartidaSolo() throws PartidaNoIniciadaException, CartaNulaException {
        Jugador jugador1 = partida.getJugador1();
        int racha = 0;

        while (!partida.getEstado().equals(Estado.FINALIZADO)) {
            vista.mostrarEncabezadoTurnoSolo(jugador1.getNombre());
            vista.mostrarEstadoJuegoSolo(jugador1, partida.getCartaActual(), racha);

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

        vista.mostrarFinSolo(racha);
        gestionarHighscore(jugador1.getNombre(), racha);
    }

    private void gestionarHighscore(String nombre, int puntaje) {
        GestorPuntuaciones gestorPuntuaciones = new GestorPuntuaciones();
        boolean nuevo = gestorPuntuaciones.actualizarPuntuacion(nombre, puntaje);
        if (nuevo) {
            consola.mostrarExito("¡Felicidades! Has conseguido una nueva PUNTUACIÓN ALTA en modo SOLO: " + puntaje);
        } else {
            consola.mostrarMensaje("No alcanzaste una puntuación alta. Intenta de nuevo para entrar en el top 5.");
        }
        mostrarTablaPuntuaciones(gestorPuntuaciones);
    }

    public void mostrarTablaPuntuaciones(GestorPuntuaciones gestorPuntuaciones) {
        java.util.List<Modelo.EntradaPuntuacion> top = gestorPuntuaciones.obtenerTop();
        StringBuilder tabla = new StringBuilder();
        tabla.append(String.format("%3s | %-20s | %s\n", "#", "NOMBRE", "PUNTAJE"));
        tabla.append("-----------------------------------------\n");
        for (int i = 0; i < 5; i++) {
            if (i < top.size()) {
                Modelo.EntradaPuntuacion e = top.get(i);
                tabla.append(String.format("%3d | %-20s | %d\n", i + 1, e.getNombre(), e.getPuntaje()));
            } else {
                tabla.append(String.format("%3d | %-20s | %s\n", i + 1, "---", "-"));
            }
        }
        consola.mostrarCaja(tabla.toString());
    }
}
