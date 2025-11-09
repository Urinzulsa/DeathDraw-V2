package EfectosEspeciales;

public enum TipoEfecto {
    VIDA_EXTRA(false), //Positivo: gana vida, Negativo: pierde vida
    VAMPIRO(true), //El jugador roba una vida al oponente (requiere 2 jugadores)
    DESARMAR(false), //El jugador se le descarta una bala
    BALA_LOCA(false), //Agrega una bala al revolver del jugador

    //NEUTRAL
    SABOTAJE(true), //Cambia el revolver por el del oponente (requiere 2 jugadores)
    CAOS(false); //Cambia aleatoriamente el número de balas del revolver

    private final boolean requiereSegundoJugador;

    TipoEfecto(boolean requiereSegundoJugador) {
        this.requiereSegundoJugador = requiereSegundoJugador;
    }

    public boolean requiereSegundoJugador() {
        return requiereSegundoJugador;
    }

    public static TipoEfecto[] getEfectos1v1() {
        return new TipoEfecto[]{VIDA_EXTRA, DESARMAR, BALA_LOCA, CAOS};
    }

    public static TipoEfecto[] getEfectos2v2() {
        return TipoEfecto.values();
    }
}
