package EfectosEspeciales;

import Exceptions.JugadorNullException;
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
                // En modo 1v1, el negativo también afecta al jugador1
                if (jugador2 != null) {
                    jugador2.getRevolver().quitarBala();
                    return jugador2.getNombre() + " se libra de una bala de su revólver";
                } else {
                    throw new JugadorNullException(jugador1.getNombre() + " no puede desarmar a nadie");
                }
        }
        return "";
    }
}