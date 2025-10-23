package Jugador;

public class Jugador{
    private String nombre;
    private int vidas;
    private final int vidasMaximas;
    private Revolver revolver;

    // Constructor con vidas configurables según el modo de juego
    public Jugador(String nombre, Revolver revolver, int vidasMaximas) {
        this.nombre = nombre;
        this.vidasMaximas = vidasMaximas;
        this.vidas = vidasMaximas;
        this.revolver = revolver;
    }

    // Constructor por defecto (modo clásico: 3 vidas)
    public Jugador(String nombre, Revolver revolver) {
        this(nombre, revolver, 3);
    }

    public String getNombre() {
        return nombre;
    }

    public int getVidas() {
        return vidas;
    }

    public Revolver getRevolver() {
        return revolver;
    }

    public void setRevolver(Revolver nuevoRevolver) {
        this.revolver = nuevoRevolver;
    }

    public void perderVida() {
        if (vidas > 0) {
            vidas--;
        }
    }

    public void ganarVida() {
        if (vidas < vidasMaximas) {
            vidas++;
        }
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    @Override
    public String toString() {
        return "Jugador: " + nombre;
    }
}
