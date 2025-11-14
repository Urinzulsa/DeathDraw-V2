import EfectosEspeciales.TipoEfecto;
import Exceptions.*;
import Jugador.Jugador;
import Jugador.Revolver;
import Carta.Mazo;
import Carta.Carta;
import EfectosEspeciales.GestorEfectos;
import Moneda.Moneda;
import Moneda.Lado;
import Modelo.ResultadoApuesta;

import java.util.Scanner;

public class Partida implements IPartida {
    
    
    private static class InfoEfecto {
        String tipoEfecto;
        String descripcion;
        String resultado;
        
        InfoEfecto(String tipoEfecto, String descripcion, String resultado) {
            this.tipoEfecto = tipoEfecto;
            this.descripcion = descripcion;
            this.resultado = resultado;
        }
    }
    
    // ================ CONSTANTES ================
    
    private static final int MAX_JUGADORES = 2;
    
    
    private static final int TURNO_INICIAL = 1;
    
    
    private static final String MENSAJE_JUGADOR_AGREGADO = "JUGADOR AGREGADO CON EXITO";
    
    // ================ ATRIBUTOS ================
    
    private Estado estado;
    
    
    private Jugador jugador1;
    
    
    private Jugador jugador2;
    
    
    private Mazo mazo;
    
    
    private int turnosContador;
    
    
    private Carta cartaActual;
    
    
    private final ModoJuego modoJuego;
    
    
    private final Scanner scanner;

    // ================ CONSTRUCTORES ================
    
    
    public Partida(ModoJuego modo) {
        if (modo == null) {
            throw new NullPointerException("El modo de juego no puede ser null");
        }
        this.estado = Estado.NO_INICIADO;
        this.jugador1 = null;
        this.jugador2 = null;
        this.turnosContador = TURNO_INICIAL;
        this.cartaActual = null;
        this.modoJuego = modo;
        this.scanner = new Scanner(System.in);
    }

    
    public Partida() {
        this(ModoJuego.CLASICO);
    }

    // ================ GETTERS ================
    
    
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

    
    public Estado getEstado() {
        return estado;
    }

    // ================ MÉTODOS PÚBLICOS ================
    
    
    public void incrementarTurno() {
        this.turnosContador++;
    }

    
    public String agregarJugador(String nombre) throws JugadoresCompletosException {
        // Validar nombre
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador no puede ser null o vacío");
        }
        
        // Agregar jugador en la primera posición disponible
        if (jugador1 == null) {
            jugador1 = crearJugadorConRevolver(nombre);
            return MENSAJE_JUGADOR_AGREGADO;
        } else if (jugador2 == null) {
            jugador2 = crearJugadorConRevolver(nombre);
            return MENSAJE_JUGADOR_AGREGADO;
        }
        
