package Carta;

import EfectosEspeciales.EfectoEspecial;
import Exceptions.CartaNulaException;

public class Carta implements ICarta {
    
    // ================ CONSTANTES ================
    
    public static final int VALOR_MINIMO = 1;
    
    
    public static final int VALOR_MAXIMO = 13;
    
    // ================ ATRIBUTOS ================
    
    private final int valor;
    
    
    private final Palo palo;
    
    
    private EfectoEspecial efecto;

    // ================ CONSTRUCTORES ================
    
    
    public Carta(int valor, Palo palo) {
        validarValor(valor);
        validarPalo(palo);
        
        this.valor = valor;
        this.palo = palo;
        this.efecto = null; // Por defecto no tiene efecto
    }

    
    public Carta(int valor, Palo palo, EfectoEspecial efecto) {
        this(valor, palo); // Reutiliza el constructor principal
        this.efecto = efecto;
    }

    // ================ VALIDACIONES ================
    
    
    private void validarValor(int valor) {
        if (valor < VALOR_MINIMO || valor > VALOR_MAXIMO) {
            throw new CartaNulaException(
                "El valor de la carta debe estar entre " + VALOR_MINIMO + 
                " y " + VALOR_MAXIMO + ". Valor recibido: " + valor
            );
        }
    }

    
    private void validarPalo(Palo palo) {
        if (palo == null) {
            throw new CartaNulaException("El palo de la carta no puede ser null");
        }
    }

    // ================ MÉTODOS DE COMPARACIÓN ================
    
    
    public boolean esMayorQue(Carta otraCarta) {
        validarCartaComparacion(otraCarta);
        return this.valor > otraCarta.valor;
    }

    
    public boolean esMenorQue(Carta otraCarta) {
        validarCartaComparacion(otraCarta);
        return this.valor < otraCarta.valor;
    }

    
    public boolean esIgualQue(Carta otraCarta) {
        validarCartaComparacion(otraCarta);
        return this.valor == otraCarta.valor;
    }

    
    private void validarCartaComparacion(Carta carta) {
        if (carta == null) {
            throw new CartaNulaException("No se puede comparar con una carta null");
        }
    }

    // ================ GETTERS ================
    
    
    public int getValor() {
        return valor;
    }

    
    public Palo getPalo() {
        return palo;
    }

    
    public EfectoEspecial getEfecto() {
        return efecto;
    }

    // ================ SETTERS ================
    
    
    public void setEfecto(EfectoEspecial efecto) {
        this.efecto = efecto;
    }

    // ================ MÉTODOS DE UTILIDAD ================
    
    
    public boolean tieneEfecto() {
        return efecto != null;
    }

    
    public String getNombreValor() {
        return switch (valor) {
            case 1 -> "As";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> String.valueOf(valor);
        };
    }

    
    public String obtenerRepresentacion() {
        String representacion = getNombreValor() + " de " + palo;
        if (tieneEfecto()) {
            representacion += " [" + efecto.getTipoEfecto() + "]";
        }
        return representacion;
    }

    // ================ MÉTODOS DE OBJECT ================
    
    
    @Override
    public String toString() {
        return "CARTA: " + obtenerRepresentacion();
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Carta otraCarta = (Carta) obj;
        return valor == otraCarta.valor && palo == otraCarta.palo;
    }

    
    @Override
    public int hashCode() {
        int result = valor;
        result = 31 * result + palo.hashCode();
        return result;
    }
}
