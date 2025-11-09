package EfectosEspeciales;

import Jugador.Jugador;

public class Caos extends EfectoEspecial {

    public Caos() {
        super(TipoEfecto.CAOS);
    }

    @Override
    public String aplicar(Jugador jugador1, Jugador jugador2, CategoriaEfecto categoria) {
        // Caos siempre es neutro, ignora la categoría
        // Generar cantidad aleatoria de balas para el jugador (0-6)
        int nuevasBalas = (int) (Math.random() * 7); // 0 a 6 balas

        // Aplicar el cambio al revólver del jugador actual
        jugador1.getRevolver().setBalas(nuevasBalas);

        // Si hay un segundo jugador (modo 2v2), también cambiar sus balas
        if (jugador2 != null) {
            int nuevasBalasJ2 = (int) (Math.random() * 7); // 0 a 6 balas
            jugador2.getRevolver().setBalas(nuevasBalasJ2);
            return "¡CAOS! El revólver de " + jugador1.getNombre() + " ahora tiene " + nuevasBalas +
                    " balas y el de " + jugador2.getNombre() + " tiene " + nuevasBalasJ2 + " balas.";
        }

        return "¡CAOS! El revólver de " + jugador1.getNombre() + " ahora tiene " + nuevasBalas + " balas.";
    }
}