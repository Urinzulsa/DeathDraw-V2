package EfectosEspeciales;

import Carta.Carta;
import Jugador.Jugador;
import Moneda.Moneda;
import Moneda.ResultadoMoneda;
import Moneda.Lado;

public class GestorEfectos {
    public static String aplicarEfectoConMoneda(EfectoEspecial efecto, Jugador jugador1, Jugador jugador2, Lado eleccionMoneda) {
        if (efecto == null) {
            return "No hay efecto especial que aplicar";
        }

        // Para efectos neutrales (SABOTAJE y CAOS), se aplican directamente sin moneda
        TipoEfecto tipo = efecto.getTipoEfecto();
        if (tipo == TipoEfecto.SABOTAJE || tipo == TipoEfecto.CAOS) {
            return efecto.aplicar(jugador1, jugador2, CategoriaEfecto.NEUTRAL);
        }

        // Lanzar la moneda para efectos positivos/negativos
        ResultadoMoneda resultado = Moneda.lanzar(eleccionMoneda);
        String mensajeMoneda = resultado.obtenerRepresentacion();

        // Determinar categoría según resultado de la moneda
        CategoriaEfecto categoriaFinal = resultado.esAcierto() ? CategoriaEfecto.POSITIVA : CategoriaEfecto.NEGATIVA;
        String tipoEfecto = resultado.esAcierto() ? "POSITIVO" : "NEGATIVO";

        return mensajeMoneda + "\n" +
                efecto.aplicar(jugador1, jugador2, categoriaFinal) +
                " (Efecto " + tipoEfecto + " para " + jugador1.getNombre() + ")";
    }

    public static String aplicarEfectoDeCarta(Carta carta, Jugador jugador1, Jugador jugador2, Lado eleccionMoneda) {
        if (!carta.tieneEfecto()) {
            return "Esta carta no tiene efectos especiales";
        }

        return aplicarEfectoConMoneda(carta.getEfecto(), jugador1, jugador2, eleccionMoneda);
    }

    public static String obtenerDescripcionEfecto(TipoEfecto tipo) {
        return switch (tipo) {
            case VIDA_EXTRA -> "Positivo: ganas una vida, Negativo: pierdes una vida";
            case VAMPIRO -> "Positivo: robas una vida al oponente, Negativo: el oponente te roba una vida";
            case DESARMAR -> "Positivo: descartas una bala de tu revólver, Negativo: el oponente descarta una bala";
            case SABOTAJE -> "Intercambia los revólveres con el oponente (neutral)";
            case BALA_LOCA -> "Positivo: aparece una bala en el revólver del oponente, Negativo: aparece una bala en tu revólver";
            case CAOS -> "Cambia aleatoriamente las balas del revólver (neutral)";
        };
    }
}