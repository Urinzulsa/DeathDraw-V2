package Carta;

import EfectosEspeciales.EfectoEspecial;

/**
 * Representa una carta del mazo de póker estándar.
 * <p>
 * Cada carta tiene:
 * <ul>
 *   <li>Valor numérico (1-13): As=1, 2-10=literal, J=11, Q=12, K=13</li>
 *   <li>Palo: CORAZONES, DIAMANTES, TREBOLES, PICAS</li>
 *   <li>Efecto especial opcional (puede ser null)</li>
 * </ul>
 * </p>
 * 
 * @author DeathDraw-V2
 * @version 2.0
 */
public class Carta {
    
    // ================ CONSTANTES ================
    /** Valor mínimo de una carta (As) */
    public static final int VALOR_MINIMO = 1;
    
    /** Valor máximo de una carta (Rey) */
    public static final int VALOR_MAXIMO = 13;
    
    // ================ ATRIBUTOS ================
    /** Valor numérico de la carta (1-13) */
    private final int valor;
    
    /** Palo de la carta */
    private final Palo palo;
    
    /** Efecto especial de la carta (puede ser null) */
    private EfectoEspecial efecto;

    // ================ CONSTRUCTORES ================
    
    /**
     * Constructor para cartas normales sin efecto.
     * 
     * @param valor Valor de la carta (1-13)
     * @param palo Palo de la carta
     * @throws IllegalArgumentException si el valor está fuera de rango o palo es null
     */
    public Carta(int valor, Palo palo) {
        validarValor(valor);
        validarPalo(palo);
        
        this.valor = valor;
        this.palo = palo;
        this.efecto = null; // Por defecto no tiene efecto
    }

    /**
     * Constructor para crear una carta con un efecto específico.
     * 
     * @param valor Valor de la carta (1-13)
     * @param palo Palo de la carta
     * @param efecto Efecto especial de la carta
     * @throws IllegalArgumentException si el valor está fuera de rango o palo es null
     */
    public Carta(int valor, Palo palo, EfectoEspecial efecto) {
        this(valor, palo); // Reutiliza el constructor principal
        this.efecto = efecto;
    }

    // ================ VALIDACIONES ================
    
    /**
     * Valida que el valor esté en el rango permitido.
     * 
     * @param valor Valor a validar
     * @throws IllegalArgumentException si el valor está fuera de rango
     */
    private void validarValor(int valor) {
        if (valor < VALOR_MINIMO || valor > VALOR_MAXIMO) {
            throw new IllegalArgumentException(
                "El valor de la carta debe estar entre " + VALOR_MINIMO + 
                " y " + VALOR_MAXIMO + ". Valor recibido: " + valor
            );
        }
    }

    /**
     * Valida que el palo no sea null.
     * 
     * @param palo Palo a validar
     * @throws IllegalArgumentException si el palo es null
     */
    private void validarPalo(Palo palo) {
        if (palo == null) {
            throw new IllegalArgumentException("El palo de la carta no puede ser null");
        }
    }

    // ================ MÉTODOS DE COMPARACIÓN ================
    
    /**
     * Compara si esta carta es mayor que otra.
     * <p>
     * <strong>NOTA:</strong> La comparación es solo por valor numérico,
     * ignorando el palo.
     * </p>
     * 
     * @param otraCarta Carta a comparar
     * @return true si el valor de esta carta es mayor
     * @throws IllegalArgumentException si otraCarta es null
     */
    public boolean esMayorQue(Carta otraCarta) {
        validarCartaComparacion(otraCarta);
        return this.valor > otraCarta.valor;
    }

    /**
     * Compara si esta carta es menor que otra.
     * <p>
     * <strong>NOTA:</strong> La comparación es solo por valor numérico,
     * ignorando el palo.
     * </p>
     * 
     * @param otraCarta Carta a comparar
     * @return true si el valor de esta carta es menor
     * @throws IllegalArgumentException si otraCarta es null
     */
    public boolean esMenorQue(Carta otraCarta) {
        validarCartaComparacion(otraCarta);
        return this.valor < otraCarta.valor;
    }

