package Jugador;

public class Revolver {
    private boolean [] recamara;

    public Revolver() {
        this.recamara = new boolean[6];
    }

    public boolean girarYDisparar() {
        int giro = 0;
        boolean aux = false;
        giro = (int) (Math.random() * 6);
        if (recamara[giro])  {
            aux = true;
            recamara[giro] = false;
        }
        return aux;
    }

    public boolean cargarBala(){
        int posicion = 0;
        while (posicion < recamara.length) {
            if (!recamara[posicion]) {
                recamara[posicion] = true;
                return true;
            }
            posicion ++;
        }
        return false;
    }

    public boolean quitarBala(){
        int posicion = 0;
        while (posicion < recamara.length) {
            if (recamara[posicion]) {
                recamara[posicion] = false;
                return true;
            }
            posicion ++;
        }
        return false;
    }

    // Limpia el revólver y establece un número específico de balas
    public void setBalas(int numeroDeBalas) {
        // 1. Limpiar la recámara
        java.util.Arrays.fill(recamara, false);

        // 2. Cargar el número especificado de balas, sin exceder la capacidad
        int balasACargar = Math.min(numeroDeBalas, recamara.length);
        for (int i = 0; i < balasACargar; i++) {
            cargarBala();
        }
    }

    public void mostrarRecamara() { /// para debuggear
        for (int i = 0; i < recamara.length; i++) {
            System.out.print(recamara[i] ? "💣 " : "⬜ ");
        }
        System.out.println();
    }
}
