package EfectosEspeciales;

import Jugador.Jugador;

public class DobleTurno extends EfectoEspecial {

    public DobleTurno() {
        super(TipoEfecto.DOBLE_TURNO);
    }


    @Override
    public String aplicar(Jugador jugador1, Jugador jugador2, CategoriaEfecto categoria) {
        return switch (categoria) {
            case POSITIVA -> "¡" + jugador2.getNombre() + " obtiene un turno extra!";
            case NEGATIVA -> "¡" + jugador1.getNombre() + " obtiene un turno extra!";
            case NEUTRAL -> "";
        };
    }
}