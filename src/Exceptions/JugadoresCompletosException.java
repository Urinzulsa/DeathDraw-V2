package Exceptions;

public class JugadoresCompletosException extends RuntimeException {
    public JugadoresCompletosException() {
        super("La partida ya tiene dos jugadores.");
    }
}
