import Jugador.Jugador;
import Jugador.Revolver;
import Carta.Mazo;
import Carta.Carta;
import EfectosEspeciales.GestorEfectos;
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
        } else if (!nueva.esMayorQue(cartaActual) && !nueva.esMenorQue(cartaActual) && apuesta == TipoApuesta.IGUAL) {
            acerto = true;
        }

        // La nueva carta siempre queda como carta actual después de revelar
        this.cartaActual = nueva;

        if (acerto) {
            System.out.println("✅ ACIERTO! ");
            
            // Verificar si la carta tiene efecto especial
            if (nueva.tieneEfecto()) {
                System.out.println("\n🌟 ¡CARTA ESPECIAL! Efecto: " + nueva.getEfecto().getTipoEfecto());
                aplicarEfectoEspecial(jugador, nueva);
            } else {
                System.out.println("Pasa el siguiente jugador.");
            }
        } else {
            System.out.println("❌ ERROR! Se acciona el revolver...");
            boolean bala = jugador.getRevolver().girarYDisparar();
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

    // Método para aplicar efectos especiales cuando se acierta
    private void aplicarEfectoEspecial(Jugador jugadorQueAcerto, Carta carta) {
        // Determinar el otro jugador
        Jugador otroJugador = (jugadorQueAcerto == jugador1) ? jugador2 : jugador1;
        
        // Solicitar elección de moneda al jugador
        Lado eleccionMoneda = solicitarEleccionMoneda(jugadorQueAcerto);
        
        // Aplicar el efecto
        String resultado = GestorEfectos.aplicarEfectoDeCarta(carta, jugadorQueAcerto, otroJugador, eleccionMoneda);
        System.out.println(resultado);
    }

    // Método para solicitar al jugador que elija CARA o CRUZ
    private Lado solicitarEleccionMoneda(Jugador jugador) {
        System.out.println("\n🪙 " + jugador.getNombre() + ", elige CARA o CRUZ:");
        System.out.println("1. CARA");
        System.out.println("2. CRUZ");
        
        int opcion = 0;
        while (opcion < 1 || opcion > 2) {
            System.out.print("Tu elección (1-2): ");
            try {
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    opcion = Integer.parseInt(input);
                    if (opcion < 1 || opcion > 2) {
                        System.out.println("❌ Opción inválida. Elige 1 o 2.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor ingresa un número válido.");
            }
        }
        
        return opcion == 1 ? Lado.CARA : Lado.CRUZ;
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