    /**
     * Compara si esta carta es igual a otra en valor.
     * <p>
     * <strong>NOTA:</strong> La comparación es solo por valor numérico,
     * ignorando el palo.
     * </p>
     * 
     * @param otraCarta Carta a comparar
     * @return true si ambas tienen el mismo valor
     * @throws IllegalArgumentException si otraCarta es null
     */
    public boolean esIgualQue(Carta otraCarta) {
        validarCartaComparacion(otraCarta);
        return this.valor == otraCarta.valor;
    }

    /**
     * Valida que la carta de comparación no sea null.
     * 
     * @param carta Carta a validar
     * @throws IllegalArgumentException si la carta es null
     */
    private void validarCartaComparacion(Carta carta) {
        if (carta == null) {
            throw new IllegalArgumentException("No se puede comparar con una carta null");
        }
    }

    // ================ GETTERS ================
    
    /**
     * Obtiene el valor numérico de la carta.
     * 
     * @return Valor entre 1 y 13
     */
    public int getValor() {
        return valor;
    }

    /**
     * Obtiene el palo de la carta.
     * 
     * @return Palo de la carta
     */
    public Palo getPalo() {
        return palo;
    }

    /**
     * Obtiene el efecto especial de la carta.
     * 
     * @return Efecto especial o null si no tiene
     */
    public EfectoEspecial getEfecto() {
        return efecto;
    }

    // ================ SETTERS ================
    
    /**
     * Establece un efecto especial para la carta.
     * <p>
     * Usado por el sistema de creación de mazos para asignar
     * efectos aleatorios a cartas normales.
     * </p>
     * 
     * @param efecto Efecto a asignar (puede ser null)
     */
    public void setEfecto(EfectoEspecial efecto) {
        this.efecto = efecto;
    }

    // ================ MÉTODOS DE UTILIDAD ================
    
    /**
     * Verifica si la carta tiene un efecto especial.
     * 
     * @return true si tiene efecto, false en caso contrario
     */
    public boolean tieneEfecto() {
        return efecto != null;
    }

    /**
     * Obtiene el nombre de la carta según su valor.
     * <p>
     * Conversión:
     * <ul>
     *   <li>1 → "As"</li>
     *   <li>11 → "J"</li>
     *   <li>12 → "Q"</li>
     *   <li>13 → "K"</li>
     *   <li>Otros → valor literal</li>
     * </ul>
     * </p>
     * 
     * @return Nombre de la carta
     */
    public String getNombreValor() {
        return switch (valor) {
            case 1 -> "As";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> String.valueOf(valor);
        };
    }

    /**
     * Obtiene una representación legible de la carta.
     * <p>
     * Formato: "{valor} de {palo}" o "{valor} de {palo}[{efecto}]" si tiene efecto
     * </p>
     * 
     * @return Representación de la carta
     */
    public String obtenerRepresentacion() {
        String representacion = getNombreValor() + " de " + palo;
        if (tieneEfecto()) {
            representacion += " [" + efecto.getTipoEfecto() + "]";
        }
        return representacion;
    }

    // ================ MÉTODOS DE OBJECT ================
    
    /**
     * Representación en String de la carta.
     * 
     * @return Formato "CARTA: {representación}"
     */
    @Override
    public String toString() {
        return "CARTA: " + obtenerRepresentacion();
    }

    /**
     * Compara si dos cartas son iguales (mismo valor y palo).
     * 
     * @param obj Objeto a comparar
     * @return true si son iguales
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Carta otraCarta = (Carta) obj;
        return valor == otraCarta.valor && palo == otraCarta.palo;
    }

    /**
     * Calcula el hash code de la carta.
     * 
     * @return Hash code basado en valor y palo
     */
    @Override
    public int hashCode() {
        int result = valor;
        result = 31 * result + palo.hashCode();
        return result;
    }
}
