package EfectosEspeciales;

import Exceptions.JugadorNullException;
import Jugador.Jugador;

public class Vampiro extends EfectoEspecial {

    public Vampiro() {
        super(TipoEfecto.VAMPIRO);
    }

    @Override
    public String aplicar(Jugador jugador1, Jugador jugador2, CategoriaEfecto categoria) {
        if (jugador1 == null || jugador2 == null) {
            throw new JugadorNullException("Los jugadores no pueden ser nulos al aplicar el efecto Vampiro.");
        }
        switch (categoria) {
            case POSITIVA:
                jugador1.ganarVida();
                jugador2.perderVida();
                return jugador1.getNombre() + " absorbe una vida de " + jugador2.getNombre();
            case NEGATIVA:
                jugador2.ganarVida();
                jugador1.perderVida();
                return jugador2.getNombre() + " absorbe una vida de " + jugador1.getNombre();
        }
        return "";
    }
}