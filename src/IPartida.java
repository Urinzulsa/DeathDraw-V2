import Jugador.Jugador;
import Carta.Carta;
import Modelo.ResultadoApuesta;
import Exceptions.PartidaNoIniciadaException;
import Exceptions.CartaNulaException;
import Exceptions.JugadoresCompletosException;
import Exceptions.PartidaIniciadaException;
import Exceptions.JugadorNullException;

public interface IPartida {
    String agregarJugador(String nombre) throws JugadoresCompletosException;

    String iniciarPartida() throws PartidaIniciadaException, JugadorNullException;

    ResultadoApuesta procesarApuesta(Jugador jugador, TipoApuesta apuesta)
            throws PartidaNoIniciadaException, CartaNulaException;

    void incrementarTurno();

    Jugador getJugador1();

    Jugador getJugador2();

    Carta getCartaActual();

    int getTurnosContador();

    Estado getEstado();
}
