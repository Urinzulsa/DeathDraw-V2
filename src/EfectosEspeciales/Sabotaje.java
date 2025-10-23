package EfectosEspeciales;

import Jugador.Jugador;
import Jugador.Revolver;

public class Sabotaje extends EfectoEspecial {

    public Sabotaje() {
        super(TipoEfecto.SABOTAJE);
    }

    @Override
    public String aplicar(Jugador jugador1, Jugador jugador2, CategoriaEfecto categoria) {
        // Sabotaje siempre es neutro, ignora la categoría
        Revolver revolverJ1 = jugador1.getRevolver();
        Revolver revolverJ2 = jugador2.getRevolver();

        jugador1.setRevolver(revolverJ2);
        jugador2.setRevolver(revolverJ1);

        return "¡SABOTAJE! " + jugador1.getNombre() + " y " + jugador2.getNombre() + " intercambian sus revólveres.";
    }
}