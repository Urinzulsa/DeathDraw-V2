package Carta;

import EfectosEspeciales.EfectoEspecial;

public interface ICarta {
    
    
    boolean esMayorQue(Carta otraCarta);
    
    
    boolean esMenorQue(Carta otraCarta);
    
    EfectoEspecial getEfecto();
    
    
    void setEfecto(EfectoEspecial efecto);
    
    
    boolean tieneEfecto();
    
    
    String getNombreValor();
    
    
    String obtenerRepresentacion();
}
