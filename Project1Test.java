import java.nio.file.Files;
import java.nio.file.Paths;

public class Project1Test {
    public static void main(String[] args) {
        PersistenciaCsv.cargar();
        check(Datos.existeProducto("Sala Verona"), "El producto inicial debe existir");
        check(Datos.precioDe("Sala Verona") > 0, "El producto debe tener precio");
        check(Datos.stockDe("Sala Verona") >= 0, "El stock no puede ser negativo");
        check(Datos.siguienteIdApartado() > 0, "Debe calcular el siguiente ID");
        check(Files.isRegularFile(Paths.get("datos_holmwood.csv")), "Debe existir el CSV de datos");
        System.out.println("Project1Test: OK");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