        throw new JugadoresCompletosException();
    }

    
    private Jugador crearJugadorConRevolver(String nombre) {
        Revolver revolver = new Revolver();
        revolver.setBalas(modoJuego.getBalasIniciales());
        return new Jugador(nombre, revolver, modoJuego.getVidasIniciales());
    }

    
    public String iniciarPartida() throws PartidaIniciadaException, JugadorNullException {
        // Validar jugadores según el modo
        validarJugadoresParaIniciar();
        
        // Validar que la partida no esté ya iniciada
        if (estado != Estado.NO_INICIADO) {
            throw new PartidaIniciadaException();
        }
        
        // Cambiar estado a EN_CURSO
        this.estado = Estado.EN_CURSO;

        // Crear mazo según el modo de juego
        // En modo SOLO solo se incluyen efectos que no requieren segundo jugador
        boolean esModo2v2 = !esModoSolo();
        this.mazo = Mazo.crearMazoEstandar(modoJuego.getProbabilidadEspeciales(), esModo2v2);

        // Robar la carta inicial
        this.cartaActual = mazo.robarCarta();
        
        // Retornar mensaje según el modo
        return construirMensajeInicio();
    }

    
    private void validarJugadoresParaIniciar() throws JugadorNullException {
        if (esModoSolo()) {
            if (jugador1 == null) {
                throw new JugadorNullException("EL JUGADOR SE ENCUENTRA VACIO");
            }
        } else {
            if (jugador1 == null || jugador2 == null) {
                throw new JugadorNullException("SE REQUIEREN 2 JUGADORES PARA ESTE MODO");
            }
        }
    }

    
    private String construirMensajeInicio() {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("La partida ha comenzado en ").append(modoJuego).append("\n");
        
        if (esModoSolo()) {
            mensaje.append(jugador1.getNombre());
        } else {
            mensaje.append(jugador1.getNombre())
                   .append(" y ")
                   .append(jugador2.getNombre());
        }
        
        mensaje.append("\nLA PRIMER CARTA DEL MAZO ES: ").append(cartaActual);
        return mensaje.toString();
    }

    
    private boolean esModoSolo() {
        return modoJuego.equals(ModoJuego.SOLO);
    }
    
    private void validarPartidaActiva() throws PartidaNoIniciadaException, CartaNulaException {
        if (estado != Estado.EN_CURSO) {
            throw new PartidaNoIniciadaException("La partida no está en curso");
        }
        if (cartaActual == null) {
            throw new CartaNulaException("No hay carta actual para comparar");
        }
        if (modoJuego == null) {
            throw new PartidaNoIniciadaException("Modo de juego no definido");
        }
    }

    
    public ResultadoApuesta procesarApuesta(Jugador jugador, TipoApuesta apuesta)
            throws PartidaNoIniciadaException, CartaNulaException {
        // Validar parámetros
        if (jugador == null || apuesta == null) {
            throw new IllegalArgumentException("El jugador y la apuesta no pueden ser null");
        }
        
        // Validar estado de la partida
        validarPartidaActiva();
        
        // Guardar carta anterior para el resultado
        Carta cartaAnterior = this.cartaActual;
        
        // Robar nueva carta
        Carta nuevaCarta = mazo.robarCarta();

        // Evaluar si acertó la apuesta
        boolean acerto = evaluarApuesta(nuevaCarta, apuesta);
        
        // Actualizar carta actual
        this.cartaActual = nuevaCarta;

        // Procesar resultado de la apuesta
        if (acerto) {
            return procesarAcierto(nuevaCarta, jugador, cartaAnterior, apuesta);
        } else {
            return procesarFallo(jugador, cartaAnterior, nuevaCarta, apuesta);
        }
    }

    
    @Deprecated
    private void mostrarInformacionApuesta(Jugador jugador, TipoApuesta apuesta, Carta nuevaCarta) {
        System.out.println(jugador.getNombre() + " apuesta: " + apuesta + 
                         " | Carta actual: " + cartaActual + 
                         " | Carta a revelar: " + nuevaCarta);
    }

    
    private boolean evaluarApuesta(Carta nuevaCarta, TipoApuesta apuesta) {
        return (nuevaCarta.esMayorQue(cartaActual) && apuesta == TipoApuesta.MAYOR) ||
               (nuevaCarta.esMenorQue(cartaActual) && apuesta == TipoApuesta.MENOR);
    }

    
    private ResultadoApuesta procesarAcierto(Carta carta, Jugador jugador,
                                             Carta cartaAnterior, TipoApuesta apuesta) {
        if (carta.tieneEfecto()) {
            InfoEfecto infoEfecto = manejarEfectoDeCarta(carta, jugador);
            return new ResultadoApuesta(
                true,  // acertó
                false, // no hubo impacto (acertó)
                jugador.getVidas(),
                jugador.getNombre(),
                cartaAnterior,
                carta,
                apuesta.toString(),
                infoEfecto.tipoEfecto,
                infoEfecto.descripcion,
                infoEfecto.resultado,
                esModoSolo()
            );
        } else {
            return new ResultadoApuesta(
                true,  // acertó
                false, // no hubo impacto
                jugador.getVidas(),
                jugador.getNombre(),
                cartaAnterior,
                carta,
                apuesta.toString(),
                esModoSolo()
            );
        }
    }

    
    private ResultadoApuesta procesarFallo(Jugador jugador, Carta cartaAnterior,
                                           Carta nuevaCarta, TipoApuesta apuesta) {
        if (esModoSolo()) {
            return procesarFalloModoSolo(jugador, cartaAnterior, nuevaCarta, apuesta);
        } else {
            return procesarFalloMultijugador(jugador, cartaAnterior, nuevaCarta, apuesta);
        }
    }

    
    private ResultadoApuesta procesarFalloModoSolo(Jugador jugador, Carta cartaAnterior,
                                                   Carta nuevaCarta, TipoApuesta apuesta) {
        jugador.perderVida();
        this.estado = Estado.FINALIZADO;
        
        return new ResultadoApuesta(
            false, // falló
            true,  // hubo impacto (modo SOLO siempre impacta)
            jugador.getVidas(),
            jugador.getNombre(),
            cartaAnterior,
            nuevaCarta,
            apuesta.toString(),
            true  // es modo SOLO
        );
    }
    
    
    private ResultadoApuesta procesarFalloMultijugador(Jugador jugador, Carta cartaAnterior,
                                                       Carta nuevaCarta, TipoApuesta apuesta) {
        boolean hayBala = jugador.getRevolver().girarYDisparar();
        
        if (hayBala) {
            // Impacto: pierde vida y resetea revólver
            jugador.perderVida();
            jugador.getRevolver().setBalas(1);
        } else {
            // Sin impacto: agrega una bala
            jugador.getRevolver().cargarBala();
        }
        
        return new ResultadoApuesta(
            false,  // falló
            hayBala, // si hubo impacto
            jugador.getVidas(),
            jugador.getNombre(),
            cartaAnterior,
            nuevaCarta,
            apuesta.toString(),
            false  // no es modo SOLO
        );
    }    
    private InfoEfecto manejarEfectoDeCarta(Carta carta, Jugador jugador) 
            throws CartaNulaException, EfectoInvalidoException {
        // Validar carta
        if (carta == null) {
            throw new CartaNulaException("LA CARTA ESPECIAL ES NULA");
        }
        
        // Obtener información del efecto
        TipoEfecto tipoEfecto = carta.getEfecto().getTipoEfecto();
        String descripcion = GestorEfectos.obtenerDescripcionEfecto(tipoEfecto);

        // Determinar oponente (null en modo SOLO)
        Jugador oponente = determinarOponente(jugador);
        
        // Aplicar efecto según su tipo
        String resultadoEfecto = aplicarEfectoSegunTipo(carta, jugador, oponente);

        // Verificar si el efecto terminó la partida
        verificarFinalizacionPorEfecto();
        
        return new InfoEfecto(
            tipoEfecto.toString(),
            descripcion,
            resultadoEfecto
        );
    }

    
    @Deprecated
    private void mostrarInformacionEfecto(Carta carta) {
        System.out.println("\n" + "🌟".repeat(60));
        System.out.println("¡CARTA ESPECIAL! Efecto: " + carta.getEfecto().getTipoEfecto());
        String descripcion = GestorEfectos.obtenerDescripcionEfecto(carta.getEfecto().getTipoEfecto());
        System.out.println("Descripción: " + descripcion);
        System.out.println("🌟".repeat(60));
    }

    
    private Jugador determinarOponente(Jugador jugador) {
        if (esModoSolo()) {
            return null;
        }
        return (jugador == jugador1) ? jugador2 : jugador1;
    }

    
    private String aplicarEfectoSegunTipo(Carta carta, Jugador jugador, Jugador oponente) 
            throws EfectoInvalidoException {
        TipoEfecto tipo = carta.getEfecto().getTipoEfecto();
        
        // Efectos neutrales se aplican sin moneda
        if (esEfectoNeutral(tipo)) {
            return aplicarEfectoNeutral(carta, jugador, oponente);
        } else {
            return aplicarEfectoConMoneda(carta, jugador, oponente);
        }
    }

    
    private boolean esEfectoNeutral(TipoEfecto tipo) {
        return tipo == TipoEfecto.SABOTAJE || tipo == TipoEfecto.CAOS;
    }

    
    private String aplicarEfectoNeutral(Carta carta, Jugador jugador, Jugador oponente) 
            throws EfectoInvalidoException {
        try {
            return GestorEfectos.aplicarEfectoDeCarta(carta, jugador, oponente, null);
        } catch (EfectoInvalidoException e) {
            return "ERROR: " + e.getMessage();
        }
    }
    
    
    private String aplicarEfectoConMoneda(Carta carta, Jugador jugador, Jugador oponente) 
            throws EfectoInvalidoException {
        Lado eleccionMoneda = Moneda.solicitarEleccion(jugador, scanner);
        try {
            return GestorEfectos.aplicarEfectoDeCarta(carta, jugador, oponente, eleccionMoneda);
        } catch (EfectoInvalidoException e) {
            return "ERROR: " + e.getMessage();
        }
    }    
    private void verificarFinalizacionPorEfecto() {
        if (esModoSolo()) {
            // En modo SOLO verificar solo jugador1
            if (jugador1.getVidas() <= 0) {
                estado = Estado.FINALIZADO;
            }
        } else {
            // En multijugador verificar ambos jugadores
            if (jugador1.getVidas() <= 0 || jugador2.getVidas() <= 0) {
                estado = Estado.FINALIZADO;
            }
        }
    }

    
    @Deprecated
    public void turno() {
        // Validar que la partida esté correctamente inicializada
        if (!esModoSolo() && (jugador1 == null || jugador2 == null)) {
            throw new IllegalStateException("Faltan jugadores para iniciar los turnos");
        }
        if (estado != Estado.EN_CURSO) {
            throw new IllegalStateException("La partida no está en curso");
        }

        // Loop principal de turnos automáticos
        while (estado != Estado.FINALIZADO) {
            Jugador actual = determinarJugadorActual();
            TipoApuesta apuesta = generarApuestaAleatoria();

            // Procesar apuesta
            procesarApuesta(actual, apuesta);
            
            // Verificar condición de victoria y actualizar turno
            if (!esModoSolo()) {
                verificarYMostrarGanador();
                turnosContador++;
            } else {
                if (estado == Estado.FINALIZADO) {
                    System.out.println("PARTIDA FINALIZADA. Puntaje final: ");
                    return;
                }
            }

            // Separador visual
            System.out.println("---");
        }
    }

    
    private Jugador determinarJugadorActual() {
        if (esModoSolo()) {
            return jugador1;
        }
        // En multijugador alterna por turno par/impar
        return (turnosContador % 2 == 0) ? jugador2 : jugador1;
    }

    
    private TipoApuesta generarApuestaAleatoria() {
        TipoApuesta[] opciones = {TipoApuesta.MAYOR, TipoApuesta.MENOR};
        return opciones[(int) (Math.random() * opciones.length)];
    }

    
    private void verificarYMostrarGanador() {
        if (jugador1.getVidas() <= 0 || jugador2.getVidas() <= 0) {
            estado = Estado.FINALIZADO;
            Jugador ganador = (jugador1.getVidas() > 0) ? jugador1 : jugador2;
            System.out.println("PARTIDA FINALIZADA. Ganador: " + ganador.getNombre());
        }
    }

    
    public void setEstado(Estado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser null");
        }
        this.estado = estado;
    }
}

