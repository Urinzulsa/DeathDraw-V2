package Carta;

public enum Palo {
    
    CORAZONES("Corazones", "♥", true),
    
    
    DIAMANTES("Diamantes", "♦", true),
    
    
    TREBOLES("Tréboles", "♣", false),
    
    
    PICAS("Picas", "♠", false);

    
    private final String nombre;
    
    
    private final String simbolo;
    
    
    private final boolean esRojo;

    
    Palo(String nombre, String simbolo, boolean esRojo) {
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.esRojo = esRojo;
    }

    
    public String getNombre() {
        return nombre;
    }

    
    public String getSimbolo() {
        return simbolo;
    }

    
    public boolean esRojo() {
        return esRojo;
    }

    
    public boolean esNegro() {
        return !esRojo;
    }

    
    public String getConSimbolo() {
        return simbolo + " " + nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
