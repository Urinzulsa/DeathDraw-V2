package Vista;

import Jugador.Jugador;
import Jugador.Revolver;
import Carta.Carta;
import Modelo.ResultadoApuesta;

public class VistaJuego implements IVista {
    
    
    private final InterfazConsola consola;
    
    
    public VistaJuego() {
        this.consola = InterfazConsola.obtenerInstancia();
    }
    
    // ================ MÉTODOS DE VISUALIZACIÓN DE ESTADO ================
    
    
    @Override
    public void mostrarEstadoJuegoMultijugador(Jugador jugador1, Jugador jugador2, Carta cartaActual) {
        consola.mostrarMensaje("\nESTADO DEL JUEGO:");
        consola.mostrarMensaje("┌" + "─".repeat(58) + "┐");
        
        // Mostrar estado de jugador 1
        String estadoJ1 = String.format("│ %-20s %s Vidas: %d      %s Balas: %d      │",
                jugador1.getNombre(),
                InterfazConsola.EMOJI_VIDAS,
                jugador1.getVidas(),
                InterfazConsola.EMOJI_BALAS,
                contarBalas(jugador1.getRevolver()));
        consola.mostrarMensaje(estadoJ1);
        
        // Mostrar estado de jugador 2
        String estadoJ2 = String.format("│ %-20s %s Vidas: %d      %s Balas: %d      │",
                jugador2.getNombre(),
                InterfazConsola.EMOJI_VIDAS,
                jugador2.getVidas(),
                InterfazConsola.EMOJI_BALAS,
                contarBalas(jugador2.getRevolver()));
        consola.mostrarMensaje(estadoJ2);
        
        consola.mostrarMensaje("└" + "─".repeat(58) + "┘");
        
        // Mostrar carta actual
        mostrarCartaActual(cartaActual);
    }
    
    
    @Override
    public void mostrarEstadoJuegoSolo(Jugador jugador, Carta cartaActual, int rachaActual) {
        consola.mostrarMensaje("\nESTADO DEL JUEGO:");
        consola.mostrarMensaje("┌" + "─".repeat(58) + "┐");
        
        String estado = String.format("│ %20s | Racha actual: %-20d│",
                jugador.getNombre(), rachaActual);
        consola.mostrarMensaje(estado);
        
        consola.mostrarMensaje("└" + "─".repeat(58) + "┘");
        
        // Mostrar carta actual
        mostrarCartaActual(cartaActual);
    }
    
    
    public void mostrarCartaActual(Carta carta) {
        if (carta != null) {
            consola.mostrarMensaje("\nCARTA ACTUAL EN MESA: " + carta.obtenerRepresentacion());
            consola.mostrarMensaje("");
        }
    }
    
    
    private int contarBalas(Revolver revolver) {
        return revolver.contarBalas();
    }
    
    // ================ MÉTODOS DE VISUALIZACIÓN DE TURNOS ================
    
    
    @Override
    public void mostrarEncabezadoTurno(String nombreJugador) {
        consola.mostrarMensaje("\n" + InterfazConsola.SEPARADOR_PRINCIPAL);
        consola.mostrarMensaje("TURNO DE: " + nombreJugador.toUpperCase());
        consola.mostrarMensaje(InterfazConsola.SEPARADOR_PRINCIPAL);
    }
    
    
    @Override
    public void mostrarEncabezadoTurnoSolo(String nombreJugador) {
        consola.mostrarMensaje("\n" + InterfazConsola.SEPARADOR_PRINCIPAL);
        consola.mostrarMensaje("TURNO DE: " + nombreJugador.toUpperCase() + " (SOLO)");
        consola.mostrarMensaje(InterfazConsola.SEPARADOR_PRINCIPAL);
    }
    
