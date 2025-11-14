package Jugador;

public interface IJugador {

    String getNombre();

    int getVidas();
    
    Revolver getRevolver();

    void setRevolver(Revolver nuevoRevolver);
    
    void perderVida();

    void ganarVida();
}
