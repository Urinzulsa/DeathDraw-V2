package Carta;

public enum Palo {
    
    CORAZONES("Corazones"),
    
    
    DIAMANTES("Diamantes"),
    
    
    TREBOLES("Tréboles"),
    
    
    PICAS("Picas");

    
    private final String nombre;

    Palo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
