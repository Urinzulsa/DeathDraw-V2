
public enum Estado {
    
    NO_INICIADO("No Iniciado"),
    
    
    EN_CURSO("En Curso"),
    
    
    FINALIZADO("Finalizado");

    
    private final String descripcion;

    
    Estado(String descripcion) {
        this.descripcion = descripcion;
    }

    
    public String getDescripcion() {
        return descripcion;
    }

    
    public boolean estaActiva() {
        return this == EN_CURSO;
    }

    
    public boolean haFinalizado() {
        return this == FINALIZADO;
    }

    
    public boolean noHaIniciado() {
        return this == NO_INICIADO;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
