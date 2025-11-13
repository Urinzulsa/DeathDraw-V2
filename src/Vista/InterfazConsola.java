package Vista;

import java.util.Scanner;

/**
 * Clase base para manejar toda la interacción por consola.
 * <p>
 * Responsabilidades:
 * <ul>
 *   <li>Mostrar mensajes formateados con emojis y separadores</li>
 *   <li>Solicitar input del usuario con validaciones</li>
 *   <li>Formatear salidas con colores y estilos consistentes</li>
 *   <li>Centralizar el Scanner para evitar múltiples instancias</li>
 * </ul>
 * 
 * Esta clase sigue el patrón Singleton para garantizar una única
 * instancia de Scanner en toda la aplicación.
 * </p>
 * 
 * @author DeathDraw-V2
 * @version 2.0
 */
public class InterfazConsola {
    
    // ================ CONSTANTES DE FORMATO ================
    /** Ancho estándar para separadores */
    public static final int ANCHO_SEPARADOR = 60;
    
    /** Separador principal (=) */
    public static final String SEPARADOR_PRINCIPAL = "=".repeat(ANCHO_SEPARADOR);
    
    /** Separador secundario (-) */
    public static final String SEPARADOR_SECUNDARIO = "-".repeat(ANCHO_SEPARADOR);
    
    /** Separador de sección (─) */
    public static final String SEPARADOR_SECCION = "─".repeat(ANCHO_SEPARADOR - 2);
    
    // ================ EMOJIS PARA FEEDBACK VISUAL ================
    /** Emoji de acierto */
    public static final String EMOJI_ACIERTO = "✅";
    
    /** Emoji de error */
    public static final String EMOJI_ERROR = "❌";
    
    /** Emoji de impacto */
    public static final String EMOJI_IMPACTO = "💥";
    
    /** Emoji de suerte */
    public static final String EMOJI_SUERTE = "🍀";
    
    /** Emoji de vidas */
    public static final String EMOJI_VIDAS = "❤️";
    
    /** Emoji de balas */
    public static final String EMOJI_BALAS = "🔫";
    
    /** Emoji de trofeo */
    public static final String EMOJI_TROFEO = "🏆";
    
    /** Emoji de muerte */
    public static final String EMOJI_MUERTE = "💀";
    
    /** Emoji de estrella */
    public static final String EMOJI_ESTRELLA = "🌟";
    
    // ================ SINGLETON ================
    /** Instancia única de InterfazConsola */
    private static InterfazConsola instancia;
    
    /** Scanner compartido para toda la aplicación */
    private final Scanner scanner;
    
    /**
     * Constructor privado para patrón Singleton.
     */
    private InterfazConsola() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Obtiene la instancia única de InterfazConsola.
     * 
     * @return La instancia singleton
     */
    public static InterfazConsola obtenerInstancia() {
        if (instancia == null) {
            instancia = new InterfazConsola();
        }
        return instancia;
    }
    
    /**
     * Obtiene el Scanner compartido.
     * 
     * @return Scanner para lectura de entrada
     */
    public Scanner getScanner() {
        return scanner;
    }
    
    // ================ MÉTODOS DE ENTRADA ================
    
    /**
     * Solicita un número entero al usuario con validación.
     * 
     * @param mensaje Mensaje a mostrar
     * @param min Valor mínimo permitido
     * @param max Valor máximo permitido
     * @return Número válido ingresado por el usuario
     */
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
    
    /**
     * Solicita un texto al usuario con validación de no vacío.
     * 
     * @param mensaje Mensaje a mostrar
     * @return Texto no vacío ingresado por el usuario
     */
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
    
    /**
     * Espera que el usuario presione ENTER para continuar.
     */
    public void esperarEnter() {
        System.out.println("\nPresiona ENTER para continuar...");
        scanner.nextLine();
    }
    
    // ================ MÉTODOS DE SALIDA ================
    
    /**
     * Muestra un mensaje simple.
     * 
     * @param mensaje Mensaje a mostrar
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    
    /**
     * Muestra un mensaje de éxito con emoji.
     * 
     * @param mensaje Mensaje de éxito
     */
    public void mostrarExito(String mensaje) {
        System.out.println(EMOJI_ACIERTO + " " + mensaje);
    }
    
