package EfectosEspeciales;

import Jugador.Jugador;

public class Desarmar extends EfectoEspecial {

    public Desarmar() {
        super(TipoEfecto.DESARMAR);
    }

    @Override
    public String aplicar(Jugador jugador1, Jugador jugador2, CategoriaEfecto categoria) {
        switch (categoria) {
            case POSITIVA:
                jugador1.getRevolver().quitarBala();
                return jugador1.getNombre() + " se libra de una bala de su revólver";
            case NEGATIVA:
                jugador2.getRevolver().quitarBala();
                return jugador2.getNombre() + " se libra de una bala de su revólver";
        }
        return "";
    }
}