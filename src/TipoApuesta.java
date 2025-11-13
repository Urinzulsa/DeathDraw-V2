/**
 * Enumeración que representa los tipos de apuesta disponibles en el juego.
 * <p>
 * El jugador apuesta si la siguiente carta será mayor o menor que la carta actual.
 * La comparación se hace solo por valor numérico (1-13), ignorando palos.
 * </p>
 * 
 * @author DeathDraw-V2
 * @version 2.0
 */
public enum TipoApuesta {
    /**
     * Apuesta a que la siguiente carta tendrá un valor MAYOR.
     * <p>
     * Ejemplo: Carta actual = 5, nueva carta = 8 → ACIERTO
     * </p>
     */
    MAYOR("Mayor", ">"),
    
    /**
     * Apuesta a que la siguiente carta tendrá un valor MENOR.
     * <p>
     * Ejemplo: Carta actual = 10, nueva carta = 3 → ACIERTO
     * </p>
     */
    MENOR("Menor", "<");

    /** Nombre descriptivo de la apuesta */
    private final String nombre;
    
    /** Símbolo de la apuesta */
    private final String simbolo;

    /**
     * Constructor del enum.
     * 
     * @param nombre Nombre descriptivo
     * @param simbolo Símbolo de comparación
     */
    TipoApuesta(String nombre, String simbolo) {
        this.nombre = nombre;
        this.simbolo = simbolo;
    }

    /**
     * Obtiene el nombre descriptivo de la apuesta.
     * 
     * @return Nombre de la apuesta
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el símbolo de la apuesta.
     * 
     * @return Símbolo (> o <)
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * Obtiene una descripción detallada de la apuesta.
     * 
     * @return Descripción con símbolo y nombre
     */
    public String getDescripcion() {
        return simbolo + " " + nombre;
    }
    
    /**
     * Convierte un String al enum TipoApuesta correspondiente.
     * <p>
     * Valores válidos: "MAYOR", "MENOR"
     * </p>
     * 
     * @param nombre Nombre de la apuesta (case-sensitive)
     * @return El enum TipoApuesta correspondiente
     * @throws IllegalArgumentException si el nombre no es válido
     */
    public static TipoApuesta fromString(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la apuesta no puede ser null o vacío");
        }
        
        try {
            return valueOf(nombre.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Tipo de apuesta inválido: '" + nombre + "'. Valores válidos: MAYOR, MENOR"
            );
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}
