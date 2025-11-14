package Vista;

/**
 * Clase responsable de manejar todos los menús de interacción con el usuario.
 * <p>
 * Responsabilidades:
 * <ul>
 *   <li>Mostrar pantalla de bienvenida</li>
 *   <li>Gestionar selección de modo de juego</li>
 *   <li>Solicitar nombres de jugadores</li>
 *   <li>Mostrar opciones de apuesta</li>
 *   <li>Validar entradas de usuario</li>
 * </ul>
 * 
 * Esta clase encapsula toda la lógica de presentación de menús,
 * separándola de la lógica de negocio.
 * </p>
 * 
 * @author DeathDraw-V2
 * @version 2.0
 */
public class MenuPrincipal {
    
    /** Interfaz de consola para input/output */
    private final InterfazConsola consola;
    
    /**
     * Constructor que inyecta la dependencia de InterfazConsola.
     */
    public MenuPrincipal() {
        this.consola = InterfazConsola.obtenerInstancia();
    }
    
    // ================ PANTALLAS DE BIENVENIDA ================
    
    /**
     * Muestra la pantalla de bienvenida del juego.
     */
    public void mostrarBienvenida() {
        consola.limpiarPantalla();
        consola.mostrarTitulo("DEATH DRAW");
    }
    
    // ================ MENÚ DE SELECCIÓN DE MODO ================
    
    /**
     * Muestra el menú de selección de modo y retorna la elección del usuario.
     * <p>
     * Modos disponibles:
     * <ol>
     *   <li>CLÁSICO: 3 vidas, 1 bala, 15% efectos</li>
     *   <li>SOBRECARGA: 5 vidas, 2 balas, 20% efectos</li>
     *   <li>MUERTE SÚBITA: 1 vida, 6 balas, 5% efectos</li>
     *   <li>SOLO: 1 vida, 1 bala, 0% efectos</li>
     * </ol>
     * </p>
     * 
     * @return El modo de juego seleccionado
     */
    public String seleccionarModo() {
        consola.mostrarMensaje("Selecciona el modo de juego:");
        consola.mostrarMensaje("");

        mostrarOpcionModo(1, "MODO CLÁSICO", 3, 1, 15);
        mostrarOpcionModo(2, "MODO SOBRECARGA", 5, 2, 20);
        mostrarOpcionModo(3, "MODO MUERTE SÚBITA", 1, 6, 5);
        mostrarOpcionModoSolo(4, "MODO SOLO", 1, 1);
        consola.mostrarMensaje("5.  VER TOP 5 (SOLO)");
        consola.mostrarMensaje("");

        int opcion = consola.solicitarNumero("Ingresa tu elección (1-5): ", 1, 5);

        String modoSeleccionado = switch (opcion) {
            case 1 -> "CLASICO";
            case 2 -> "SOBRECARGA";
            case 3 -> "MUERTE_SUBITA";
            case 4 -> "SOLO";
            case 5 -> "VER_TOP";
            default -> "CLASICO";
        };

        consola.mostrarMensaje("\nModo seleccionado: " + modoSeleccionado);
        return modoSeleccionado;
    }
    
    /**
     * Muestra una opción de modo de juego con sus características.
     * 
     * @param numero Número de la opción
     * @param nombre Nombre del modo
     * @param vidas Vidas iniciales
     * @param balas Balas iniciales
     * @param efectos Porcentaje de efectos especiales
     */
    private void mostrarOpcionModo(int numero, String nombre, int vidas, int balas, int efectos) {
        consola.mostrarMensaje(String.format("%d.  %s", numero, nombre));
        consola.mostrarMensaje(String.format("   - Vidas: %d por jugador", vidas));
        consola.mostrarMensaje(String.format("   - Balas iniciales: %d", balas));
        consola.mostrarMensaje(String.format("   - Cartas especiales: %d%%", efectos));
        consola.mostrarMensaje("");
    }
    
