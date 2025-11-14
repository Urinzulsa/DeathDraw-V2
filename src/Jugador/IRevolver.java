package Jugador;

public interface IRevolver {
    boolean girarYDisparar();

    boolean cargarBala();

    boolean quitarBala();

    void setBalas(int numeroDeBalas);

    void vaciarRevolver();

    int contarBalas();
    
    double getProbabilidadImpacto();
}
