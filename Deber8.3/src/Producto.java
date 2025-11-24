import Excepciones.PrecioInvalidoException;

public abstract class Producto {

    private String codigo;
    private String nombre;
    private double precioBase;
    private int stock;

    public Producto(String codigo, String nombre, double precioBase, int stock) {
        if (codigo == null || codigo.isEmpty())
            throw new IllegalArgumentException("Código no puede estar vacío");

        if (nombre == null || nombre.isEmpty())
            throw new IllegalArgumentException("Nombre no puede estar vacío");

        if (precioBase <= 0)
            throw new PrecioInvalidoException("Precio base debe ser mayor que 0");

        if (stock < 0)
            throw new IllegalArgumentException("Stock no puede ser negativo");

        this.codigo = codigo;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.stock = stock;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecioBase() { return precioBase; }
    public int getStock() { return stock; }

    public void setStock(int stock) {
        if (stock < 0)
            throw new IllegalArgumentException("Stock no puede ser negativo");
        this.stock = stock;
    }

    public abstract double calcularPrecioFinal();

    @Override
    public String toString() {
        return codigo + " - " + nombre + " | Precio final: $" + calcularPrecioFinal() + " | Stock: " + stock;
    }
}
