package EfectosEspeciales;

import Exceptions.JugadorNullException;
import Jugador.Jugador;
import Jugador.Revolver;

public class Sabotaje extends EfectoEspecial {

    public Sabotaje() {
        super(TipoEfecto.SABOTAJE);
    }

    @Override
    public String aplicar(Jugador jugador1, Jugador jugador2, CategoriaEfecto categoria) {
        if (jugador1 == null || jugador2 == null) {
            throw new JugadorNullException("Los jugadores no pueden ser nulos al aplicar el efecto SABOTAJE.");
        }
        // Sabotaje siempre es neutro, ignora la categoría
        Revolver revolverJ1 = jugador1.getRevolver();
        Revolver revolverJ2 = jugador2.getRevolver();

        jugador1.setRevolver(revolverJ2);
        jugador2.setRevolver(revolverJ1);

        return "¡SABOTAJE! " + jugador1.getNombre() + " y " + jugador2.getNombre() + " intercambian sus revólveres.";
    }
}