package EfectosEspeciales;

import Jugador.Jugador;

public class Caos extends EfectoEspecial {

    public Caos() {
        super(TipoEfecto.CAOS);
    }

    @Override
    public String aplicar(Jugador jugador1, Jugador jugador2, CategoriaEfecto categoria) {
        // Caos siempre es neutro, ignora la categoría
        // Generar cantidades aleatorias de balas para cada jugador (0-6)
        int nuevasBalasJ1 = (int) (Math.random() * 7); // 0 a 6 balas
        int nuevasBalasJ2 = (int) (Math.random() * 7); // 0 a 6 balas

        // Aplicar el cambio a los revólveres
        jugador1.getRevolver().setBalas(nuevasBalasJ1);
        jugador2.getRevolver().setBalas(nuevasBalasJ2);

        return "¡CAOS! El revólver de " + jugador1.getNombre() + " ahora tiene " + nuevasBalasJ1 +
                " balas y el de " + jugador2.getNombre() + " tiene " + nuevasBalasJ2 + " balas.";
    }
}