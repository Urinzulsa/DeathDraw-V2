package Jugador;

public class Jugador{
    private String nombre;
    private int vidas;
    private final int vidasMaximas;
    private Revolver revolver;

    public Jugador(String nombre, Revolver revolver) {
        this.nombre = nombre;
        this.vidasMaximas = 3; // Podría ser 3 o 5 dependiendo del modo de juego
        this.vidas = vidasMaximas;
        this.revolver = revolver;
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
