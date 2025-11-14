package Jugador;

import java.util.Arrays;
import java.util.Random;

public class Revolver implements IRevolver {
    
    // ================ CONSTANTES ================
    
    private static final int CAPACIDAD_RECAMARA = 6;
    
    
    private static final int INDICE_PRIMERA_RECAMARA = 0;
    
    
    private static final Random RANDOM = new Random();
    
    // ================ ATRIBUTOS ================
    
    private final boolean[] recamara;

    // ================ CONSTRUCTOR ================
    
    
    public Revolver() {
        this.recamara = new boolean[CAPACIDAD_RECAMARA];
        // Por defecto todas las posiciones son false (sin balas)
    }

    // ================ MÉTODOS PRINCIPALES ================
    
    
    public boolean girarYDisparar() {
        int posicionAleatoria = RANDOM.nextInt(CAPACIDAD_RECAMARA);
        return recamara[posicionAleatoria];
        // NOTA: NO se quita la bala intencionalmente
        // Esto crea un ciclo de tensión creciente en el juego
    }

    
    public boolean cargarBala() {
        for (int i = 0; i < recamara.length; i++) {
            if (!recamara[i]) {
                recamara[i] = true;
                return true;
            }
        }
        return false; // Revólver lleno
    }

    
    public boolean quitarBala() {
        for (int i = 0; i < recamara.length; i++) {
            if (recamara[i]) {
                recamara[i] = false;
                return true;
            }
        }
        return false; // Revólver vacío
    }

    
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

    
    public void vaciarRevolver() {
        Arrays.fill(recamara, false);
    }

    
    public void llenarRevolver() {
        Arrays.fill(recamara, true);
    }

    // ================ MÉTODOS DE CONSULTA ================
    
    
    public int contarBalas() {
        int count = 0;
        for (boolean bala : recamara) {
            if (bala) {
                count++;
            }
        }
        return count;
    }

    
    public int getCapacidad() {
        return CAPACIDAD_RECAMARA;
    }

    
    public boolean estaVacio() {
        return contarBalas() == 0;
    }

    
    public boolean estaLleno() {
        return contarBalas() == CAPACIDAD_RECAMARA;
    }

    
    public double getProbabilidadImpacto() {
        return (contarBalas() * 100.0) / CAPACIDAD_RECAMARA;
    }

    // ================ MÉTODOS DE UTILIDAD ================
    
    
    public void mostrarRecamara() {
        for (boolean bala : recamara) {
            System.out.print(bala ? "💣 " : "⬜ ");
        }
        System.out.println();
    }

    
    @Override
    public String toString() {
        return String.format("Revólver [%d/%d balas]", contarBalas(), CAPACIDAD_RECAMARA);
    }

    
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
