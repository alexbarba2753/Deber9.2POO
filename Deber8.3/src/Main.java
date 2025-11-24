import Excepciones.PrecioInvalidoException;
import Excepciones.ProductoNoEncontradoException;
import Excepciones.StockInsuficienteException;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== PRUEBAS DE VALIDACIONES ===");

        // Precio negativo
        try {
            new ProductoElectronico("ELEC-X", "TV", -100, 10, "Sony", 12);
        } catch (PrecioInvalidoException e) {
            System.out.println(e.getMessage());
        }

        // Código vacío
        try {
            new ProductoRopa("", "Camisa", 20, 5, "M", "Algodón");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // INVENTARIO
        Inventario inv = new Inventario("MegaStore");

        // Crear 10 productos válidos
        inv.agregarProducto(new ProductoElectronico(Inventario.generarCodigo("ELEC"), "Laptop", 800, 5, "HP", 12));
        inv.agregarProducto(new ProductoElectronico(Inventario.generarCodigo("ELEC"), "Celular", 500, 10, "Samsung", 12));

        inv.agregarProducto(new ProductoAlimenticio(Inventario.generarCodigo("ALIM"), "Leche", 1.5, 20, LocalDate.now().plusDays(10), true));
        inv.agregarProducto(new ProductoAlimenticio(Inventario.generarCodigo("ALIM"), "Pan", 0.5, 30, LocalDate.now().plusDays(2), false));

        inv.agregarProducto(new ProductoRopa(Inventario.generarCodigo("ROPA"), "Pantalón", 25, 8, "L", "Jeans"));
        inv.agregarProducto(new ProductoRopa(Inventario.generarCodigo("ROPA"), "Camisa", 18, 2, "M", "Algodón"));

        // Otros 4
        inv.agregarProducto(new ProductoElectronico(Inventario.generarCodigo("ELEC"), "Audífonos", 30, 15, "Sony", 6));
        inv.agregarProducto(new ProductoRopa(Inventario.generarCodigo("ROPA"), "Zapatos", 40, 3, "42", "Cuero"));
        inv.agregarProducto(new ProductoAlimenticio(Inventario.generarCodigo("ALIM"), "Yogurt", 1.2, 10, LocalDate.now().plusDays(5), true));
        inv.agregarProducto(new ProductoRopa(Inventario.generarCodigo("ROPA"), "Gorra", 12, 1, "U", "Algodón"));

        System.out.println("\n=== VENTAS ===");
        try {
            double total = inv.venderProducto(inv.buscarPorCodigo(inv.listarProductosBajoStock(100).get(0).getCodigo()).getCodigo(), 1);
            System.out.println("Venta exitosa: $" + total);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Venta con cantidad negativa
        try {
            inv.venderProducto("ELEC-XXXX", -2);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (StockInsuficienteException e) {
            throw new RuntimeException(e);
        }

        // Buscar producto inexistente
        try {
            inv.buscarPorCodigo("NO-EXISTE");
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }

        // Stock insuficiente
        try {
            inv.venderProducto(inv.listarProductosBajoStock(5).get(0).getCodigo(), 999);
        } catch (StockInsuficienteException e) {
            System.out.println(e.getMessage());
        }

        // Calcular valor total
        System.out.println("\nValor total del inventario: $" + inv.calcularValorInventario());

        // Productos bajo stock
        System.out.println("\nProductos con stock < 5:");
        for (Producto p : inv.listarProductosBajoStock(5)) {
            System.out.println(p);
        }

        // Ordenar por precio
        System.out.println("\n=== INVENTARIO ORDENADO POR PRECIO ===");
        inv.ordenarPorPrecio();
        for (Producto p : inv.listarProductosBajoStock(1000)) {
            System.out.println(p);
        }
    }
}