    // ================ MÉTODOS DE VISUALIZACIÓN DE APUESTAS ================
    
    
    @Override
    public void mostrarResultadoApuesta(ResultadoApuesta resultado) {
        // Mostrar información de la apuesta
        mostrarInformacionApuesta(
            resultado.getNombreJugador(),
            resultado.getTipoApuesta(),
            resultado.getCartaAnterior(),
            resultado.getNuevaCarta()
        );
        
        // Mostrar resultado (acierto o fallo)
        if (resultado.acerto()) {
            mostrarAcierto();
            
            // Si tiene efecto especial, mostrarlo
            if (resultado.tieneEfecto()) {
                mostrarEfectoEspecial(resultado.getTipoEfecto(), resultado.getDescripcionEfecto());
                mostrarResultadoEfecto(resultado.getResultadoEfecto());
            } else {
                // Sin efecto especial
                if (resultado.esModoSolo()) {
                    mostrarContinuacionSolo();
                } else {
                    mostrarContinuacionSinEfecto();
                }
            }
        } else {
            // Mostrar fallo y efecto del revólver
            mostrarFallo();
            
            if (resultado.huboImpacto()) {
                if (resultado.esModoSolo()) {
                    mostrarImpactoSolo(resultado.getNombreJugador());
                } else {
                    mostrarImpacto(resultado.getNombreJugador(), resultado.getVidasRestantes());
                }
            } else {
                mostrarSinImpacto(resultado.getNombreJugador());
            }
        }
    }
    
    
    public void mostrarInformacionApuesta(String nombreJugador, String apuesta, 
                                         Carta cartaActual, Carta nuevaCarta) {
        String mensaje = String.format("%s apuesta: %s | Carta actual: %s | Carta a revelar: %s",
                nombreJugador, apuesta, cartaActual, nuevaCarta);
        consola.mostrarMensaje(mensaje);
    }
    
    
    public void mostrarAcierto() {
        consola.mostrarExito("ACIERTO!");
    }
    
    
    public void mostrarFallo() {
        consola.mostrarError("ERROR! Se acciona el revolver...");
    }
    
    
    public void mostrarContinuacionSinEfecto() {
        consola.mostrarMensaje("Pasa el siguiente jugador.");
    }
    
    
    public void mostrarContinuacionSolo() {
        consola.mostrarMensaje("Excelente, continua jugando");
    }
    
    // ================ MÉTODOS DE VISUALIZACIÓN DE REVÓLVER ================
    
    
    public void mostrarImpacto(String nombreJugador, int vidasRestantes) {
        String mensaje = String.format("%s %s recibe un impacto y pierde 1 vida. Vidas restantes: %d",
                InterfazConsola.EMOJI_IMPACTO, nombreJugador, vidasRestantes);
        consola.mostrarMensaje(mensaje);
    }
    
    
    public void mostrarSinImpacto(String nombreJugador) {
        String mensaje = String.format("%s %s no recibió impacto. Se agrega una bala al revólver, apreta bien el chupete",
                InterfazConsola.EMOJI_SUERTE, nombreJugador);
        consola.mostrarMensaje(mensaje);
    }
    
    
    public void mostrarImpactoSolo(String nombreJugador) {
        String mensaje = String.format("%s recibe un impacto y pierde 1 vida.\nEL JUEGO TERMINO",
                nombreJugador);
        consola.mostrarMensaje(mensaje);
    }
    
    // ================ MÉTODOS DE VISUALIZACIÓN DE EFECTOS ================
    
    
    public void mostrarEfectoEspecial(String tipoEfecto, String descripcion) {
        consola.mostrarMensaje("\n" + InterfazConsola.EMOJI_ESTRELLA.repeat(60));
        consola.mostrarMensaje("¡CARTA ESPECIAL! Efecto: " + tipoEfecto);
        consola.mostrarMensaje("Descripción: " + descripcion);
        consola.mostrarMensaje(InterfazConsola.EMOJI_ESTRELLA.repeat(60));
    }
    
    
    public void mostrarResultadoEfecto(String resultado) {
        if (resultado != null && !resultado.isEmpty()) {
            consola.mostrarMensaje("\n" + resultado);
        }
    }
    
    // ================ MÉTODOS DE VISUALIZACIÓN DE FIN DE JUEGO ================
    
    
    @Override
    public void mostrarVictoria(String nombreGanador) {
        consola.mostrarMensaje("\n" + InterfazConsola.EMOJI_TROFEO.repeat(30));
        consola.mostrarMensaje("      ¡PARTIDA FINALIZADA!");
        consola.mostrarMensaje("      GANADOR: " + nombreGanador.toUpperCase());
        consola.mostrarMensaje(InterfazConsola.EMOJI_TROFEO.repeat(30) + "\n");
    }
    
    
    @Override
    public void mostrarFinSolo(int rachaMaxima) {
        consola.mostrarMensaje("\n" + InterfazConsola.EMOJI_MUERTE.repeat(15));
        consola.mostrarMensaje("      ¡FIN DEL JUEGO SOLO!");
        consola.mostrarMensaje("      Racha máxima: " + rachaMaxima + " aciertos");
        consola.mostrarMensaje(InterfazConsola.EMOJI_MUERTE.repeat(15) + "\n");
    }
    
    // ================ MÉTODOS DE UTILIDAD ================
    
    
    @Override
    public void mostrarError(String mensaje) {
        consola.mostrarError(mensaje);
    }
    
    
    @Override
    public void esperarContinuar() {
        consola.esperarEnter();
    }
}
