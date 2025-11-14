package Modelo;

import Vista.InterfazConsola;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestor simple de highscores para modo SOLO.
 * <p>
 * Mantiene las top 5 entradas en un archivo JSON simple.
 * Implementa un fallback sencillo sin depender de librerías externas.
 * </p>
 */
public class HighscoreManager {
    private static final String DEFAULT_FILE = "highscores_solo.json";
    private static final int MAX_ENTRIES = 5;

    private final InterfazConsola consola = InterfazConsola.obtenerInstancia();

    /**
     * Intenta actualizar el archivo de highscores con un nuevo puntaje.
     * Si el puntaje entra en el top-5, guarda y devuelve true.
     * Si no, no modifica el archivo y devuelve false.
     *
     * @param nombre Nombre del jugador
     * @param puntaje Puntaje obtenido
     * @return true si se obtuvo un nuevo highscore y se guardó
     */
    public boolean actualizarHighscore(String nombre, int puntaje) {
        return actualizarHighscore(nombre, puntaje, DEFAULT_FILE);
    }

    /**
     * Versión que recibe ruta de archivo (útil para tests).
     */
    public boolean actualizarHighscore(String nombre, int puntaje, String rutaArchivo) {
        try {
            List<HighscoreEntry> oldEntries = cargarDesdeArchivo(rutaArchivo);

            // Determinar si el nuevo puntaje califica
            boolean isHighscore = false;
            if (oldEntries.size() < MAX_ENTRIES) {
                isHighscore = true;
            } else {
                // Lista ya ordenada de mayor a menor
                HighscoreEntry last = oldEntries.get(oldEntries.size() - 1);
                if (puntaje > last.getPuntaje()) {
                    isHighscore = true;
                }
            }

            if (!isHighscore) {
                return false;
            }

            // Añadir, ordenar y recortar a top N
            oldEntries.add(new HighscoreEntry(nombre, puntaje));
            Collections.sort(oldEntries);
            List<HighscoreEntry> top = oldEntries.subList(0, Math.min(oldEntries.size(), MAX_ENTRIES));
            guardarEnArchivo(top, rutaArchivo);
            return true;
        } catch (IOException e) {
            consola.mostrarError("Error al leer/escribir highscores: " + e.getMessage());
            return false;
        }
    }

    /**
     * Devuelve el top N (hasta MAX_ENTRIES) desde el archivo por defecto.
     */
    public List<HighscoreEntry> obtenerTop() {
        return obtenerTop(DEFAULT_FILE);
    }

    /**
     * Devuelve el top N desde la ruta indicada.
     */
    public List<HighscoreEntry> obtenerTop(String rutaArchivo) {
        try {
            List<HighscoreEntry> all = cargarDesdeArchivo(rutaArchivo);
            return all.subList(0, Math.min(all.size(), MAX_ENTRIES));
        } catch (IOException e) {
            consola.mostrarError("No se pudo leer highscores: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<HighscoreEntry> cargarDesdeArchivo(String rutaArchivo) throws IOException {
        Path path = Path.of(rutaArchivo);
        if (!Files.exists(path)) {
            // Archivo no existe -> retornar vacío
            return new ArrayList<>();
        }

        String contenido = Files.readString(path, StandardCharsets.UTF_8).trim();
        if (contenido.isEmpty()) {
            return new ArrayList<>();
        }

        List<HighscoreEntry> list = new ArrayList<>();
        try {
            String body = contenido;
            if (body.startsWith("[") && body.endsWith("]")) {
                body = body.substring(1, body.length() - 1).trim();
            }
            if (body.isEmpty()) return list;

            // Reemplazar separador estándar '},{' por un separador único y dividir
            String normalized = body.replace("},{", "}||{");
            String[] objetos = normalized.split("\\|\\|");
            for (String obj : objetos) {
                String o = obj.trim();
                if (!o.startsWith("{")) o = "{" + o;
                if (!o.endsWith("}")) o = o + "}";

                String nombre = extraerValorString(o, "nombre");
                int puntaje = extraerValorInt(o, "puntaje");
                if (nombre != null) {
                    list.add(new HighscoreEntry(nombre, puntaje));
                }
            }

        } catch (Exception e) {
            // Si falla el parseo, devolver lista vacía para no romper el flujo
            return new ArrayList<>();
        }

        Collections.sort(list);
        return list;
    }

    private void guardarEnArchivo(List<HighscoreEntry> entries, String rutaArchivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        for (HighscoreEntry e : entries) {
            if (!first) sb.append(",");
            first = false;
            sb.append("{\"nombre\":\"").append(escape(e.getNombre())).append("\",\"puntaje\":").append(e.getPuntaje()).append("}");
        }
        sb.append("]");

        Files.writeString(Path.of(rutaArchivo), sb.toString(), StandardCharsets.UTF_8);
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private String extraerValorString(String json, String key) {
        String keyQuoted = "\"" + key + "\"";
        int idx = json.indexOf(keyQuoted);
        if (idx == -1) return null;
        int colon = json.indexOf(':', idx + keyQuoted.length());
        if (colon == -1) return null;
        int startQuote = json.indexOf('"', colon + 1);
        if (startQuote == -1) return null;
        int endQuote = json.indexOf('"', startQuote + 1);
        if (endQuote == -1) return null;
        return json.substring(startQuote + 1, endQuote);
    }

    private int extraerValorInt(String json, String key) {
        String keyQuoted = "\"" + key + "\"";
        int idx = json.indexOf(keyQuoted);
        if (idx == -1) return 0;
        int colon = json.indexOf(':', idx + keyQuoted.length());
        if (colon == -1) return 0;
        int start = colon + 1;
        // saltar espacios
        while (start < json.length() && Character.isWhitespace(json.charAt(start))) start++;
        int end = start;
        while (end < json.length() && (Character.isDigit(json.charAt(end)) || json.charAt(end) == '-')) end++;
        try {
            return Integer.parseInt(json.substring(start, end));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
