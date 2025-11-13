public enum ModoJuego {
    CLASICO(3, 1, 0.15),
    SOBRECARGA(5, 2, 0.20),
    MUERTE_SUBITA(1, 6, 0.05),
    SOLO(1,1,0.00);

    private final int vidasIniciales;
    private final int balasIniciales;
    private final double probabilidadEspeciales;

    ModoJuego(int vidasIniciales, int balasIniciales, double probabilidadEspeciales) {
        this.vidasIniciales = vidasIniciales;
        this.balasIniciales = balasIniciales;
        this.probabilidadEspeciales = probabilidadEspeciales;
    }

    public int getVidasIniciales() {
        return vidasIniciales;
    }

    public int getBalasIniciales() {
        return balasIniciales;
    }

    public double getProbabilidadEspeciales() {
        return probabilidadEspeciales;
    }
    
    /**
     * Convierte un String al enum ModoJuego correspondiente.
     * <p>
     * Valores válidos: "CLASICO", "SOBRECARGA", "MUERTE_SUBITA", "SOLO"
     * </p>
     * 
     * @param nombre Nombre del modo (case-sensitive)
     * @return El enum ModoJuego correspondiente
     * @throws IllegalArgumentException si el nombre no es válido
     */
    public static ModoJuego fromString(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del modo no puede ser null o vacío");
        }
        
        try {
            return valueOf(nombre.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Modo de juego inválido: '" + nombre + "'. Valores válidos: CLASICO, SOBRECARGA, MUERTE_SUBITA, SOLO"
            );
        }
    }

    @Override
    public String toString() {
        return switch (this) {
            case CLASICO -> "Modo Clásico (3 vidas, 1 bala, 15% especiales)";
            case SOBRECARGA -> "Modo Sobrecarga (5 vidas, 2 balas, 20% especiales)";
            case MUERTE_SUBITA -> "Modo Muerte Súbita (1 vida, 6 balas, 5% especiales)";
            case SOLO -> "Modo Solo (Una sola chance, si pierde termina el juego)";
        };
    }
}
