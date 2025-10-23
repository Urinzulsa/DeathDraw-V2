//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Partida partida = new Partida();

        try {
            System.out.println(partida.agregarJugador("Morena"));
            System.out.println(partida.agregarJugador("Ramona"));
        } catch (Exception e) {
            System.err.println("Error al agregar jugadores: " + e.getMessage());
        }

        try {
            String resultado = partida.iniciarPartida();
            System.out.println(resultado);
            // Start the automated turn loop
            partida.turno();
        } catch (Exception e) {
            System.err.println("Error al iniciar la partida: " + e.getMessage());
        }
    }
}