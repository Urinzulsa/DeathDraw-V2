package Modelo;

/**
 * Representa una entrada de highscore (nombre y puntaje).
 */
public class HighscoreEntry implements Comparable<HighscoreEntry> {
    private final String nombre;
    private final int puntaje;

    public HighscoreEntry(String nombre, int puntaje) {
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
    public int compareTo(HighscoreEntry o) {
        // Orden descendente por puntaje
        return Integer.compare(o.puntaje, this.puntaje);
    }
}

