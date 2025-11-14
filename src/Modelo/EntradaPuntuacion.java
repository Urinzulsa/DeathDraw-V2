package Modelo;

/**
 * Representa una entrada de puntuación (nombre y puntaje).
 */
public class EntradaPuntuacion implements Comparable<EntradaPuntuacion> {
    private final String nombre;
    private final int puntaje;

    public EntradaPuntuacion(String nombre, int puntaje) {
        this.nombre = nombre;
        this.puntaje = puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    @Override
    public int compareTo(EntradaPuntuacion o) {
        // Orden descendente por puntaje
        return Integer.compare(o.puntaje, this.puntaje);
    }
}

