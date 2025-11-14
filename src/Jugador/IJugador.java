package Jugador;

public interface IJugador {
    
    
    String getNombre();
    
    
    int getVidas();
    
    
    int getVidasMaximas();
    
    
    Revolver getRevolver();
    
    
    void setRevolver(Revolver nuevoRevolver);
    
    
    void setVidas(int vidas);
    
    
    void perderVida();
    
    
    void ganarVida();
    
    
    boolean estaVivo();
    
    
    boolean tieneVidasMaximas();
}
