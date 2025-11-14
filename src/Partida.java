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

/**
 * Clase que representa una partida del juego DeathDraw.
 * <p>
 * Gestiona el flujo completo del juego incluyendo: 
 * - Estados de la partida (NO_INICIADO, EN_CURSO, FINALIZADO)
 * - Turnos de jugadores
 * - Apuestas y comparación de cartas
 * - Mecánica del revólver y pérdida de vidas
 * - Aplicación de efectos especiales
 * </p>
 * 
 * @author DeathDraw-V2
 * @version 2.0
 */
public class Partida {
    
    /**
     * Clase interna para encapsular información de efectos especiales.
     */
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
    /** Número máximo de jugadores permitidos por partida */
    private static final int MAX_JUGADORES = 2;
    
    /** Turno inicial de la partida */
    private static final int TURNO_INICIAL = 1;
    
    /** Mensaje de éxito al agregar jugador */
    private static final String MENSAJE_JUGADOR_AGREGADO = "JUGADOR AGREGADO CON EXITO";
    
    // ================ ATRIBUTOS ================
    /** Estado actual de la partida */
    private Estado estado;
    
    /** Primer jugador de la partida */
    private Jugador jugador1;
    
    /** Segundo jugador de la partida (null en modo SOLO) */
    private Jugador jugador2;
    
    /** Mazo de cartas de la partida */
    private Mazo mazo;
    
    /** Contador de turnos jugados */
    private int turnosContador;
    
    /** Carta actualmente en mesa */
    private Carta cartaActual;
    
    /** Modo de juego seleccionado */
    private final ModoJuego modoJuego;
    
    /** Scanner para entrada de usuario (moneda) */
    private final Scanner scanner;

    // ================ CONSTRUCTORES ================
    
    /**
     * Constructor con modo de juego específico.
     * <p>
     * Inicializa una partida en estado NO_INICIADO con el modo especificado.
     * Los jugadores deben agregarse mediante {@link #agregarJugador(String)} 
     * antes de iniciar la partida.
     * </p>
     * 
     * @param modo El modo de juego para esta partida (CLASICO, SOBRECARGA, etc.)
     * @throws NullPointerException si modo es null
     */
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

    /**
     * Constructor por defecto que inicializa con modo CLASICO.
     * <p>
     * Equivalente a {@code new Partida(ModoJuego.CLASICO)}
     * </p>
     */
    public Partida() {
        this(ModoJuego.CLASICO);
    }

    // ================ GETTERS ================
    
    /**
     * Obtiene el modo de juego de la partida.
     * 
     * @return El modo de juego configurado
     */
    public ModoJuego getModoJuego() {
        return modoJuego;
    }

    /**
     * Obtiene el primer jugador de la partida.
     * 
     * @return El jugador 1, o null si no ha sido agregado
     */
    public Jugador getJugador1() {
        return jugador1;
    }

    /**
     * Obtiene el segundo jugador de la partida.
     * 
     * @return El jugador 2, o null si no ha sido agregado o es modo SOLO
     */
    public Jugador getJugador2() {
        return jugador2;
    }

    /**
     * Obtiene la carta actualmente en mesa.
     * 
     * @return La carta actual, o null si la partida no ha iniciado
     */
    public Carta getCartaActual() {
        return cartaActual;
    }

    /**
     * Obtiene el número de turnos jugados.
     * 
     * @return Contador de turnos (inicia en 1)
     */
    public int getTurnosContador() {
        return turnosContador;
    }

    /**
     * Obtiene el estado actual de la partida.
     * 
     * @return Estado actual (NO_INICIADO, EN_CURSO, o FINALIZADO)
     */
    public Estado getEstado() {
        return estado;
    }

    // ================ MÉTODOS PÚBLICOS ================
    
    /**
     * Incrementa el contador de turnos.
     * <p>
     * Este método debe ser llamado al final de cada turno para mantener
     * el seguimiento correcto de los turnos.
     * </p>
     */
    public void incrementarTurno() {
        this.turnosContador++;
    }

    /**
     * Agrega un jugador a la partida.
     * <p>
     * Los jugadores se agregan secuencialmente:
     * <ol>
     *   <li>Primera llamada: agrega jugador1</li>
     *   <li>Segunda llamada: agrega jugador2 (solo si no es modo SOLO)</li>
     * </ol>
     * Cada jugador se inicializa con un revólver configurado según el modo de juego.
     * </p>
     * 
     * @param nombre Nombre del jugador a agregar (no puede ser null o vacío)
     * @return Mensaje de confirmación
     * @throws JugadoresCompletosException si ya se agregaron los 2 jugadores permitidos
     * @throws IllegalArgumentException si el nombre es null o vacío
     */
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

