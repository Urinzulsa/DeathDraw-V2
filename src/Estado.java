/**
 * Enumeración que representa los posibles estados de una partida.
 * <p>
 * Flujo normal de estados:
 * <pre>
 * NO_INICIADO → EN_CURSO → FINALIZADO
 * </pre>
 * </p>
 * 
 * @author DeathDraw-V2
 * @version 2.0
 */
public enum Estado {
    /**
     * Estado inicial de la partida.
     * <p>
     * La partida está creada pero no ha comenzado.
     * Se permiten agregar jugadores pero no realizar apuestas.
     * </p>
     */
    NO_INICIADO("No Iniciado"),
    
    /**
     * Estado activo de juego.
     * <p>
     * La partida está en curso.
     * Se permiten apuestas y mecánica de juego.
     * </p>
     */
    EN_CURSO("En Curso"),
    
    /**
     * Estado final de la partida.
     * <p>
     * La partida ha terminado (algún jugador perdió todas las vidas).
     * No se permiten más acciones de juego.
     * </p>
     */
    FINALIZADO("Finalizado");

    /** Descripción legible del estado */
    private final String descripcion;

    /**
     * Constructor del enum.
     * 
     * @param descripcion Descripción legible del estado
     */
    Estado(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la descripción legible del estado.
     * 
     * @return Descripción del estado
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Verifica si la partida está activa (en curso).
     * 
     * @return true si es EN_CURSO, false en caso contrario
     */
    public boolean estaActiva() {
        return this == EN_CURSO;
    }

    /**
     * Verifica si la partida ha finalizado.
     * 
     * @return true si es FINALIZADO, false en caso contrario
     */
    public boolean haFinalizado() {
        return this == FINALIZADO;
    }

    /**
     * Verifica si la partida no ha iniciado.
     * 
     * @return true si es NO_INICIADO, false en caso contrario
     */
    public boolean noHaIniciado() {
        return this == NO_INICIADO;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
