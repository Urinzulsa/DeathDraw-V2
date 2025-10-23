import Jugador.Jugador;
import Jugador.Revolver;
import Carta.Mazo;

public class Partida {
    private Estado estado;
    private Jugador jugador1;
    private Jugador jugador2;
    private Mazo mazo;
    private int turnosContador;

    public Partida() {
        this.estado = Estado.NO_INICIADO;
        this.jugador1 = null; // no players at start
        this.jugador2 = null;
        this.turnosContador = 1;
    }

    public String agregarJugador(String nombre) throws JugadoresCompletosException {
        if (jugador1 == null) {
            Revolver revolver = new Revolver();
            this.jugador1 = new Jugador(nombre, revolver);
            return "JUGADOR AGREGADO CON EXITO";
        } else if (jugador2 == null) {
            Revolver revolver = new Revolver();
            this.jugador2 = new Jugador(nombre, revolver);
            return "JUGADOR AGREGADO CON EXITO";
        }
        throw new JugadoresCompletosException();
    }


    public String iniciarPartida() throws PartidaIniciadaException {
        if (jugador1 == null || jugador2 == null) {
            throw new IllegalArgumentException("EL JUGADOR SE ENCUENTRA VACIO");
        }
        if (estado != Estado.NO_INICIADO) {
            throw new PartidaIniciadaException();
        }
        this.estado = Estado.EN_CURSO;
        this.mazo = Mazo.crearMazoEstandar();
        return "La partida ha comenzado entre " + jugador1.getNombre() + " y " + jugador2.getNombre() + "\nLA PRIMER CARTA DEL MAZO ES: " + mazo.robarCarta();
    }

    public void Apuesta(Jugador jugador) {
        if ( )
    }

    public Jugador turno() {
        if (jugador1.getVidas() <= 0 || jugador2.getVidas() <= 0) {
            throw  new IllegalArgumentException("LA PARTIDA HA FINALIZADO");
        }
        if (turnosContador%2==0){
            turnosContador++;
            return jugador2;
        } else {
            turnosContador++;
            return jugador1;
        }
    }
}
