package EfectosEspeciales;

import Jugador.Jugador;

public class Vampiro extends EfectoEspecial {

    public Vampiro() {
        super(TipoEfecto.VAMPIRO);
    }

    @Override
    public String aplicar(Jugador jugador1, Jugador jugador2, CategoriaEfecto categoria) {
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