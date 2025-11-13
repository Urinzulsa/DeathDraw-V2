package Jugador;

import Exceptions.JugadorNullException;
import Exceptions.NombreInvalidoException;
import Exceptions.RevolverNullException;
import Exceptions.VidasInvalidasException;

/**
 * Representa un jugador en el juego DeathDraw.
 * <p>
 * Cada jugador tiene:
 * <ul>
 *   <li>Un nombre identificador</li>
 *   <li>Vidas actuales y máximas según el modo de juego</li>
 *   <li>Un revólver para la mecánica de ruleta rusa</li>
 * </ul>
 * </p>
 * 
 * @author DeathDraw-V2
 * @version 2.0
 */
public class Jugador {
    
    // ================ CONSTANTES ================
    /** Número de vidas por defecto en modo CLASICO */
    private static final int VIDAS_DEFAULT = 3;
    
    /** Vidas mínimas posibles */
    private static final int VIDAS_MINIMAS = 0;
    
    // ================ ATRIBUTOS ================
    /** Nombre del jugador */
    private final String nombre;
    
    /** Vidas actuales del jugador */
    private int vidas;
    
    /** Vidas máximas permitidas para este jugador */
    private final int vidasMaximas;
    
    /** Revólver del jugador */
    private Revolver revolver;

    // ================ CONSTRUCTORES ================
    
    /**
     * Constructor con vidas configurables según el modo de juego.
     * <p>
     * El jugador inicia con vidas al máximo.
     * </p>
     * 
     * @param nombre Nombre del jugador (no puede ser null o vacío)
     * @param revolver Revólver del jugador (no puede ser null)
     * @param vidasMaximas Número máximo de vidas según el modo
     * @throws NombreInvalidoException si el nombre es null o vacío
     * @throws RevolverNullException si el revolver es null
     * @throws VidasInvalidasException si vidasMaximas <= 0
     */

    public Jugador(String nombre, Revolver revolver, int vidasMaximas) {
        // Validar parámetros
        validarNombre(nombre);
        validarRevolver(revolver);
        validarVidasMaximas(vidasMaximas);
        
        this.nombre = nombre.trim();
        this.vidasMaximas = vidasMaximas;
        this.vidas = vidasMaximas; // Inicia con vidas al máximo
        this.revolver = revolver;
    }

    /**
     * Constructor por defecto para modo clásico (3 vidas).
     * 
     * @param nombre Nombre del jugador
     * @param revolver Revólver del jugador
     * @throws JugadorNullException si algún parámetro es inválido
     */
    public Jugador(String nombre, Revolver revolver) {
        this(nombre, revolver, VIDAS_DEFAULT);
    }

    // ================ VALIDACIONES ================
    
    /**
     * Valida que el nombre no sea null ni vacío.
     * 
     * @param nombre Nombre a validar
     * @throws NombreInvalidoException si el nombre es inválido
     */
    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NombreInvalidoException("El nombre del jugador no puede ser null o vacío");
        }
    }

    /**
     * Valida que el revólver no sea null.
     * 
     * @param revolver Revólver a validar
     * @throws RevolverNullException si el revólver es null
     */
    private void validarRevolver(Revolver revolver) {
        if (revolver == null) {
            throw new RevolverNullException("El revólver no puede ser null");
        }
    }

    /**
     * Valida que las vidas máximas sean positivas.
     * 
     * @param vidasMaximas Vidas máximas a validar
     * @throws VidasInvalidasException si las vidas no son válidas
     */
    private void validarVidasMaximas(int vidasMaximas) {
        if (vidasMaximas <= 0) {
            throw new VidasInvalidasException("Las vidas máximas deben ser mayores a 0");
        }
    }

    // ================ GETTERS ================
    
    /**
     * Obtiene el nombre del jugador.
     * 
     * @return Nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene las vidas actuales del jugador.
     * 
     * @return Número de vidas actuales
     */
    public int getVidas() {
        return vidas;
    }

    /**
     * Obtiene las vidas máximas del jugador.
     * 
     * @return Número de vidas máximas
     */
    public int getVidasMaximas() {
        return vidasMaximas;
    }

    /**
     * Obtiene el revólver del jugador.
     * 
     * @return Revólver actual
     */
    public Revolver getRevolver() {
        return revolver;
    }

    // ================ SETTERS ================
    
    /**
     * Establece un nuevo revólver para el jugador.
     * <p>
     * Usado principalmente en el efecto SABOTAJE para intercambiar revólveres.
     * </p>
     * 
     * @param nuevoRevolver Nuevo revólver (no puede ser null)
     * @throws RevolverNullException si el revólver es null
     */
    public void setRevolver(Revolver nuevoRevolver) {
        validarRevolver(nuevoRevolver);
        this.revolver = nuevoRevolver;
    }

    /**
     * Establece las vidas del jugador directamente.
     * <p>
     * <strong>ADVERTENCIA:</strong> Este método permite establecer vidas
     * de forma arbitraria. Preferir usar {@link #perderVida()} o {@link #ganarVida()}.
     * </p>
     * 
     * @param vidas Número de vidas a establecer
     * @throws VidasInvalidasException si las vidas son negativas o exceden el máximo
     */
    public void setVidas(int vidas) {
        if (vidas < VIDAS_MINIMAS) {
            throw new VidasInvalidasException("Las vidas no pueden ser negativas");
        }
        if (vidas > vidasMaximas) {
            throw new VidasInvalidasException(
                    "Las vidas no pueden exceder el máximo de " + vidasMaximas
            );
        }
        this.vidas = vidas;
    }

    // ================ MÉTODOS DE NEGOCIO ================
    
    /**
     * Reduce las vidas del jugador en 1.
     * <p>
     * Si el jugador ya está en 0 vidas, no hace nada.
     * </p>
     */
    public void perderVida() {
        if (vidas > VIDAS_MINIMAS) {
            vidas--;
        }
    }

    /**
     * Incrementa las vidas del jugador en 1.
     * <p>
     * No puede exceder el máximo de vidas configurado para el modo de juego.
     * </p>
     */
    public void ganarVida() {
        if (vidas < vidasMaximas) {
            vidas++;
        }
    }

    /**
     * Verifica si el jugador está vivo.
     * 
     * @return true si tiene vidas > 0, false en caso contrario
     */
    public boolean estaVivo() {
        return vidas > VIDAS_MINIMAS;
    }

    /**
     * Verifica si el jugador tiene las vidas al máximo.
     * 
     * @return true si vidas == vidasMaximas
     */
    public boolean tieneVidasMaximas() {
        return vidas == vidasMaximas;
    }

    // ================ MÉTODOS DE OBJECT ================
    
    /**
     * Representación en String del jugador.
     * 
     * @return Formato "Jugador: {nombre}"
     */
    @Override
    public String toString() {
        return "Jugador: " + nombre;
    }

    /**
     * Representación detallada del jugador con todas sus estadísticas.
     * 
     * @return String con nombre, vidas y balas del revólver
     */
    public String toStringDetallado() {
        return String.format("Jugador: %s | Vidas: %d/%d | Balas: %d",
            nombre, vidas, vidasMaximas, revolver.contarBalas());
    }
}
