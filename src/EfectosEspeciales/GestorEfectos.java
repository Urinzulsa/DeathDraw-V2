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
            case VIDA_EXTRA -> "Positivo: gana una vida, Negativo: pierde una vida";
            case DOBLE_TURNO -> "Positivo: el rival juega 2 turnos, Negativo: tú juegas 2 turnos";
            case VAMPIRO -> "Positivo: robas una vida, Negativo: pierdes una vida";
            case DESARMAR -> "Positivo: descartas una bala de tu revólver, Negativo: el oponente descarta una bala de su revólver";
            case SABOTAJE -> "Intercambia los revólveres (neutral)";
            case BALA_LOCA -> "Positivo: rival gana bala, Negativo: tú ganas bala";
            case CAOS -> "Cambia aleatoriamente las balas de ambos revólveres (neutral)";
        };
    }
}