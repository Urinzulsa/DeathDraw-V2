package Moneda;

import Modelo.Acerto;

public class ResultadoMoneda implements Acerto {
    private final Lado eleccion;
    private final Lado resultado;
    private final boolean acierto;

    public ResultadoMoneda(Lado eleccion, Lado resultado, boolean acierto) {
        this.eleccion = eleccion;
        this.resultado = resultado;
        this.acierto = acierto;
    }

    public Lado getEleccion() {
        return eleccion;
    }

    public Lado getResultado() {
        return resultado;
    }

    @Override
    public boolean acerto() {
        return acierto;
    }

    public String obtenerRepresentacion() {
        String estadoTexto = acierto ? "✅ ACERTO" : "❌ FALLO";

        return String.format("Eligio %s - Salio %s - %s",
                eleccion, resultado, estadoTexto);
    }

    @Override
    public String toString() {
        return obtenerRepresentacion();
    }
}