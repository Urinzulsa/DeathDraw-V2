package Carta;

/**
 * Enumeración que representa los cuatro palos de una baraja francesa estándar.
 * <p>
 * Palos:
 * <ul>
 *   <li>♥ CORAZONES (rojo)</li>
 *   <li>♦ DIAMANTES (rojo)</li>
 *   <li>♣ TREBOLES (negro)</li>
 *   <li>♠ PICAS (negro)</li>
 * </ul>
 * </p>
 * 
 * @author DeathDraw-V2
 * @version 2.0
 */
public enum Palo {
    /**
     * Palo de Corazones (♥) - Color rojo
     */
    CORAZONES("Corazones", "♥", true),
    
    /**
     * Palo de Diamantes (♦) - Color rojo
     */
    DIAMANTES("Diamantes", "♦", true),
    
    /**
     * Palo de Tréboles (♣) - Color negro
     */
    TREBOLES("Tréboles", "♣", false),
    
    /**
     * Palo de Picas (♠) - Color negro
     */
    PICAS("Picas", "♠", false);

    /** Nombre descriptivo del palo */
    private final String nombre;
    
    /** Símbolo Unicode del palo */
    private final String simbolo;
    
    /** Indica si el palo es de color rojo */
    private final boolean esRojo;

    /**
     * Constructor del enum.
     * 
     * @param nombre Nombre del palo
     * @param simbolo Símbolo Unicode
     * @param esRojo true si es rojo, false si es negro
     */
    Palo(String nombre, String simbolo, boolean esRojo) {
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.esRojo = esRojo;
    }

    /**
     * Obtiene el nombre descriptivo del palo.
     * 
     * @return Nombre del palo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el símbolo Unicode del palo.
     * 
     * @return Símbolo (♥, ♦, ♣, ♠)
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * Verifica si el palo es de color rojo.
     * 
     * @return true si es CORAZONES o DIAMANTES
     */
    public boolean esRojo() {
        return esRojo;
    }

    /**
     * Verifica si el palo es de color negro.
     * 
     * @return true si es TREBOLES o PICAS
     */
    public boolean esNegro() {
        return !esRojo;
    }

    /**
     * Obtiene el nombre con el símbolo.
     * 
     * @return Formato "{símbolo} {nombre}"
     */
    public String getConSimbolo() {
        return simbolo + " " + nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
