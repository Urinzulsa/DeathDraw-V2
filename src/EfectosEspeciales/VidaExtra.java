package EfectosEspeciales;

import Jugador.Jugador;

public class VidaExtra extends EfectoEspecial {

    public VidaExtra() {
        super(TipoEfecto.VIDA_EXTRA);
    }

    @Override
    public String aplicar(Jugador jugador1, Jugador jugador2, CategoriaEfecto categoria) {
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