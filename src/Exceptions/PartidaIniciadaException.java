package Exceptions;

public class PartidaIniciadaException extends RuntimeException {
    public PartidaIniciadaException() {
        super("ERROR: LA PARTIDA YA FUE INICIADA");
    }
}