    /**
     * Método auxiliar para crear un jugador con su revólver configurado.
     * <p>
     * Encapsula la lógica de creación de jugador para evitar duplicación de código.
     * El revólver se inicializa con el número de balas según el modo de juego.
     * </p>
     * 
     * @param nombre Nombre del jugador
     * @return Jugador configurado con revólver según el modo de juego
     */
    private Jugador crearJugadorConRevolver(String nombre) {
        Revolver revolver = new Revolver();
        revolver.setBalas(modoJuego.getBalasIniciales());
        return new Jugador(nombre, revolver, modoJuego.getVidasIniciales());
    }

    /**
     * Inicia la partida creando el mazo y robando la carta inicial.
     * <p>
     * Requisitos previos:
     * <ul>
     *   <li>Modo SOLO: debe haber al menos 1 jugador agregado</li>
     *   <li>Modo Multijugador: deben haber 2 jugadores agregados</li>
     *   <li>La partida debe estar en estado NO_INICIADO</li>
     * </ul>
     * 
     * Al iniciar:
     * <ol>
     *   <li>Valida que haya suficientes jugadores</li>
     *   <li>Cambia el estado a EN_CURSO</li>
     *   <li>Crea el mazo con probabilidad de efectos según el modo</li>
     *   <li>Roba la carta inicial que queda en mesa</li>
     * </ol>
     * </p>
     * 
     * @return Mensaje descriptivo del inicio de partida con jugadores y carta inicial
     * @throws PartidaIniciadaException si la partida ya fue iniciada
     * @throws JugadorNullException si falta algún jugador requerido
     */
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

    /**
     * Valida que haya suficientes jugadores para iniciar según el modo de juego.
     * 
     * @throws JugadorNullException si falta algún jugador requerido
     */
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

    /**
     * Construye el mensaje de inicio de partida según el modo de juego.
     * 
     * @return Mensaje formateado con información de inicio
     */
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

    /**
     * Verifica si el modo de juego es SOLO.
     * 
     * @return true si es modo SOLO, false en caso contrario
     */
    private boolean esModoSolo() {
        return modoJuego.equals(ModoJuego.SOLO);
    }
    /**
     * Valida que la partida esté en condiciones de aceptar apuestas.
     * <p>
     * Verifica:
     * <ul>
     *   <li>Estado debe ser EN_CURSO</li>
     *   <li>Debe haber una carta actual para comparar</li>
     *   <li>El modo de juego debe estar definido</li>
     * </ul>
     * </p>
     * 
     * @throws PartidaNoIniciadaException si la partida no está en curso
     * @throws CartaNulaException si no hay carta actual
     */
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

    /**
     * Procesa una apuesta de un jugador comparando con la siguiente carta del mazo.
     * <p>
     * Flujo de la apuesta:
     * <ol>
     *   <li>Valida que la partida esté activa</li>
     *   <li>Roba una nueva carta del mazo</li>
     *   <li>Compara la nueva carta con la actual según la apuesta</li>
     *   <li>Si acierta: aplica efecto especial si existe</li>
     *   <li>Si falla: aplica mecánica del revólver</li>
     *   <li>La nueva carta queda como carta actual</li>
     * </ol>
     * 
     * Mecánica del Revólver:
     * <ul>
     *   <li>Modo SOLO: muerte instantánea (sin probabilidad)</li>
     *   <li>Modo Multijugador: 
     *       <ul>
     *         <li>Si hay bala → pierde vida y resetea a 1 bala</li>
     *         <li>Si no hay bala → agrega una bala al revólver</li>
     *       </ul>
     *   </li>
     * </ul>
     * </p>
     * 
     * @param jugador El jugador que realiza la apuesta
     * @param apuesta MAYOR o MENOR
     * @return ResultadoApuesta con toda la información del resultado
     * @throws PartidaNoIniciadaException si la partida no está en curso
     * @throws CartaNulaException si no hay carta actual
     * @throws IllegalArgumentException si jugador o apuesta son null
     */
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

    /**
     * Muestra la información de la apuesta realizada.
     * 
     * @param jugador Jugador que apuesta
     * @param apuesta Tipo de apuesta
     * @param nuevaCarta Carta que se está revelando
     * @deprecated Este método ya no se usa, la vista maneja la presentación
     */
    @Deprecated
    private void mostrarInformacionApuesta(Jugador jugador, TipoApuesta apuesta, Carta nuevaCarta) {
        System.out.println(jugador.getNombre() + " apuesta: " + apuesta + 
                         " | Carta actual: " + cartaActual + 
                         " | Carta a revelar: " + nuevaCarta);
    }

