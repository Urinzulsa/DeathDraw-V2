
public enum TipoApuesta {
    
    MAYOR("Mayor", ">"),
    
    
    MENOR("Menor", "<");

    
    private final String nombre;
    
    
    private final String simbolo;

    
    TipoApuesta(String nombre, String simbolo) {
        this.nombre = nombre;
        this.simbolo = simbolo;
    }

    
    public String getNombre() {
        return nombre;
    }

    
    public String getSimbolo() {
        return simbolo;
    }

    
    public String getDescripcion() {
        return simbolo + " " + nombre;
    }
    
    
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
