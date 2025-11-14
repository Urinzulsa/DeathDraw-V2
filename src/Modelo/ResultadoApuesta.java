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
public class ResultadoApuesta {

    
    private final boolean acerto;

    
    private final boolean huboImpacto;

    
    private final int vidasRestantes;

    
    private final String nombreJugador;

    
    private final Carta cartaAnterior;

    
    private final Carta nuevaCarta;

    
    private final String tipoApuesta;

    
    private final boolean tieneEfecto;

    
    private final String tipoEfecto;

    
    private final String descripcionEfecto;

    
    private final String resultadoEfecto;

    
    private final boolean esModoSolo;

    
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
    
    
    public ResultadoApuesta(boolean acerto, boolean huboImpacto, int vidasRestantes,
                           String nombreJugador, Carta cartaAnterior, Carta nuevaCarta,
                           String tipoApuesta, boolean esModoSolo) {
        this(acerto, huboImpacto, vidasRestantes, nombreJugador, cartaAnterior,
             nuevaCarta, tipoApuesta, null, null, null, esModoSolo);
    }
    
    // ================ GETTERS ================
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
