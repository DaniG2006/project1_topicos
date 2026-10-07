import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class Datos {
    static final List<Object[]> MUEBLES = new ArrayList<>();
    static final Map<String, Integer> STOCK = new LinkedHashMap<>();
    static final List<Object[]> VENTAS = new ArrayList<>();
    static final List<Object[]> GASTOS = new ArrayList<>();
    static final List<Object[]> APARTADOS = new ArrayList<>();

    private Datos() {
    }

    static {
        MUEBLES.add(new Object[]{"Sala", "Sala Verona", "Sala de 3 piezas", 8500.0});
        MUEBLES.add(new Object[]{"Sala", "Sala Oslo", "Sala de 3 piezas", 9800.0});
        MUEBLES.add(new Object[]{"Comedor", "Comedor Roma", "Mesa con 6 sillas y buffet", 6100.0});
        MUEBLES.add(new Object[]{"Recámara", "Recámara Kyoto", "Cama king con 2 burós", 5300.0});
        MUEBLES.add(new Object[]{"Oficina", "Escritorio Nordic", "Escritorio con cajonera", 3700.0});
        MUEBLES.add(new Object[]{"Infantil", "Cuna Luna", "Cuna convertible para bebé", 2900.0});

        STOCK.put("Sala Verona", 5);
        STOCK.put("Sala Oslo", 4);
        STOCK.put("Comedor Roma", 5);
        STOCK.put("Recámara Kyoto", 0);
        STOCK.put("Escritorio Nordic", 8);
        STOCK.put("Cuna Luna", 3);

        APARTADOS.add(new Object[]{1, "Ruben", "Sala Verona", "$3,000.00", "$1,200.00", "$1,800.00", "2026-09-30", "Pendiente"});
        APARTADOS.add(new Object[]{2, "Roberto", "Comedor Roma", "$5,500.00", "$2,500.00", "$3,000.00", "2026-10-02", "Pagado"});
        APARTADOS.add(new Object[]{3, "Carolina", "Recámara Kyoto", "$4,000.00", "$1,500.00", "$2,500.00", "2026-10-05", "Vigente"});
    }

    static Object[] buscarProducto(String producto) {
        for (Object[] mueble : MUEBLES) {
            if (mueble[1].equals(producto)) {
                return mueble;
            }
        }
        return null;
    }

    static boolean existeProducto(String producto) {
        return buscarProducto(producto) != null;
    }

    static String tipoDe(String producto) {
        Object[] mueble = buscarProducto(producto);
        return mueble == null ? "" : String.valueOf(mueble[0]);
    }

    static double precioDe(String producto) {
        Object[] mueble = buscarProducto(producto);
        return mueble == null ? 0 : (Double) mueble[3];
    }

    static int stockDe(String producto) {
        return STOCK.getOrDefault(producto, 0);
    }

    static void asegurarProducto(String producto, int stockInicial) {
        if (producto != null && !producto.isBlank()) {
            STOCK.putIfAbsent(producto, stockInicial);
        }
    }

    static void renombrarProducto(String anterior, String nuevo) {
        if (anterior == null || nuevo == null || anterior.equals(nuevo)) {
            return;
        }
        Integer stock = STOCK.remove(anterior);
        if (stock != null) {
            STOCK.put(nuevo, stock);
        } else {
            STOCK.putIfAbsent(nuevo, 0);
        }
    }

    static void eliminarProducto(String producto) {
        if (producto == null) {
            return;
        }
        MUEBLES.removeIf(mueble -> mueble[1].equals(producto));
        STOCK.remove(producto);
        PersistenciaCsv.guardar();
    }

    static int siguienteIdApartado() {
        int maximo = 0;
        for (Object[] apartado : APARTADOS) {
            maximo = Math.max(maximo, (Integer) apartado[0]);
        }
        return maximo + 1;
    }

    static double totalVentas() {
        double total = 0;
        for (Object[] venta : VENTAS) {
            total += (Double) venta[2];
        }
        return total;
    }
}
