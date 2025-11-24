import Excepciones.ProductoNoEncontradoException;
import Excepciones.StockInsuficienteException;

import java.util.ArrayList;

public class Inventario {

    private ArrayList<Producto> productos = new ArrayList<>();
    private String nombreTienda;

    public Inventario(String nombreTienda) {
        if (nombreTienda == null || nombreTienda.isEmpty())
            throw new IllegalArgumentException("Nombre de tienda inválido");

        this.nombreTienda = nombreTienda;
    }

    public static String generarCodigo(String categoria) {
        int numero = (int) (Math.random() * 9000) + 1000;
        return categoria + "-" + numero;
    }

    public void agregarProducto(Producto p) {
        if (p == null)
            throw new NullPointerException("Producto no puede ser null");
        productos.add(p);
    }

    public Producto buscarPorCodigo(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo))
                return p;
        }
        throw new ProductoNoEncontradoException("No existe producto con código: " + codigo);
    }

    public double venderProducto(String codigo, int cantidad)
            throws StockInsuficienteException {

        if (cantidad <= 0)
            throw new IllegalArgumentException("Cantidad debe ser mayor a 0");

        Producto p = buscarPorCodigo(codigo);

        if (p.getStock() < cantidad)
            throw new StockInsuficienteException("Stock insuficiente de " + p.getNombre());

        p.setStock(p.getStock() - cantidad);

        return p.calcularPrecioFinal() * cantidad;
    }

    public double calcularValorInventario() {
        if (productos.isEmpty())
            throw new IllegalStateException("Inventario vacío");

        double total = 0;

        for (Producto p : productos) {
            total += p.calcularPrecioFinal() * p.getStock();
        }

        return total;
    }

    public ArrayList<Producto> listarProductosBajoStock(int minimo) {
        ArrayList<Producto> lista = new ArrayList<>();

        for (Producto p : productos) {
            if (p.getStock() < minimo)
                lista.add(p);
        }

        return lista;
    }

    // ORDENAMIENTO MANUAL (Burbuja)
    public void ordenarPorPrecio() {
        for (int i = 0; i < productos.size() - 1; i++) {
            for (int j = 0; j < productos.size() - i - 1; j++) {
                if (productos.get(j).calcularPrecioFinal() >
                        productos.get(j + 1).calcularPrecioFinal()) {

                    Producto aux = productos.get(j);
                    productos.set(j, productos.get(j + 1));
                    productos.set(j + 1, aux);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Inventario de " + nombreTienda + " con " + productos.size() + " productos.";
    }
}
