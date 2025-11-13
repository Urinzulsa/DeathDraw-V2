package Jugador;

import java.util.Arrays;
import java.util.Random;

/**
 * Representa un revólver de 6 recámaras para la mecánica de ruleta rusa.
 * <p>
 * Características del revólver:
 * <ul>
 *   <li>Capacidad fija de 6 recámaras</li>
 *   <li>Cada recámara puede contener o no una bala</li>
 *   <li>Las balas NO se quitan al disparar (mecánica única del juego)</li>
 *   <li>Permite cargar y descargar balas individualmente</li>
 * </ul>
 * </p>
 * 
 * @author DeathDraw-V2
 * @version 2.0
 */
public class Revolver {
    
    // ================ CONSTANTES ================
    /** Número de recámaras del revólver */
    private static final int CAPACIDAD_RECAMARA = 6;
    
    /** Índice de la primera recámara */
    private static final int INDICE_PRIMERA_RECAMARA = 0;
    
    /** Generador de números aleatorios para el giro del tambor */
    private static final Random RANDOM = new Random();
    
    // ================ ATRIBUTOS ================
    /** 
     * Array que representa las 6 recámaras del revólver.
     * true = hay bala, false = recámara vacía
     */
    private final boolean[] recamara;

    // ================ CONSTRUCTOR ================
    
    /**
     * Constructor que inicializa un revólver vacío (sin balas).
     */
    public Revolver() {
        this.recamara = new boolean[CAPACIDAD_RECAMARA];
        // Por defecto todas las posiciones son false (sin balas)
    }

    // ================ MÉTODOS PRINCIPALES ================
    
    /**
     * Gira el tambor y dispara en una posición aleatoria.
     * <p>
     * <strong>IMPORTANTE:</strong> Las balas NO se quitan al disparar.
     * Esta es una mecánica única del juego que mantiene la tensión creciente.
     * </p>
     * 
     * @return true si había bala en la posición (impacto), false si estaba vacía
     */
    public boolean girarYDisparar() {
        int posicionAleatoria = RANDOM.nextInt(CAPACIDAD_RECAMARA);
        return recamara[posicionAleatoria];
        // NOTA: NO se quita la bala intencionalmente
        // Esto crea un ciclo de tensión creciente en el juego
    }

    /**
     * Carga una bala en la primera recámara vacía disponible.
     * <p>
     * Busca secuencialmente desde la posición 0 hasta encontrar
     * una recámara vacía y coloca ahí la bala.
     * </p>
     * 
     * @return true si se pudo cargar la bala, false si el revólver está lleno
     */
    public boolean cargarBala() {
        for (int i = 0; i < recamara.length; i++) {
            if (!recamara[i]) {
                recamara[i] = true;
                return true;
            }
        }
        return false; // Revólver lleno
    }

    /**
     * Quita una bala de la primera recámara ocupada.
     * <p>
     * Busca secuencialmente desde la posición 0 hasta encontrar
     * una bala y la remueve.
     * </p>
     * 
     * @return true si se pudo quitar una bala, false si estaba vacío
     */
    public boolean quitarBala() {
        for (int i = 0; i < recamara.length; i++) {
            if (recamara[i]) {
                recamara[i] = false;
                return true;
            }
        }
        return false; // Revólver vacío
    }

    /**
     * Resetea el revólver y establece un número específico de balas.
     * <p>
     * Proceso:
     * <ol>
     *   <li>Limpia todas las recámaras (vacía el revólver)</li>
     *   <li>Carga el número especificado de balas</li>
     * </ol>
     * </p>
     * 
     * @param numeroDeBalas Número de balas a cargar (0-6)
     * @throws IllegalArgumentException si numeroDeBalas es negativo o mayor a capacidad
     */
    public void setBalas(int numeroDeBalas) {
        // Validar parámetro
        if (numeroDeBalas < 0) {
            throw new IllegalArgumentException("El número de balas no puede ser negativo");
        }
        if (numeroDeBalas > CAPACIDAD_RECAMARA) {
            throw new IllegalArgumentException(
                "El número de balas no puede exceder la capacidad del revólver (" + 
                CAPACIDAD_RECAMARA + ")"
            );
        }
        
        // Limpiar todas las recámaras
        vaciarRevolver();

        // Cargar el número especificado de balas
        for (int i = 0; i < numeroDeBalas; i++) {
            cargarBala();
        }
    }

    /**
     * Vacía completamente el revólver (quita todas las balas).
     */
    public void vaciarRevolver() {
        Arrays.fill(recamara, false);
    }

    /**
     * Llena completamente el revólver (carga todas las recámaras).
     */
    public void llenarRevolver() {
        Arrays.fill(recamara, true);
    }

    // ================ MÉTODOS DE CONSULTA ================
    
    /**
     * Cuenta el número de balas actualmente en el revólver.
     * 
     * @return Número de balas (0-6)
     */
    public int contarBalas() {
        int count = 0;
        for (boolean bala : recamara) {
            if (bala) {
                count++;
            }
        }
        return count;
    }

    /**
     * Obtiene la capacidad máxima del revólver.
     * 
     * @return Capacidad fija de 6 recámaras
     */
    public int getCapacidad() {
        return CAPACIDAD_RECAMARA;
    }

    /**
     * Verifica si el revólver está vacío (sin balas).
     * 
     * @return true si no tiene balas, false en caso contrario
     */
    public boolean estaVacio() {
        return contarBalas() == 0;
    }

    /**
     * Verifica si el revólver está lleno (todas las recámaras con bala).
     * 
     * @return true si está al máximo, false en caso contrario
     */
    public boolean estaLleno() {
        return contarBalas() == CAPACIDAD_RECAMARA;
    }

    /**
     * Calcula el porcentaje de probabilidad de impacto.
     * 
     * @return Porcentaje (0.0 a 100.0)
     */
    public double getProbabilidadImpacto() {
        return (contarBalas() * 100.0) / CAPACIDAD_RECAMARA;
    }

    // ================ MÉTODOS DE UTILIDAD ================
    
    /**
     * Muestra visualmente el estado de las recámaras del revólver.
     * <p>
     * Formato: 💣 para bala, ⬜ para vacío
     * Útil para debugging.
     * </p>
     */
    public void mostrarRecamara() {
        for (boolean bala : recamara) {
            System.out.print(bala ? "💣 " : "⬜ ");
        }
        System.out.println();
    }

    /**
     * Obtiene una representación en texto del estado del revólver.
     * 
     * @return String con el número de balas y capacidad
     */
    @Override
    public String toString() {
        return String.format("Revólver [%d/%d balas]", contarBalas(), CAPACIDAD_RECAMARA);
    }

    /**
     * Obtiene una representación detallada del revólver.
     * 
     * @return String con balas, probabilidad y estado de cada recámara
     */
    public String toStringDetallado() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Revólver: %d/%d balas (%.1f%% probabilidad)\n",
            contarBalas(), CAPACIDAD_RECAMARA, getProbabilidadImpacto()));
        sb.append("Recámaras: ");
        for (int i = 0; i < recamara.length; i++) {
            sb.append(recamara[i] ? "💣" : "⬜");
            if (i < recamara.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
