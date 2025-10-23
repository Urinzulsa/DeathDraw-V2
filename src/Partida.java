import Jugador.Jugador;
import Jugador.Revolver;
import Carta.Mazo;
import Carta.Carta;

public class Partida {
    private Estado estado;
    private Jugador jugador1;
    private Jugador jugador2;
    private Mazo mazo;
    private int turnosContador;
    private Carta cartaActual;

    public Partida() {
        this.estado = Estado.NO_INICIADO;
        this.jugador1 = null; // no players at start
        this.jugador2 = null;
        this.turnosContador = 1;
        this.cartaActual = null;
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
        this.cartaActual = mazo.robarCarta();
        return "La partida ha comenzado entre " + jugador1.getNombre() + " y " + jugador2.getNombre() + "\nLA PRIMER CARTA DEL MAZO ES: " + cartaActual;
    }

    // El jugador pasa su apuesta (MAYOR, MENOR o IGUAL) para la carta que se roba del mazo
    public void Apuesta(Jugador jugador, TipoApuesta apuesta) {
        if (estado != Estado.EN_CURSO) {
            throw new IllegalStateException("La partida no está en curso");
        }
        if (cartaActual == null) {
            throw new IllegalStateException("No hay carta actual para comparar");
        }

        Carta nueva = mazo.robarCarta();
        System.out.println(jugador.getNombre() + " apuesta: " + apuesta + " | Carta actual: " + cartaActual + " | Carta a revelar: " + nueva);

        boolean acerto = false;
        if (nueva.esMayorQue(cartaActual) && apuesta == TipoApuesta.MAYOR) {
            acerto = true;
        } else if (nueva.esMenorQue(cartaActual) && apuesta == TipoApuesta.MENOR) {
            acerto = true;
        } else if (!nueva.esMayorQue(cartaActual) && !nueva.esMenorQue(cartaActual) && apuesta == TipoApuesta.IGUAL) {
            acerto = true;
        }

        // La nueva carta siempre queda como carta actual después de revelar
        this.cartaActual = nueva;

        if (acerto) {
            System.out.println("ACIERTO! Pasa el siguiente jugador.");
            // no otras acciones: el siguiente turno se maneja por el contador en turno()
        } else {
            System.out.println("ERROR! Se acciona el revolver...");
            boolean bala = jugador.getRevolver().girarYDisparar();
            if (bala) {
                jugador.perderVida();
                System.out.println(jugador.getNombre() + " recibe un impacto y pierde 1 vida. Vidas restantes: " + jugador.getVidas());
            } else {
                // No había bala: se agrega una al revólver del jugador
                boolean cargada = jugador.getRevolver().cargarBala();
                System.out.println(jugador.getNombre() + " no recibió impacto. Se agrega una bala al revólver: " + (cargada ? "OK" : "REVOLVER LLENO"));
            }
        }
    }

    // Ejecuta turnos alternados hasta que alguno pierda todas sus vidas
    public void turno() {
        if (jugador1 == null || jugador2 == null) {
            throw new IllegalStateException("Faltan jugadores para iniciar los turnos");
        }
        if (estado != Estado.EN_CURSO) {
            throw new IllegalStateException("La partida no está en curso");
        }

        while (jugador1.getVidas() > 0 && jugador2.getVidas() > 0) {
            Jugador actual;
            if (turnosContador % 2 == 0) {
                actual = jugador2;
            } else {
                actual = jugador1;
            }

            // Elegir apuesta de forma aleatoria entre MAYOR y MENOR (se puede incluir IGUAL si se desea)
            TipoApuesta[] opciones = {TipoApuesta.MAYOR, TipoApuesta.MENOR, TipoApuesta.IGUAL};
            TipoApuesta apuesta = opciones[(int) (Math.random() * opciones.length)];

            Apuesta(actual, apuesta);

            turnosContador++;

            // Breve separación visual en la consola
            System.out.println("---");
        }

        estado = Estado.FINALIZADO;
        Jugador ganador = jugador1.getVidas() > 0 ? jugador1 : jugador2;
        System.out.println("PARTIDA FINALIZADA. Ganador: " + ganador.getNombre());
    }

}
