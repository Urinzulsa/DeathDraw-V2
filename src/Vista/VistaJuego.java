package Vista;

import Jugador.Jugador;
import Jugador.Revolver;
import Carta.Carta;
import Modelo.ResultadoApuesta;

/**
 * Clase responsable de mostrar el estado del juego en consola.
 * <p>
 * Responsabilidades:
 * <ul>
 *   <li>Mostrar estado de jugadores (vidas, balas)</li>
 *   <li>Mostrar carta actual en mesa</li>
 *   <li>Mostrar información de turnos</li>
 *   <li>Mostrar resultados de apuestas</li>
 *   <li>Mostrar efectos especiales</li>
 * </ul>
 * 
 * Esta clase NO contiene lógica de negocio, solo presentación.
 * </p>
 * 
 * @author DeathDraw-V2
 * @version 2.0
 */
public class VistaJuego {
    
    /** Interfaz de consola para output */
    private final InterfazConsola consola;
    
    /**
     * Constructor que inyecta la dependencia de InterfazConsola.
     */
    public VistaJuego() {
        this.consola = InterfazConsola.obtenerInstancia();
    }
    
    // ================ MÉTODOS DE VISUALIZACIÓN DE ESTADO ================
    
    /**
     * Muestra el estado completo del juego para modo multijugador.
     * 
     * @param jugador1 Primer jugador
     * @param jugador2 Segundo jugador
     * @param cartaActual Carta actual en mesa
     */
    public void mostrarEstadoJuegoMultijugador(Jugador jugador1, Jugador jugador2, Carta cartaActual) {
        consola.mostrarMensaje("\nESTADO DEL JUEGO:");
        consola.mostrarMensaje("┌" + "─".repeat(58) + "┐");
        
        // Mostrar estado de jugador 1
        String estadoJ1 = String.format("│ %-20s %s Vidas: %d      %s Balas: %d      │",
                jugador1.getNombre(),
                InterfazConsola.EMOJI_VIDAS,
                jugador1.getVidas(),
                InterfazConsola.EMOJI_BALAS,
                contarBalas(jugador1.getRevolver()));
        consola.mostrarMensaje(estadoJ1);
        
        // Mostrar estado de jugador 2
        String estadoJ2 = String.format("│ %-20s %s Vidas: %d      %s Balas: %d      │",
                jugador2.getNombre(),
                InterfazConsola.EMOJI_VIDAS,
                jugador2.getVidas(),
                InterfazConsola.EMOJI_BALAS,
                contarBalas(jugador2.getRevolver()));
        consola.mostrarMensaje(estadoJ2);
        
        consola.mostrarMensaje("└" + "─".repeat(58) + "┘");
        
        // Mostrar carta actual
        mostrarCartaActual(cartaActual);
    }
    
    /**
     * Muestra el estado del juego para modo SOLO.
     * 
     * @param jugador Jugador único
     * @param cartaActual Carta actual en mesa
     * @param rachaActual Racha de aciertos consecutivos
     */
    public void mostrarEstadoJuegoSolo(Jugador jugador, Carta cartaActual, int rachaActual) {
        consola.mostrarMensaje("\nESTADO DEL JUEGO:");
        consola.mostrarMensaje("┌" + "─".repeat(58) + "┐");
        
        String estado = String.format("│ %20s | Racha actual: %-20d│",
                jugador.getNombre(), rachaActual);
        consola.mostrarMensaje(estado);
        
        consola.mostrarMensaje("└" + "─".repeat(58) + "┘");
        
        // Mostrar carta actual
        mostrarCartaActual(cartaActual);
    }
    
    /**
     * Muestra la carta actual en mesa.
     * 
     * @param carta Carta a mostrar
     */
    public void mostrarCartaActual(Carta carta) {
        if (carta != null) {
            consola.mostrarMensaje("\nCARTA ACTUAL EN MESA: " + carta.obtenerRepresentacion());
            consola.mostrarMensaje("");
        }
    }
    
    /**
     * Cuenta el número de balas en un revólver.
     * 
     * @param revolver Revólver a evaluar
     * @return Número de balas cargadas
     */
    private int contarBalas(Revolver revolver) {
        return revolver.contarBalas();
    }
    
    // ================ MÉTODOS DE VISUALIZACIÓN DE TURNOS ================
    