    /**
     * Evalúa si la apuesta fue correcta comparando las cartas.
     * 
     * @param nuevaCarta Carta robada del mazo
     * @param apuesta Tipo de apuesta (MAYOR o MENOR)
     * @return true si acertó, false si falló
     */
    private boolean evaluarApuesta(Carta nuevaCarta, TipoApuesta apuesta) {
        return (nuevaCarta.esMayorQue(cartaActual) && apuesta == TipoApuesta.MAYOR) ||
               (nuevaCarta.esMenorQue(cartaActual) && apuesta == TipoApuesta.MENOR);
    }

    /**
     * Procesa el resultado cuando el jugador acierta la apuesta.
     * <p>
     * Si la carta tiene efecto especial, lo aplica mediante el gestor de efectos.
     * </p>
     * 
     * @param carta Carta que acaba de ser revelada
     * @param jugador Jugador que acertó
     * @param cartaAnterior Carta que estaba en mesa
     * @param apuesta Tipo de apuesta realizada
     * @return ResultadoApuesta con los datos del acierto
     */
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

    /**
     * Procesa el resultado cuando el jugador falla la apuesta.
     * <p>
     * Aplica la mecánica del revólver según el modo de juego:
     * <ul>
     *   <li>SOLO: muerte instantánea</li>
     *   <li>Multijugador: mecánica probabilística del revólver</li>
     * </ul>
     * </p>
     * 
     * @param jugador Jugador que falló la apuesta
     * @param cartaAnterior Carta que estaba en mesa
     * @param nuevaCarta Nueva carta robada
     * @param apuesta Tipo de apuesta realizada
     * @return ResultadoApuesta con los datos del fallo
     */
    private ResultadoApuesta procesarFallo(Jugador jugador, Carta cartaAnterior,
                                           Carta nuevaCarta, TipoApuesta apuesta) {
        if (esModoSolo()) {
            return procesarFalloModoSolo(jugador, cartaAnterior, nuevaCarta, apuesta);
        } else {
            return procesarFalloMultijugador(jugador, cartaAnterior, nuevaCarta, apuesta);
        }
    }

    /**
     * Procesa el fallo en modo SOLO: muerte instantánea.
     * 
     * @param jugador Jugador que falló
     * @param cartaAnterior Carta que estaba en mesa
     * @param nuevaCarta Nueva carta robada
     * @param apuesta Tipo de apuesta realizada
     * @return ResultadoApuesta con los datos del fallo en modo SOLO
     */
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
    