    /**
     * Muestra un mensaje de error con emoji.
     * 
     * @param mensaje Mensaje de error
     */
    public void mostrarError(String mensaje) {
        System.out.println(EMOJI_ERROR + " " + mensaje);
    }
    
    /**
     * Muestra un mensaje de advertencia.
     * 
     * @param mensaje Mensaje de advertencia
     */
    public void mostrarAdvertencia(String mensaje) {
        System.out.println("⚠️  " + mensaje);
    }
    
    /**
     * Muestra un título centrado con separadores.
     * 
     * @param titulo Título a mostrar
     */
    public void mostrarTitulo(String titulo) {
        System.out.println("\n" + SEPARADOR_PRINCIPAL);
        System.out.println(centrarTexto(titulo, ANCHO_SEPARADOR));
        System.out.println(SEPARADOR_PRINCIPAL);
    }
    
    /**
     * Muestra un subtítulo con separador secundario.
     * 
     * @param subtitulo Subtítulo a mostrar
     */
    public void mostrarSubtitulo(String subtitulo) {
        System.out.println("\n" + SEPARADOR_SECUNDARIO);
        System.out.println(subtitulo);
        System.out.println(SEPARADOR_SECUNDARIO);
    }
    
    /**
     * Muestra una caja con contenido.
     * 
     * @param contenido Contenido a mostrar en la caja
     */
    public void mostrarCaja(String contenido) {
        System.out.println("┌" + SEPARADOR_SECCION + "┐");
        
        String[] lineas = contenido.split("\n");
        for (String linea : lineas) {
            System.out.println("│ " + ajustarLinea(linea, ANCHO_SEPARADOR - 4) + " │");
        }
        
        System.out.println("└" + SEPARADOR_SECCION + "┘");
    }
    
    /**
     * Muestra un separador visual.
     */
    public void mostrarSeparador() {
        System.out.println(SEPARADOR_PRINCIPAL);
    }
    
    /**
     * Limpia la pantalla (solo visual, agrega líneas en blanco).
     */
    public void limpiarPantalla() {
        for (int i = 0; i < 2; i++) {
            System.out.println();
        }
    }
    
    // ================ MÉTODOS DE FORMATO ================
    
    /**
     * Centra un texto en un ancho dado.
     * 
     * @param texto Texto a centrar
     * @param ancho Ancho total disponible
     * @return Texto centrado con espacios
     */
    private String centrarTexto(String texto, int ancho) {
        int espacios = (ancho - texto.length()) / 2;
        return " ".repeat(Math.max(0, espacios)) + texto;
    }
    
    /**
     * Ajusta una línea al ancho especificado, rellenando con espacios.
     * 
     * @param linea Línea a ajustar
     * @param ancho Ancho deseado
     * @return Línea ajustada
     */
    private String ajustarLinea(String linea, int ancho) {
        if (linea.length() > ancho) {
            return linea.substring(0, ancho);
        }
        return String.format("%-" + ancho + "s", linea);
    }
    
    /**
     * Formatea un mensaje con emoji según el tipo.
     * 
     * @param tipo Tipo de mensaje (acierto, error, etc.)
     * @param mensaje Mensaje a formatear
     * @return Mensaje formateado con emoji
     */
    public String formatearMensaje(String tipo, String mensaje) {
        String emoji = switch (tipo.toLowerCase()) {
            case "acierto" -> EMOJI_ACIERTO;
            case "error" -> EMOJI_ERROR;
            case "impacto" -> EMOJI_IMPACTO;
            case "suerte" -> EMOJI_SUERTE;
            case "estrella" -> EMOJI_ESTRELLA;
            default -> "";
        };
        
        return emoji.isEmpty() ? mensaje : emoji + " " + mensaje;
    }
    
    /**
     * Cierra el scanner.
     * <p>
     * Solo debe llamarse al finalizar la aplicación completamente.
     * </p>
     */
    public void cerrar() {
        scanner.close();
    }
}
