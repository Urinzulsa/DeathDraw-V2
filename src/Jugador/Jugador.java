package Jugador;

import Exceptions.JugadorNullException;
import Exceptions.NombreInvalidoException;
import Exceptions.RevolverNullException;
import Exceptions.VidasInvalidasException;

public class Jugador implements IJugador {
    
    // ================ CONSTANTES ================
    
    private static final int VIDAS_DEFAULT = 3;
    
    
    private static final int VIDAS_MINIMAS = 0;
    
    // ================ ATRIBUTOS ================
    
    private final String nombre;
    
    
    private int vidas;
    
    
    private final int vidasMaximas;
    
    
    private Revolver revolver;

    // ================ CONSTRUCTORES ================
    
    

    public Jugador(String nombre, Revolver revolver, int vidasMaximas) {
        // Validar parámetros
        validarNombre(nombre);
        validarRevolver(revolver);
        validarVidasMaximas(vidasMaximas);
        
        this.nombre = nombre.trim();
        this.vidasMaximas = vidasMaximas;
        this.vidas = vidasMaximas; // Inicia con vidas al máximo
        this.revolver = revolver;
    }

    
    public Jugador(String nombre, Revolver revolver) {
        this(nombre, revolver, VIDAS_DEFAULT);
    }

    // ================ VALIDACIONES ================
    
    
    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NombreInvalidoException("El nombre del jugador no puede ser null o vacío");
        }
    }

    
    private void validarRevolver(Revolver revolver) {
        if (revolver == null) {
            throw new RevolverNullException("El revólver no puede ser null");
        }
    }

    
    private void validarVidasMaximas(int vidasMaximas) {
        if (vidasMaximas <= 0) {
            throw new VidasInvalidasException("Las vidas máximas deben ser mayores a 0");
        }
    }

    // ================ GETTERS ================
    
    
    public String getNombre() {
        return nombre;
    }

    
    public int getVidas() {
        return vidas;
    }

    
    public int getVidasMaximas() {
        return vidasMaximas;
    }

    
    public Revolver getRevolver() {
        return revolver;
    }

    // ================ SETTERS ================
    
    
    public void setRevolver(Revolver nuevoRevolver) {
        validarRevolver(nuevoRevolver);
        this.revolver = nuevoRevolver;
    }

    
    public void setVidas(int vidas) {
        if (vidas < VIDAS_MINIMAS) {
            throw new VidasInvalidasException("Las vidas no pueden ser negativas");
        }
        if (vidas > vidasMaximas) {
            throw new VidasInvalidasException(
                    "Las vidas no pueden exceder el máximo de " + vidasMaximas
            );
        }
        this.vidas = vidas;
    }

    // ================ MÉTODOS DE NEGOCIO ================
    
    
    public void perderVida() {
        if (vidas > VIDAS_MINIMAS) {
            vidas--;
        }
    }

    
    public void ganarVida() {
        if (vidas < vidasMaximas) {
            vidas++;
        }
    }

    
    public boolean estaVivo() {
        return vidas > VIDAS_MINIMAS;
    }

    
    public boolean tieneVidasMaximas() {
        return vidas == vidasMaximas;
    }

    // ================ MÉTODOS DE OBJECT ================
    
    
    @Override
    public String toString() {
        return "Jugador: " + nombre;
    }

    
    public String toStringDetallado() {
        return String.format("Jugador: %s | Vidas: %d/%d | Balas: %d",
            nombre, vidas, vidasMaximas, revolver.contarBalas());
    }
}