    /**
     * Procesa el fallo en modo multijugador aplicando la mecánica del revólver.
     * <p>
     * Mecánica:
     * <ul>
     *   <li>Si sale bala: pierde vida y resetea revólver a 1 bala</li>
     *   <li>Si no sale bala: agrega una bala al revólver</li>
     * </ul>
     * </p>
     * 
     * @param jugador Jugador que falló
     * @param cartaAnterior Carta que estaba en mesa
     * @param nuevaCarta Nueva carta robada
     * @param apuesta Tipo de apuesta realizada
     * @return ResultadoApuesta con los datos del fallo multijugador
     */
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
    }    /**
     * Maneja la aplicación de un efecto especial de una carta.
     * <p>
     * Flujo:
     * <ol>
     *   <li>Determina el oponente (puede ser null en modo SOLO)</li>
     *   <li>Si es efecto neutral (SABOTAJE/CAOS): aplica directamente</li>
     *   <li>Si es efecto positivo/negativo: lanza moneda y aplica según resultado</li>
     *   <li>Verifica si el efecto terminó la partida</li>
     * </ol>
     * </p>
     * 
     * @param carta Carta con efecto especial
     * @param jugador Jugador que sacó la carta
     * @return InfoEfecto con toda la información del efecto aplicado
     * @throws CartaNulaException si la carta es null
     * @throws EfectoInvalidoException si el efecto no es válido
     */
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

    /**
     * Muestra la información visual del efecto especial.
     * 
     * @param carta Carta con efecto
     * @deprecated Este método ya no se usa, la vista maneja la presentación
     */
    @Deprecated
    private void mostrarInformacionEfecto(Carta carta) {
        System.out.println("\n" + "🌟".repeat(60));
        System.out.println("¡CARTA ESPECIAL! Efecto: " + carta.getEfecto().getTipoEfecto());
        String descripcion = GestorEfectos.obtenerDescripcionEfecto(carta.getEfecto().getTipoEfecto());
        System.out.println("Descripción: " + descripcion);
        System.out.println("🌟".repeat(60));
    }

    /**
     * Determina quién es el oponente del jugador actual.
     * 
     * @param jugador Jugador actual
     * @return El oponente, o null si es modo SOLO
     */
    private Jugador determinarOponente(Jugador jugador) {
        if (esModoSolo()) {
            return null;
        }
        return (jugador == jugador1) ? jugador2 : jugador1;
    }

    /**
     * Aplica el efecto según su tipo (neutral o con moneda).
     * 
     * @param carta Carta con efecto
     * @param jugador Jugador que sacó la carta
     * @param oponente Oponente (puede ser null)
     * @return Mensaje del resultado del efecto
     * @throws EfectoInvalidoException si hay error al aplicar el efecto
     */
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

    /**
     * Verifica si un efecto es neutral (SABOTAJE o CAOS).
     * 
     * @param tipo Tipo de efecto
     * @return true si es neutral
     */
    private boolean esEfectoNeutral(TipoEfecto tipo) {
        return tipo == TipoEfecto.SABOTAJE || tipo == TipoEfecto.CAOS;
    }

    /**
     * Aplica un efecto neutral directamente sin lanzar moneda.
     * 
     * @param carta Carta con efecto
     * @param jugador Jugador actual
     * @param oponente Oponente (puede ser null)
     * @return Resultado del efecto
     * @throws EfectoInvalidoException si hay error
     */
    private String aplicarEfectoNeutral(Carta carta, Jugador jugador, Jugador oponente) 
            throws EfectoInvalidoException {
        try {
            return GestorEfectos.aplicarEfectoDeCarta(carta, jugador, oponente, null);
        } catch (EfectoInvalidoException e) {
            return "ERROR: " + e.getMessage();
        }
    }
    
    /**
     * Aplica un efecto con mecánica de moneda.
     * 
     * @param carta Carta con efecto
     * @param jugador Jugador actual
     * @param oponente Oponente (puede ser null)
     * @return Resultado del efecto
     * @throws EfectoInvalidoException si hay error
     */
    private String aplicarEfectoConMoneda(Carta carta, Jugador jugador, Jugador oponente) 
            throws EfectoInvalidoException {
        Lado eleccionMoneda = Moneda.solicitarEleccion(jugador, scanner);
        try {
            return GestorEfectos.aplicarEfectoDeCarta(carta, jugador, oponente, eleccionMoneda);
        } catch (EfectoInvalidoException e) {
            return "ERROR: " + e.getMessage();
        }
    }    /**
     * Verifica si algún jugador murió por el efecto y finaliza la partida.
     */
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

    /**
     * Ejecuta turnos automáticos de manera aleatoria hasta que la partida finalice.
     * <p>
     * <strong>NOTA:</strong> Este método es para testing/demo automática. 
     * Para juego real usar el flujo manual desde Main.
     * </p>
     * <p>
     * Comportamiento:
     * <ul>
     *   <li>Alterna turnos entre jugadores automáticamente</li>
     *   <li>Hace apuestas aleatorias (MAYOR o MENOR)</li>
     *   <li>Continúa hasta que un jugador pierda todas las vidas</li>
     * </ul>
     * </p>
     * 
     * @throws IllegalStateException si la partida no está correctamente inicializada
     * @deprecated Este método es solo para demostración automática
     */
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

    /**
     * Determina qué jugador debe jugar el turno actual.
     * 
     * @return El jugador del turno actual
     */
    private Jugador determinarJugadorActual() {
        if (esModoSolo()) {
            return jugador1;
        }
        // En multijugador alterna por turno par/impar
        return (turnosContador % 2 == 0) ? jugador2 : jugador1;
    }

    /**
     * Genera una apuesta aleatoria para modo automático.
     * 
     * @return MAYOR o MENOR aleatoriamente
     */
    private TipoApuesta generarApuestaAleatoria() {
        TipoApuesta[] opciones = {TipoApuesta.MAYOR, TipoApuesta.MENOR};
        return opciones[(int) (Math.random() * opciones.length)];
    }

    /**
     * Verifica si hay un ganador y muestra el mensaje correspondiente.
     */
    private void verificarYMostrarGanador() {
        if (jugador1.getVidas() <= 0 || jugador2.getVidas() <= 0) {
            estado = Estado.FINALIZADO;
            Jugador ganador = (jugador1.getVidas() > 0) ? jugador1 : jugador2;
            System.out.println("PARTIDA FINALIZADA. Ganador: " + ganador.getNombre());
        }
    }

    /**
     * Establece el estado de la partida.
     * <p>
     * <strong>ADVERTENCIA:</strong> Usar con cuidado. Este método permite
     * cambiar el estado de la partida manualmente, lo cual puede romper
     * la lógica del juego si se usa incorrectamente.
     * </p>
     * 
     * @param estado Nuevo estado de la partida
     * @throws IllegalArgumentException si estado es null
     */
    public void setEstado(Estado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser null");
        }
        this.estado = estado;
    }
}