    /**
     * Muestra el encabezado de turno para un jugador.
     * 
     * @param nombreJugador Nombre del jugador del turno
     */
    public void mostrarEncabezadoTurno(String nombreJugador) {
        consola.mostrarMensaje("\n" + InterfazConsola.SEPARADOR_PRINCIPAL);
        consola.mostrarMensaje("TURNO DE: " + nombreJugador.toUpperCase());
        consola.mostrarMensaje(InterfazConsola.SEPARADOR_PRINCIPAL);
    }
    
    /**
     * Muestra el encabezado de turno para modo SOLO.
     * 
     * @param nombreJugador Nombre del jugador
     */
    public void mostrarEncabezadoTurnoSolo(String nombreJugador) {
        consola.mostrarMensaje("\n" + InterfazConsola.SEPARADOR_PRINCIPAL);
        consola.mostrarMensaje("TURNO DE: " + nombreJugador.toUpperCase() + " (SOLO)");
        consola.mostrarMensaje(InterfazConsola.SEPARADOR_PRINCIPAL);
    }
    
    // ================ MÉTODOS DE VISUALIZACIÓN DE APUESTAS ================
    
    /**
     * Muestra el resultado completo de una apuesta usando ResultadoApuesta.
     * <p>
     * Este método centraliza toda la presentación del resultado incluyendo:
     * - Información de la apuesta (cartas, tipo)
     * - Resultado (acierto/fallo)
     * - Efecto del revólver (si aplica)
     * - Efecto especial (si existe)
     * </p>
     * 
     * @param resultado Objeto con toda la información del resultado
     */
    public void mostrarResultadoApuesta(ResultadoApuesta resultado) {
        // Mostrar información de la apuesta
        mostrarInformacionApuesta(
            resultado.getNombreJugador(),
            resultado.getTipoApuesta(),
            resultado.getCartaAnterior(),
            resultado.getNuevaCarta()
        );
        
        // Mostrar resultado (acierto o fallo)
        if (resultado.acerto()) {
            mostrarAcierto();
            
            // Si tiene efecto especial, mostrarlo
            if (resultado.tieneEfecto()) {
                mostrarEfectoEspecial(resultado.getTipoEfecto(), resultado.getDescripcionEfecto());
                mostrarResultadoEfecto(resultado.getResultadoEfecto());
            } else {
                // Sin efecto especial
                if (resultado.esModoSolo()) {
                    mostrarContinuacionSolo();
                } else {
                    mostrarContinuacionSinEfecto();
                }
            }
        } else {
            // Mostrar fallo y efecto del revólver
            mostrarFallo();
            
            if (resultado.huboImpacto()) {
                if (resultado.esModoSolo()) {
                    mostrarImpactoSolo(resultado.getNombreJugador());
                } else {
                    mostrarImpacto(resultado.getNombreJugador(), resultado.getVidasRestantes());
                }
            } else {
                mostrarSinImpacto(resultado.getNombreJugador());
            }
        }
    }
    
    /**
     * Muestra información de una apuesta realizada.
     * 
     * @param nombreJugador Nombre del jugador
     * @param apuesta Tipo de apuesta realizada
     * @param cartaActual Carta actual en mesa
     * @param nuevaCarta Carta que se está revelando
     */
    public void mostrarInformacionApuesta(String nombreJugador, String apuesta, 
                                         Carta cartaActual, Carta nuevaCarta) {
        String mensaje = String.format("%s apuesta: %s | Carta actual: %s | Carta a revelar: %s",
                nombreJugador, apuesta, cartaActual, nuevaCarta);
        consola.mostrarMensaje(mensaje);
    }
    
    /**
     * Muestra el resultado de una apuesta acertada.
     */
    public void mostrarAcierto() {
        consola.mostrarExito("ACIERTO!");
    }
    
    /**
     * Muestra el resultado de una apuesta fallida.
     */
    public void mostrarFallo() {
        consola.mostrarError("ERROR! Se acciona el revolver...");
    }
    
    /**
     * Muestra mensaje de continuación tras acierto sin efecto.
     */
    public void mostrarContinuacionSinEfecto() {
        consola.mostrarMensaje("Pasa el siguiente jugador.");
    }
    
    /**
     * Muestra mensaje de continuación en modo SOLO.
     */
    public void mostrarContinuacionSolo() {
        consola.mostrarMensaje("Excelente, continua jugando");
    }
    
