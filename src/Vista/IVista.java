package Vista;

import Jugador.Jugador;
import Carta.Carta;
import Modelo.ResultadoApuesta;

public interface IVista {
    
    
    void mostrarEstadoJuegoMultijugador(Jugador jugador1, Jugador jugador2, Carta cartaActual);
    
    
    void mostrarEstadoJuegoSolo(Jugador jugador, Carta cartaActual, int rachaActual);
    
    
    void mostrarEncabezadoTurno(String nombreJugador);
    
    
    void mostrarEncabezadoTurnoSolo(String nombreJugador);
    
    
    void mostrarResultadoApuesta(ResultadoApuesta resultado);
    
    
    void mostrarVictoria(String nombreGanador);
    
    
    void mostrarFinSolo(int rachaMaxima);
    
    
    void mostrarError(String mensaje);
    
    
    void esperarContinuar();
}
