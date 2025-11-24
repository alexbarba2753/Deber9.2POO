public class Main {
    public static void main(String[] args) {

        System.out.println("=== PRUEBAS DE VALIDACIONES ===");

        try {
            new CuentaAhorros(Banco.generarNumeroCuenta(), "", 100, 0.05);
        } catch (IllegalArgumentException e) {
            System.out.println("Error titular vacío: " + e.getMessage());
        }

        try {
            new CuentaCorriente(Banco.generarNumeroCuenta(), "Alex", -50, 100);
        } catch (IllegalArgumentException e) {
            System.out.println("Error saldo negativo: " + e.getMessage());
        }

        Banco banco = new Banco("Banco Prueba");

        // Crear 6 cuentas
        CuentaAhorros a1 = new CuentaAhorros(Banco.generarNumeroCuenta(), "Luis", 200, 0.02);
        CuentaAhorros a2 = new CuentaAhorros(Banco.generarNumeroCuenta(), "Ana", 500, 0.03);

        CuentaCorriente c1 = new CuentaCorriente(Banco.generarNumeroCuenta(), "Carlos", 300, 200);
        CuentaCorriente c2 = new CuentaCorriente(Banco.generarNumeroCuenta(), "Pedro", 800, 300);

        CuentaInversion i1 = new CuentaInversion(Banco.generarNumeroCuenta(), "Diana", 1000, 200, 0.10);
        CuentaInversion i2 = new CuentaInversion(Banco.generarNumeroCuenta(), "Elena", 1500, 300, 0.08);

        banco.abrirCuenta(a1);
        banco.abrirCuenta(a2);
        banco.abrirCuenta(c1);
        banco.abrirCuenta(c2);
        banco.abrirCuenta(i1);
        banco.abrirCuenta(i2);

        System.out.println("\n=== DEPÓSITOS Y RETIROS ===");

        a1.depositar(100);
        i1.depositar(300);

        try {
            a1.retirar(3000);
        } catch (SaldoInsuficienteException e) {
            System.out.println("Retiro insuficiente: " + e.getMessage());
        }

        try {
            c2.retirar(-50);
        } catch (MontoInvalidoException e) {
            System.out.println("Monto negativo: " + e.getMessage());
        } catch (Exception ignored) {}

        System.out.println("\n=== TRANSFERENCIAS ===");

        try {
            banco.transferir(a2.getNumeroCuenta(), c1.getNumeroCuenta(), 200);
            System.out.println("Transferencia exitosa");
        } catch (Exception e) {
            System.out.println("Error transferencia: " + e.getMessage());
        }

        try {
            banco.transferir(a2.getNumeroCuenta(), "999999", 100);
        } catch (Exception e) {
            System.out.println("Transferencia fallida: " + e.getMessage());
        }

        System.out.println("\n=== SALDO TOTAL ===");
        System.out.println("Total: " + banco.obtenerSaldoTotal());

        System.out.println("\n=== INTERESES ===");

        try {
            banco.aplicarInteresesAhorros();
        } catch (CuentaInactivaException e) {
            System.out.println("Cuenta inactiva: " + e.getMessage());
        }

        System.out.println("\n=== ORDENAR POR SALDO ===");
        banco.ordenarPorSaldo();

        for (CuentaBancaria c : banco.getCuentas()) {
            System.out.println(c);
        }
    }
}