    // ================ MÉTODOS DE VISUALIZACIÓN DE REVÓLVER ================
    
    /**
     * Muestra el resultado de un disparo con impacto.
     * 
     * @param nombreJugador Nombre del jugador impactado
     * @param vidasRestantes Vidas que le quedan al jugador
     */
    public void mostrarImpacto(String nombreJugador, int vidasRestantes) {
        String mensaje = String.format("%s %s recibe un impacto y pierde 1 vida. Vidas restantes: %d",
                InterfazConsola.EMOJI_IMPACTO, nombreJugador, vidasRestantes);
        consola.mostrarMensaje(mensaje);
    }
    
    /**
     * Muestra el resultado de un disparo sin impacto.
     * 
     * @param nombreJugador Nombre del jugador
     */
    public void mostrarSinImpacto(String nombreJugador) {
        String mensaje = String.format("%s %s no recibió impacto. Se agrega una bala al revólver, apreta bien el chupete",
                InterfazConsola.EMOJI_SUERTE, nombreJugador);
        consola.mostrarMensaje(mensaje);
    }
    
    /**
     * Muestra el resultado de un disparo con impacto en modo SOLO.
     * 
     * @param nombreJugador Nombre del jugador
     */
    public void mostrarImpactoSolo(String nombreJugador) {
        String mensaje = String.format("%s recibe un impacto y pierde 1 vida.\nEL JUEGO TERMINO",
                nombreJugador);
        consola.mostrarMensaje(mensaje);
    }
    
    // ================ MÉTODOS DE VISUALIZACIÓN DE EFECTOS ================
    
    /**
     * Muestra el encabezado de un efecto especial.
     * 
     * @param tipoEfecto Tipo de efecto activado
     * @param descripcion Descripción del efecto
     */
    public void mostrarEfectoEspecial(String tipoEfecto, String descripcion) {
        consola.mostrarMensaje("\n" + InterfazConsola.EMOJI_ESTRELLA.repeat(60));
        consola.mostrarMensaje("¡CARTA ESPECIAL! Efecto: " + tipoEfecto);
        consola.mostrarMensaje("Descripción: " + descripcion);
        consola.mostrarMensaje(InterfazConsola.EMOJI_ESTRELLA.repeat(60));
    }
    
    /**
     * Muestra el resultado de un efecto especial.
     * 
     * @param resultado Mensaje del resultado del efecto
     */
    public void mostrarResultadoEfecto(String resultado) {
        if (resultado != null && !resultado.isEmpty()) {
            consola.mostrarMensaje("\n" + resultado);
        }
    }
    
    // ================ MÉTODOS DE VISUALIZACIÓN DE FIN DE JUEGO ================
    
    /**
     * Muestra la pantalla de victoria para modo multijugador.
     * 
     * @param nombreGanador Nombre del jugador ganador
     */
    public void mostrarVictoria(String nombreGanador) {
        consola.mostrarMensaje("\n" + InterfazConsola.EMOJI_TROFEO.repeat(30));
        consola.mostrarMensaje("      ¡PARTIDA FINALIZADA!");
        consola.mostrarMensaje("      GANADOR: " + nombreGanador.toUpperCase());
        consola.mostrarMensaje(InterfazConsola.EMOJI_TROFEO.repeat(30) + "\n");
    }
    
    /**
     * Muestra la pantalla de fin de juego para modo SOLO.
     * 
     * @param rachaMaxima Racha máxima de aciertos conseguida
     */
    public void mostrarFinSolo(int rachaMaxima) {
        consola.mostrarMensaje("\n" + InterfazConsola.EMOJI_MUERTE.repeat(15));
        consola.mostrarMensaje("      ¡FIN DEL JUEGO SOLO!");
        consola.mostrarMensaje("      Racha máxima: " + rachaMaxima + " aciertos");
        consola.mostrarMensaje(InterfazConsola.EMOJI_MUERTE.repeat(15) + "\n");
    }
    
    // ================ MÉTODOS DE UTILIDAD ================
    
    /**
     * Muestra un mensaje de error genérico.
     * 
     * @param mensaje Mensaje de error
     */
    public void mostrarError(String mensaje) {
        consola.mostrarError(mensaje);
    }
    
    /**
     * Espera que el usuario presione ENTER.
     */
    public void esperarContinuar() {
        consola.esperarEnter();
    }
}
