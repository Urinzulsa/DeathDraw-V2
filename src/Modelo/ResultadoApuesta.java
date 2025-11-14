package Modelo;

import Carta.Carta;

/**
 * Clase que encapsula el resultado completo de una apuesta.
 * <p>
 * Este DTO (Data Transfer Object) permite separar la lógica de negocio
 * de la presentación al retornar información estructurada en lugar de
 * imprimir directamente en consola.
 * </p>
 * 
 * @author DeathDraw-V2
 * @version 2.0
 */
public class ResultadoApuesta implements Acerto {

    /** Indica si la apuesta fue acertada */
    private final boolean acerto;

    /** Indica si hubo impacto del revólver (solo para fallos) */
    private final boolean huboImpacto;

    /** Vidas restantes del jugador tras el resultado */
    private final int vidasRestantes;

    /** Nombre del jugador que apostó */
    private final String nombreJugador;

    /** Carta actual antes de la apuesta */
    private final Carta cartaAnterior;

    /** Nueva carta robada */
    private final Carta nuevaCarta;

    /** Tipo de apuesta realizada */
    private final String tipoApuesta;

    /** Indica si el jugador tiene efecto especial */
    private final boolean tieneEfecto;

    /** Tipo de efecto especial (null si no tiene) */
    private final String tipoEfecto;

    /** Descripción del efecto especial (null si no tiene) */
    private final String descripcionEfecto;

    /** Resultado del efecto especial (null si no tiene) */
    private final String resultadoEfecto;

    /** Indica si es modo SOLO (afecta mensajes) */
    private final boolean esModoSolo;

    /**
     * Constructor completo para resultado con efecto especial.
     *
     * @param acerto Si acertó la apuesta
     * @param huboImpacto Si hubo impacto del revólver
     * @param vidasRestantes Vidas restantes del jugador
     * @param nombreJugador Nombre del jugador
     * @param cartaAnterior Carta que estaba en mesa
     * @param nuevaCarta Carta robada del mazo
     * @param tipoApuesta Tipo de apuesta ("MAYOR" o "MENOR")
     * @param tipoEfecto Tipo de efecto especial
     * @param descripcionEfecto Descripción del efecto
     * @param resultadoEfecto Resultado del efecto
     * @param esModoSolo Si es modo SOLO
     */
    public ResultadoApuesta(boolean acerto, boolean huboImpacto, int vidasRestantes,
                           String nombreJugador, Carta cartaAnterior, Carta nuevaCarta,
                           String tipoApuesta, String tipoEfecto, String descripcionEfecto,
                           String resultadoEfecto, boolean esModoSolo) {
        this.acerto = acerto;
        this.huboImpacto = huboImpacto;
        this.vidasRestantes = vidasRestantes;
        this.nombreJugador = nombreJugador;
        this.cartaAnterior = cartaAnterior;
        this.nuevaCarta = nuevaCarta;
        this.tipoApuesta = tipoApuesta;
        this.tieneEfecto = (tipoEfecto != null);
        this.tipoEfecto = tipoEfecto;
        this.descripcionEfecto = descripcionEfecto;
        this.resultadoEfecto = resultadoEfecto;
        this.esModoSolo = esModoSolo;
    }
    
    /**
     * Constructor para resultado sin efecto especial.
     *
     * @param acerto Si acertó la apuesta
     * @param huboImpacto Si hubo impacto del revólver
     * @param vidasRestantes Vidas restantes del jugador
     * @param nombreJugador Nombre del jugador
     * @param cartaAnterior Carta que estaba en mesa
     * @param nuevaCarta Carta robada del mazo
     * @param tipoApuesta Tipo de apuesta ("MAYOR" o "MENOR")
     * @param esModoSolo Si es modo SOLO
     */
    public ResultadoApuesta(boolean acerto, boolean huboImpacto, int vidasRestantes,
                           String nombreJugador, Carta cartaAnterior, Carta nuevaCarta,
                           String tipoApuesta, boolean esModoSolo) {
        this(acerto, huboImpacto, vidasRestantes, nombreJugador, cartaAnterior,
             nuevaCarta, tipoApuesta, null, null, null, esModoSolo);
    }
    
    // ================ GETTERS ================
    @Override
    public boolean acerto() {
        return acerto;
    }
    
    public boolean huboImpacto() {
        return huboImpacto;
    }
    
    public int getVidasRestantes() {
        return vidasRestantes;
    }
    
    public String getNombreJugador() {
        return nombreJugador;
    }
    
    public Carta getCartaAnterior() {
        return cartaAnterior;
    }
    
    public Carta getNuevaCarta() {
        return nuevaCarta;
    }
    
    public String getTipoApuesta() {
        return tipoApuesta;
    }
    
    public boolean tieneEfecto() {
        return tieneEfecto;
    }
    
    public String getTipoEfecto() {
        return tipoEfecto;
    }
    
    public String getDescripcionEfecto() {
        return descripcionEfecto;
    }
    
    public String getResultadoEfecto() {
        return resultadoEfecto;
    }
    
    public boolean esModoSolo() {
        return esModoSolo;
    }
}
