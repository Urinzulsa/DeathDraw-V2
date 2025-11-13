package EfectosEspeciales;

import Exceptions.JugadorNullException;
import Jugador.Jugador;

public class VidaExtra extends EfectoEspecial {

    public VidaExtra() {
        super(TipoEfecto.VIDA_EXTRA);
    }

    @Override
    public String aplicar(Jugador jugador1, Jugador jugador2, CategoriaEfecto categoria) {
        if (jugador1 == null) {
            throw new JugadorNullException("El jugador no puede ser nulo al aplicar el efecto VIDA EXTRA.");
        }
        switch (categoria) {
            case POSITIVA:
                jugador1.ganarVida();
                return jugador1.getNombre() + " obtiene una vida extra";
            case NEGATIVA:
                jugador1.perderVida();
                return jugador1.getNombre() + " pierde una vida";
        }
        return "";
    }
}