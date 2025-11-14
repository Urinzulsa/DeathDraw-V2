package Vista;

public interface IMenu {
    
    
    void mostrarBienvenida();
    
    
    String seleccionarModo();
    
    
    String[] solicitarNombresJugadores(boolean esModoSolo);
    
    
    String solicitarApuesta(String nombreJugador);
    
    
    void mostrarInicioPartida(String mensaje);
    
    
    void mostrarJugadorAgregado(String mensaje);
    
    
    void mostrarError(String mensaje);
}
