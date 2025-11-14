package Modelo;

import java.util.List;

public interface IGestorPuntuaciones {
    
    
    boolean actualizarPuntuacion(String nombre, int puntaje);
    
    
    List<EntradaPuntuacion> obtenerTop();
}
