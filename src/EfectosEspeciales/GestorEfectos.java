package EfectosEspeciales;

import Carta.Carta;
import Exceptions.EfectoInvalidoException;
import Exceptions.JugadorNullException;
import Jugador.Jugador;
import Moneda.Moneda;
import Moneda.ResultadoMoneda;
import Moneda.Lado;

public class GestorEfectos {
    public static String aplicarEfectoConMoneda(EfectoEspecial efecto, Jugador jugador1, Jugador jugador2, Lado eleccionMoneda) throws EfectoInvalidoException {
        if (efecto == null || efecto.getTipoEfecto() == null) {
            throw new EfectoInvalidoException("El efecto especial es inválido o la carta no tiene efecto asociado.");
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
        CategoriaEfecto categoriaFinal = resultado.acerto() ? CategoriaEfecto.POSITIVA : CategoriaEfecto.NEGATIVA;
        String tipoEfecto = resultado.acerto() ? "POSITIVO" : "NEGATIVO";
        try {
            return mensajeMoneda + "\n" +
                    efecto.aplicar(jugador1, jugador2, categoriaFinal) +
                    " (Efecto " + tipoEfecto + " para " + jugador1.getNombre() + ")";
        } catch (JugadorNullException e) {
            return "⚠️ ERROR: no se pudo aplicar el efecto porque uno de los jugadores es nulo. Detalle: " + e.getMessage();
        }
    }

    public static String aplicarEfectoDeCarta(Carta carta, Jugador jugador1, Jugador jugador2, Lado eleccionMoneda) throws EfectoInvalidoException {
        if (carta == null || carta.getEfecto() == null || !carta.tieneEfecto()) {
            throw new EfectoInvalidoException("EL EFECTO ESPECIAL ES INVALIDO (NULO,VACIO U OTRO)");

        }

        return aplicarEfectoConMoneda(carta.getEfecto(), jugador1, jugador2, eleccionMoneda);
    }

    public static String obtenerDescripcionEfecto(TipoEfecto tipo) {
        return switch (tipo) {
            case VIDA_EXTRA -> "Positivo: ganas una vida, Negativo: pierdes una vida";
            case VAMPIRO -> "Positivo: robas una vida al oponente, Negativo: el oponente te roba una vida";
            case DESARMAR -> "Positivo: descartas una bala de tu revólver, Negativo: el oponente descarta una bala";
            case SABOTAJE -> "Intercambia los revólveres con el oponente (neutral)";
            case BALA_LOCA ->
                    "Positivo: aparece una bala en el revólver del oponente, Negativo: aparece una bala en tu revólver";
            case CAOS -> "Cambia aleatoriamente las balas del revólver (neutral)";
        };
    }
}