import java.time.LocalDate;

public class ProductoAlimenticio extends Producto {

    private LocalDate fechaVencimiento;
    private boolean requiereRefrigeracion;

    public ProductoAlimenticio(String codigo, String nombre, double precioBase, int stock,
                               LocalDate fechaVencimiento, boolean requiereRefrigeracion) {
        super(codigo, nombre, precioBase, stock);

        if (fechaVencimiento == null)
            throw new IllegalArgumentException("Fecha de vencimiento no puede ser nula");

        this.fechaVencimiento = fechaVencimiento;
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecioBase(); // IVA 0%
    }
}
