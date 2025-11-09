package EfectosEspeciales;

import Jugador.Jugador;

public class BalaLoca extends EfectoEspecial {

    public BalaLoca() {
        super(TipoEfecto.BALA_LOCA);
    }

    @Override
    public String aplicar(Jugador jugador1, Jugador jugador2, CategoriaEfecto categoria) {
        switch (categoria) {
            case POSITIVA:
                // En modo 1v1, si no hay jugador2, el positivo no hace nada
                if (jugador2 != null) {
                    jugador2.getRevolver().cargarBala();
                    return "¡Una bala loca aparece en el revólver de " + jugador2.getNombre() + "!";
                } else {
                    // En modo SOLO, el positivo no carga bala (ya que no hay rival)
                    return jugador1.getNombre() + " esquiva la bala loca";
                }
            case NEGATIVA:
                jugador1.getRevolver().cargarBala();
                return "¡Una bala loca aparece en el revólver de " + jugador1.getNombre() + "!";
        }
        return "";

    }
}