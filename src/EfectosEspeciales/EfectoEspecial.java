package EfectosEspeciales;

import Jugador.Jugador;

public abstract class EfectoEspecial {
    private TipoEfecto tipoEfecto;

    public EfectoEspecial(TipoEfecto tipo) {
        this.tipoEfecto = tipo;
    }

    public TipoEfecto getTipoEfecto() {
        return tipoEfecto;
    }

    public static EfectoEspecial crear(TipoEfecto tipo) {
        return switch (tipo) {
            case VIDA_EXTRA -> new VidaExtra();
            case DOBLE_TURNO -> new DobleTurno();
            case VAMPIRO -> new Vampiro();
            case DESARMAR -> new Desarmar();
            case SABOTAJE -> new Sabotaje();
            case BALA_LOCA -> new BalaLoca();
            case CAOS -> new Caos();
        };
    }

    public abstract String aplicar(Jugador jugador1, Jugador jugador2, CategoriaEfecto categoria);
}