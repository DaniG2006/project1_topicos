import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

final class PersistenciaCsv {
    private static final Path ARCHIVO = Paths.get("datos_holmwood.csv");

    private PersistenciaCsv() {
    }

    static void cargar() {
        if (!Files.isRegularFile(ARCHIVO)) {
            guardar();
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(ARCHIVO, StandardCharsets.UTF_8)) {
            Datos.MUEBLES.clear();
            Datos.STOCK.clear();
            Datos.VENTAS.clear();
            Datos.GASTOS.clear();
            Datos.APARTADOS.clear();
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> values = parseLine(line);
                if (values.isEmpty()) {
                    continue;
                }
                try {
                    cargarRegistro(values);
                } catch (RuntimeException ignored) {
                }
            }
        } catch (IOException ex) {
            avisarError("No se pudo leer datos_holmwood.csv", ex);
        }
    }

    private static void cargarRegistro(List<String> values) {
        String type = values.get(0);
        switch (type) {
            case "MUEBLE":
                String category = values.get(2);
                String product = values.get(3);
                double price = Double.parseDouble(values.get(5));
                int stock = Integer.parseInt(values.get(6));
                Datos.MUEBLES.add(new Object[]{category, product, values.get(4), price});
                Datos.STOCK.put(product, stock);
                break;
            case "VENTA":
                List<Object[]> lines = new ArrayList<>();
                if (!values.get(5).isEmpty()) {
                    for (String item : values.get(5).split("\\|", -1)) {
                        String[] parts = item.split("~", -1);
                        lines.add(new Object[]{parts[0], Integer.parseInt(parts[1])});
                    }
                }
                Datos.VENTAS.add(new Object[]{values.get(2), values.get(3),
                        Double.parseDouble(values.get(4)), lines});
                break;
            case "GASTO":
                Datos.GASTOS.add(new Object[]{values.get(2), values.get(3),
                        Double.parseDouble(values.get(4)), values.get(5)});
                break;
            case "APARTADO":
                Datos.APARTADOS.add(new Object[]{Integer.parseInt(values.get(1)), values.get(2),
                        values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8)});
                break;
            default:
                break;
        }
    }

    static void guardar() {
        Path backup = Paths.get("datos_holmwood.csv.bak");
        try {
            if (Files.isRegularFile(ARCHIVO)) {
                Files.copy(ARCHIVO, backup, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            avisarError("No se pudo crear el respaldo CSV", ex);
            return;
        }
        try (BufferedWriter writer = Files.newBufferedWriter(ARCHIVO, StandardCharsets.UTF_8)) {
            writer.write("tipo,id,campo1,campo2,campo3,campo4,campo5,campo6,campo7,campo8");
            writer.newLine();
            for (Object[] mueble : Datos.MUEBLES) {
                String product = String.valueOf(mueble[1]);
                write(writer, "MUEBLE", "", String.valueOf(mueble[0]), product,
                        String.valueOf(mueble[2]), String.valueOf(mueble[3]),
                        String.valueOf(Datos.stockDe(product)), "", "");
            }
            for (int i = 0; i < Datos.VENTAS.size(); i++) {
                Object[] venta = Datos.VENTAS.get(i);
                @SuppressWarnings("unchecked")
                List<Object[]> lines = (List<Object[]>) venta[3];
                StringBuilder serializedLines = new StringBuilder();
                for (Object[] line : lines) {
                    if (serializedLines.length() > 0) {
                        serializedLines.append('|');
                    }
                    serializedLines.append(line[0]).append('~').append(line[1]);
                }
                write(writer, "VENTA", String.valueOf(i + 1), String.valueOf(venta[0]),
                        String.valueOf(venta[1]), String.valueOf(venta[2]), serializedLines.toString(), "", "", "");
            }
            for (Object[] gasto : Datos.GASTOS) {
                write(writer, "GASTO", "", String.valueOf(gasto[0]), String.valueOf(gasto[1]),
                        String.valueOf(gasto[2]), String.valueOf(gasto[3]), "", "", "");
            }
            for (Object[] apartado : Datos.APARTADOS) {
                write(writer, "APARTADO", String.valueOf(apartado[0]), String.valueOf(apartado[1]),
                        String.valueOf(apartado[2]), String.valueOf(apartado[3]), String.valueOf(apartado[4]),
                        String.valueOf(apartado[5]), String.valueOf(apartado[6]), String.valueOf(apartado[7]));
            }
        } catch (IOException ex) {
            avisarError("No se pudo guardar datos_holmwood.csv", ex);
        }
    }

    private static void avisarError(String mensaje, IOException error) {
        System.err.println(mensaje + ": " + error.getMessage());
        if (!java.awt.GraphicsEnvironment.isHeadless()) {
            JOptionPane.showMessageDialog(null, mensaje + ".\nRevisa permisos y espacio disponible.",
                    "Error de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void write(BufferedWriter writer, String... values) throws IOException {
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                writer.write(',');
            }
            writer.write(escape(values[i]));
        }
        writer.newLine();
    }

    private static String escape(String value) {
        String safe = value == null ? "" : value;
        return '"' + safe.replace("\"", "\"\"") + '"';
    }

    private static List<String> parseLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean quoted = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (quoted && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    quoted = !quoted;
                }
            } else if (c == ',' && !quoted) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        values.add(current.toString());
        return values;
    }
}
