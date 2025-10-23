import Jugador.Jugador;
import Jugador.Revolver;
import Carta.Mazo;
import Carta.Carta;
import EfectosEspeciales.GestorEfectos;
import Moneda.Moneda;
import Moneda.Lado;

import java.util.Scanner;

public class Partida {
    private Estado estado;
    private Jugador jugador1;
    private Jugador jugador2;
    private Mazo mazo;
    private int turnosContador;
    private Carta cartaActual;
    private ModoJuego modoJuego;
    private Scanner scanner;

    // Constructor con modo de juego específico
    public Partida(ModoJuego modo) {
        this.estado = Estado.NO_INICIADO;
        this.jugador1 = null;
        this.jugador2 = null;
        this.turnosContador = 1;
        this.cartaActual = null;
        this.modoJuego = modo;
        this.scanner = new Scanner(System.in);
    }

    // Constructor por defecto (modo clásico)
    public Partida() {
        this(ModoJuego.CLASICO);
    }

    public ModoJuego getModoJuego() {
        return modoJuego;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public Carta getCartaActual() {
        return cartaActual;
    }

    public int getTurnosContador() {
        return turnosContador;
    }

    public void incrementarTurno() {
        this.turnosContador++;
    }

    public String agregarJugador(String nombre) throws JugadoresCompletosException {
        if (jugador1 == null) {
            Revolver revolver = new Revolver();
            revolver.setBalas(modoJuego.getBalasIniciales());
            this.jugador1 = new Jugador(nombre, revolver, modoJuego.getVidasIniciales());
            return "JUGADOR AGREGADO CON EXITO";
        } else if (jugador2 == null) {
            Revolver revolver = new Revolver();
            revolver.setBalas(modoJuego.getBalasIniciales());
            this.jugador2 = new Jugador(nombre, revolver, modoJuego.getVidasIniciales());
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
        this.mazo = Mazo.crearMazoEstandar(modoJuego.getProbabilidadEspeciales());
        this.cartaActual = mazo.robarCarta();
        return "La partida ha comenzado en " + modoJuego + "\n" + 
               jugador1.getNombre() + " y " + jugador2.getNombre() + 
               "\nLA PRIMER CARTA DEL MAZO ES: " + cartaActual;
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
        }
        // La nueva carta siempre queda como carta actual después de revelar
        this.cartaActual = nueva;

        if (acerto) {
            System.out.println("✅ ACIERTO! ");

            if (nueva.tieneEfecto()) {
                manejarEfectoDeCarta(nueva, jugador);
            } else {
                System.out.println("Pasa el siguiente jugador.");
            }
            if (modoJuego.equals(ModoJuego.SOLO)){
                System.out.println("Excelente, continua jugando");
            }
        } else {
            System.out.println("❌ ERROR! Se acciona el revolver...");
            boolean bala = jugador.getRevolver().girarYDisparar();
            if (modoJuego.equals(ModoJuego.SOLO)) {
                System.out.println( jugador.getNombre() +" recibe un impacto y pierde 1 vida.\n EL JUEGO TERMINO");
                this.estado=Estado.FINALIZADO;
                return;
            }
            if (bala) {
                jugador.perderVida();
                System.out.println("💥 " + jugador.getNombre() + " recibe un impacto y pierde 1 vida. Vidas restantes: " + jugador.getVidas());
            } else {
                // No había bala: se agrega una al revólver del jugador
                boolean cargada = jugador.getRevolver().cargarBala();
                System.out.println("🍀 " + jugador.getNombre() + " no recibió impacto. Se agrega una bala al revólver: " + (cargada ? "OK" : "REVOLVER LLENO"));
            }
        }
    }

    private void manejarEfectoDeCarta(Carta carta, Jugador jugador) {
        System.out.println("\n" + "🌟".repeat(60));
        System.out.println("¡CARTA ESPECIAL! Efecto: " + carta.getEfecto().getTipoEfecto());
        String descripcion = GestorEfectos.obtenerDescripcionEfecto(carta.getEfecto().getTipoEfecto());
        System.out.println("Descripción: " + descripcion);

        Jugador oponente = (jugador == jugador1) ? jugador2 : jugador1;
        String resultadoEfecto;

        // Comprobar si el efecto es neutral ANTES de pedir la moneda
        if (carta.getEfecto().getTipoEfecto() == EfectosEspeciales.TipoEfecto.SABOTAJE || carta.getEfecto().getTipoEfecto() == EfectosEspeciales.TipoEfecto.CAOS) {
            System.out.println("🌟".repeat(60));
            resultadoEfecto = GestorEfectos.aplicarEfectoDeCarta(carta, jugador, oponente, null);
        } else {
            System.out.println("🌟".repeat(60));
            Lado eleccionMoneda = Moneda.solicitarEleccion(jugador, scanner);
            resultadoEfecto = GestorEfectos.aplicarEfectoDeCarta(carta, jugador, oponente, eleccionMoneda);
        }
        System.out.println("\n" + resultadoEfecto);

        // Comprobar si el efecto finalizó la partida
        if (jugador1.getVidas() <= 0 || jugador2.getVidas() <= 0) {
            estado = Estado.FINALIZADO;
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

        while (!estado.equals(Estado.FINALIZADO)) {
            Jugador actual;
            if (modoJuego.equals(ModoJuego.SOLO)) {
                actual=jugador1;
            } else if (turnosContador % 2 == 0) {
                actual = jugador2;
            } else {
                actual = jugador1;
            }

            // Elegir apuesta de forma aleatoria entre MAYOR y MENOR (se puede incluir IGUAL si se desea)
            TipoApuesta[] opciones = {TipoApuesta.MAYOR, TipoApuesta.MENOR};
            TipoApuesta apuesta = opciones[(int) (Math.random() * opciones.length)];

            Apuesta(actual, apuesta);
            if (!modoJuego.equals(ModoJuego.SOLO)) {
                if (jugador1.getVidas() <= 0 || jugador2.getVidas() <= 0) {
                    estado = Estado.FINALIZADO;
                    Jugador ganador = jugador1.getVidas() > 0 ? jugador1 : jugador2;
                    System.out.println("PARTIDA FINALIZADA. Ganador: " + ganador.getNombre());
                }
                turnosContador++;
            }

            // Separación visual
            System.out.println("---");
        }

        if (modoJuego.equals(ModoJuego.SOLO)) {
            System.out.println("PARTIDA FINALIZADA. Puntaje final: "); /// A ARREGLAR;



    }

}
}