    /**
     * Muestra la opción de modo SOLO con sus características especiales.
     * 
     * @param numero Número de la opción
     * @param nombre Nombre del modo
     * @param vidas Vidas iniciales
     * @param balas Balas iniciales
     */
    private void mostrarOpcionModoSolo(int numero, String nombre, int vidas, int balas) {
        consola.mostrarMensaje(String.format("%d.  %s", numero, nombre));
        consola.mostrarMensaje(String.format("   - Vidas: %d", vidas));
        consola.mostrarMensaje(String.format("   - Balas iniciales: %d (SI PIERDE EL JUEGO TERMINA)", balas));
        consola.mostrarMensaje("");
    }
    
    // ================ MENÚ DE REGISTRO DE JUGADORES ================
    
    /**
     * Solicita los nombres de jugadores según el modo seleccionado.
     * <p>
     * Si es modo SOLO, solo solicita un nombre.
     * Si es multijugador, solicita dos nombres.
     * </p>
     * 
     * @param esModoSolo true si es modo SOLO, false si es multijugador
     * @return Array con los nombres (1 elemento para SOLO, 2 para multijugador)
     */
    public String[] solicitarNombresJugadores(boolean esModoSolo) {
        consola.mostrarSubtitulo("REGISTRO DE JUGADORES");
        
        String jugador1 = consola.solicitarTexto("Nombre del Jugador 1: ");
        
        if (esModoSolo) {
            return new String[]{jugador1};
        }
        
        String jugador2 = consola.solicitarTexto("Nombre del Jugador 2: ");
        return new String[]{jugador1, jugador2};
    }
    
    // ================ MENÚ DE APUESTAS ================
    
    /**
     * Solicita la apuesta de un jugador.
     * <p>
     * Opciones:
     * <ol>
     *   <li>MAYOR - La siguiente carta será mayor</li>
     *   <li>MENOR - La siguiente carta será menor</li>
     * </ol>
     * </p>
     * 
     * @param nombreJugador Nombre del jugador que apuesta
     * @return La apuesta seleccionada ("MAYOR" o "MENOR")
     */
    public String solicitarApuesta(String nombreJugador) {
        consola.mostrarMensaje(nombreJugador + ", ¿qué apuestas?");
        consola.mostrarMensaje("1. > MAYOR - La siguiente carta será MAYOR");
        consola.mostrarMensaje("2. < MENOR - La siguiente carta será MENOR");
        
        int opcion = consola.solicitarNumero("\nTu apuesta (1-2): ", 1, 2);
        
        return switch (opcion) {
            case 1 -> "MAYOR";
            case 2 -> "MENOR";
            default -> "MAYOR";
        };
    }
    
    // ================ MENSAJES DE INFORMACIÓN ================
    
    /**
     * Muestra un mensaje de inicio de partida.
     * 
     * @param mensaje Mensaje descriptivo del inicio
     */
    public void mostrarInicioPartida(String mensaje) {
        consola.mostrarMensaje("\n" + InterfazConsola.SEPARADOR_PRINCIPAL);
        
        // Extraer solo la parte del modo y jugadores, sin la carta
        String[] lineas = mensaje.split("\n");
        for (int i = 0; i < lineas.length - 1; i++) {
            consola.mostrarMensaje(lineas[i]);
        }
        
        consola.mostrarMensaje(InterfazConsola.SEPARADOR_PRINCIPAL + "\n");
    }
    
    /**
     * Muestra un mensaje de éxito al agregar jugador.
     * 
     * @param mensaje Mensaje de confirmación
     */
    public void mostrarJugadorAgregado(String mensaje) {
        consola.mostrarExito(mensaje);
    }
    
    /**
     * Muestra un mensaje de error genérico.
     * 
     * @param mensaje Mensaje de error
     */
    public void mostrarError(String mensaje) {
        consola.mostrarError(mensaje);
    }
}
