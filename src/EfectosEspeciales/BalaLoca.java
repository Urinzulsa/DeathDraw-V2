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
                jugador2.getRevolver().cargarBala();
                return "¡Una bala loca aparece en el revólver de " + jugador2.getNombre() + "!";
            case NEGATIVA:
                jugador1.getRevolver().cargarBala();
                return "¡Una bala loca aparece en el revólver de " + jugador1.getNombre() + "!";
        }
        return "";

    }
}