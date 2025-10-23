package Moneda;

public class Moneda {

    private static Lado Lado;

    public static ResultadoMoneda lanzar(Lado eleccionJugador) {
        Lado resultado = Math.random() < 0.5 ? Lado.CARA : Lado.CRUZ;
        boolean acierto = eleccionJugador == resultado;

        return new ResultadoMoneda(eleccionJugador, resultado, acierto);
    }
}