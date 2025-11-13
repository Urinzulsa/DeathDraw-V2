package EfectosEspeciales;

import Exceptions.JugadorNullException;
import Jugador.Jugador;

public class BalaLoca extends EfectoEspecial {

    public BalaLoca() {
        super(TipoEfecto.BALA_LOCA);
    }

    @Override
    public String aplicar(Jugador jugador1, Jugador jugador2, CategoriaEfecto categoria) {
        if (jugador1 == null ) {
            throw new JugadorNullException("Los jugadores no pueden ser nulos al aplicar el efecto BALA LOCA.");
        }
        switch (categoria) {
            case POSITIVA:
                // En modo 1v1, si no hay jugador2, el positivo no hace nada
                if (jugador2 != null) {
                    jugador2.getRevolver().cargarBala();
                    return "¡Una bala loca aparece en el revólver de " + jugador2.getNombre() + "!";
                } else {
                    // En modo SOLO, el positivo no carga bala (ya que no hay rival)
                    return jugador1.getNombre() + " esquiva la bala loca (no hay oponente)";
                }
            case NEGATIVA:
                jugador1.getRevolver().cargarBala();
                return "¡Una bala loca aparece en el revólver de " + jugador1.getNombre() + "!";
        }
        return "";

    }
}