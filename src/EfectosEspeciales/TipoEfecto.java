package EfectosEspeciales;

public enum TipoEfecto {
    VIDA_EXTRA, //Positivo: gana vida, Negativo: pierde vida
    DOBLE_TURNO, //Agrega un turno extra al jugador
    VAMPIRO, //El jugador roba una vida al oponente
    DESARMAR, //El jugador se le descarta una bala
    BALA_LOCA, //Agrega una bala al revolver del jugador

    //NEUTRAL
    SABOTAJE, //Cambia el revolver por el del oponente
    CAOS, //Cambia aleatoriamente el número de balas en ambos revólveres (0-6)
}
