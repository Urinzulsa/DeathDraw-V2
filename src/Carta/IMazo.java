package Carta;

import java.util.List;

public interface IMazo {
    
    
    Carta robarCarta();
    
    
    void mezclar();
    
    
    List<Carta> verProximasCartas(int n);
}
