package Vista;

public class MenuPrincipal implements IMenu {
    
    
    private final InterfazConsola consola;
    
    
    public MenuPrincipal() {
        this.consola = InterfazConsola.obtenerInstancia();
    }
    
    // ================ PANTALLAS DE BIENVENIDA ================
    
    
    @Override
    public void mostrarBienvenida() {
        consola.limpiarPantalla();
        consola.mostrarTitulo("DEATH DRAW");
    }
    
    // ================ MENÚ DE SELECCIÓN DE MODO ================
    
    
    @Override
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
    
    
    private void mostrarOpcionModo(int numero, String nombre, int vidas, int balas, int efectos) {
        consola.mostrarMensaje(String.format("%d.  %s", numero, nombre));
        consola.mostrarMensaje(String.format("   - Vidas: %d por jugador", vidas));
        consola.mostrarMensaje(String.format("   - Balas iniciales: %d", balas));
        consola.mostrarMensaje(String.format("   - Cartas especiales: %d%%", efectos));
        consola.mostrarMensaje("");
    }
    
    
    private void mostrarOpcionModoSolo(int numero, String nombre, int vidas, int balas) {
        consola.mostrarMensaje(String.format("%d.  %s", numero, nombre));
        consola.mostrarMensaje(String.format("   - Vidas: %d", vidas));
        consola.mostrarMensaje(String.format("   - Balas iniciales: %d (SI PIERDE EL JUEGO TERMINA)", balas));
        consola.mostrarMensaje("");
    }
    
    // ================ MENÚ DE REGISTRO DE JUGADORES ================
    
    
    @Override
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
    
    
    @Override
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
    
    
    @Override
    public void mostrarInicioPartida(String mensaje) {
        consola.mostrarMensaje("\n" + InterfazConsola.SEPARADOR_PRINCIPAL);
        
        // Extraer solo la parte del modo y jugadores, sin la carta
        String[] lineas = mensaje.split("\n");
        for (int i = 0; i < lineas.length - 1; i++) {
            consola.mostrarMensaje(lineas[i]);
        }
        
        consola.mostrarMensaje(InterfazConsola.SEPARADOR_PRINCIPAL + "\n");
    }
    
    
    @Override
    public void mostrarJugadorAgregado(String mensaje) {
        consola.mostrarExito(mensaje);
    }
    
    
    @Override
    public void mostrarError(String mensaje) {
        consola.mostrarError(mensaje);
    }
}
