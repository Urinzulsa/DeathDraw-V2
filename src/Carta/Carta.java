package Carta;

import EfectosEspeciales.EfectoEspecial;

public class Carta {
    private final int valor;
    private final Palo palo;
    private EfectoEspecial efecto; // Puede ser null si no tiene efecto

    // Constructor para cartas normales
    public Carta(int valor, Palo palo) {
        this.valor = valor;
        this.palo = palo;
        this.efecto = null; // Por defecto no tiene efecto
    }

    // Constructor para crear una carta con un efecto específico
    public Carta(int valor, Palo palo, EfectoEspecial efecto) {
        this.valor = valor;
        this.palo = palo;
        this.efecto = efecto;
    }

    public boolean esMayorQue(Carta otraCarta) {
        return this.valor > otraCarta.valor;
    }

    public boolean esMenorQue(Carta otraCarta) {
        return this.valor < otraCarta.valor;
    }

    public String obtenerRepresentacion() {
        String representacion = this.valor + " de " + this.palo;
        if (tieneEfecto()) {
            representacion += "[" + efecto.getTipoEfecto() + "]";
        }
        return representacion;
    }

    public boolean tieneEfecto() {
        return efecto != null;
    }

    public EfectoEspecial getEfecto() {
        return efecto;
    }

    public void setEfecto(EfectoEspecial efecto) {
        this.efecto = efecto;
    }

    @Override
    public String toString() {
        return "CARTA: " + obtenerRepresentacion();
    }
}
