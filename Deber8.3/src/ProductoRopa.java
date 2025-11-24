public class ProductoRopa extends Producto {

    private String talla;
    private String material;

    public ProductoRopa(String codigo, String nombre, double precioBase, int stock,
                        String talla, String material) {
        super(codigo, nombre, precioBase, stock);

        if (talla == null || talla.isEmpty())
            throw new IllegalArgumentException("La talla no puede estar vacía");

        if (material == null || material.isEmpty())
            throw new IllegalArgumentException("Material no puede estar vacío");

        this.talla = talla;
        this.material = material;
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecioBase() * 1.12; // IVA 12%
    }
}
