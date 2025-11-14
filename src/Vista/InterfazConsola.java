package Vista;

import java.util.Scanner;

public class InterfazConsola {
    
    // ================ CONSTANTES DE FORMATO ================
    
    public static final int ANCHO_SEPARADOR = 60;
    
    
    public static final String SEPARADOR_PRINCIPAL = "=".repeat(ANCHO_SEPARADOR);
    
    
    public static final String SEPARADOR_SECUNDARIO = "-".repeat(ANCHO_SEPARADOR);
    
    
    public static final String SEPARADOR_SECCION = "─".repeat(ANCHO_SEPARADOR - 2);
    
    // ================ EMOJIS PARA FEEDBACK VISUAL ================
    
    public static final String EMOJI_ACIERTO = "✅";
    
    
    public static final String EMOJI_ERROR = "❌";
    
    
    public static final String EMOJI_IMPACTO = "💥";
    
    
    public static final String EMOJI_SUERTE = "🍀";
    
    
    public static final String EMOJI_VIDAS = "❤️";
    
    
    public static final String EMOJI_BALAS = "🔫";
    
    
    public static final String EMOJI_TROFEO = "🏆";
    
    
    public static final String EMOJI_MUERTE = "💀";
    
    
    public static final String EMOJI_ESTRELLA = "🌟";
    
    // ================ SINGLETON ================
    
    private static InterfazConsola instancia;
    
    
    private final Scanner scanner;
    
    
    private InterfazConsola() {
        this.scanner = new Scanner(System.in);
    }
    
    
    public static InterfazConsola obtenerInstancia() {
        if (instancia == null) {
            instancia = new InterfazConsola();
        }
        return instancia;
    }
    
    
    public Scanner getScanner() {
        return scanner;
    }
    
    // ================ MÉTODOS DE ENTRADA ================
    
    
    public int solicitarNumero(String mensaje, int min, int max) {
        int numero = 0;
        boolean valido = false;
        
        while (!valido) {
            System.out.print(mensaje);
            try {
                String input = scanner.nextLine().trim();
                numero = Integer.parseInt(input);
                
                if (numero >= min && numero <= max) {
                    valido = true;
                } else {
                    mostrarError("Opción inválida. Elige entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                mostrarError("Por favor ingresa un número válido.");
            }
        }
        
        return numero;
    }
    
    
    public String solicitarTexto(String mensaje) {
        String texto = "";
        
        while (texto.trim().isEmpty()) {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            
            if (texto.isEmpty()) {
                mostrarError("El texto no puede estar vacío. Intenta de nuevo.");
            }
        }
        
        return texto;
    }
    
    
    public void esperarEnter() {
        System.out.println("\nPresiona ENTER para continuar...");
        scanner.nextLine();
    }
    
    // ================ MÉTODOS DE SALIDA ================
    
    
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    
    
    public void mostrarExito(String mensaje) {
        System.out.println(EMOJI_ACIERTO + " " + mensaje);
    }
    
    
    public void mostrarError(String mensaje) {
        System.out.println(EMOJI_ERROR + " " + mensaje);
    }
    
    public void mostrarTitulo(String titulo) {
        System.out.println("\n" + SEPARADOR_PRINCIPAL);
        System.out.println(centrarTexto(titulo, ANCHO_SEPARADOR));
        System.out.println(SEPARADOR_PRINCIPAL);
    }
    
    
    public void mostrarSubtitulo(String subtitulo) {
        System.out.println("\n" + SEPARADOR_SECUNDARIO);
        System.out.println(subtitulo);
        System.out.println(SEPARADOR_SECUNDARIO);
    }
    
    
    public void mostrarCaja(String contenido) {
        System.out.println("┌" + SEPARADOR_SECCION + "┐");
        
        String[] lineas = contenido.split("\n");
        for (String linea : lineas) {
            System.out.println("│ " + ajustarLinea(linea, ANCHO_SEPARADOR - 4) + " │");
        }
        
        System.out.println("└" + SEPARADOR_SECCION + "┘");
    }
    
    public void limpiarPantalla() {
        for (int i = 0; i < 2; i++) {
            System.out.println();
        }
    }
    
    // ================ MÉTODOS DE FORMATO ================
    
    
    private String centrarTexto(String texto, int ancho) {
        int espacios = (ancho - texto.length()) / 2;
        return " ".repeat(Math.max(0, espacios)) + texto;
    }

    private String ajustarLinea(String linea, int ancho) {
        if (linea.length() > ancho) {
            return linea.substring(0, ancho);
        }
        return String.format("%-" + ancho + "s", linea);
    }
    
    public void cerrar() {
        scanner.close();
    }
}
