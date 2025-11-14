package Jugador;

public interface IRevolver {
    
    
    boolean girarYDisparar();
    
    
    boolean cargarBala();
    
    
    boolean quitarBala();
    
    
    void setBalas(int numeroDeBalas);
    
    
    void vaciarRevolver();
    
    
    void llenarRevolver();
    
    
    int contarBalas();
    
    
    int getCapacidad();
    
    
    boolean estaVacio();
    
    
    boolean estaLleno();
    
    
    double getProbabilidadImpacto();
}
