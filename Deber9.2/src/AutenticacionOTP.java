import Interfaces.Auditable;
import Interfaces.Autenticable;
import Interfaces.MultiFactor;

import java.util.ArrayList;
import java.util.List;

public class AutenticacionOTP implements Autenticable, MultiFactor, Auditable {
    private String usuario;
    private String password;
    private String codigoOTP;
    private boolean sesionActiva;
    private int intentos;
    private List<String> historial;

    public AutenticacionOTP(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
        this.historial = new ArrayList<>();
        this.sesionActiva = false;
    }

    @Override
    public void registrarIntento(String usuario, boolean exitoso) {
        String estado = exitoso ? "OTP OK" : "OTP ERROR";
        String registro = "["+java.time.LocalDateTime.now() + "]"+estado;
        historial.add(registro);
        System.out.println("[AUDIT] Intento "+(exitoso ? "exitoso" : "fallido")+": "+usuario+" (OTP) - "+java.time.LocalDateTime.now());
    }

    @Override
    public List<String> obtenerHistorial(String usuario) {
        return historial;
    }

    @Override
    public boolean autenticar(String usuario, String credencial) {
        boolean valido = this.usuario.equals(usuario) && this.password.equals(credencial);

        if (!valido) {
            return false;
        }

        return true;
    }

    @Override
    public void cerrarSesion() {
        sesionActiva = false;
    }

    @Override
    public boolean sesionActiva() {
        return sesionActiva;
    }

    @Override
    public String generarCodigoVerificacion() {
        codigoOTP = String.format("%06d", (int) (Math.random() * 1000000));
        intentos = 3;
        System.out.println("Código OTP generado: " + codigoOTP);
        System.out.println("Código enviado por SMS");
        return codigoOTP;
    }

    @Override
    public boolean verificarCodigo(String codigo) {
        if (intentos <= 0) {
            System.out.println("❌ Sin intentos restantes");
            return false;
        }

        intentos--;

        if (codigoOTP.equals(codigo)) {
            System.out.println("✅ Código verificado correctamente");
            System.out.println("✅ Autenticación OTP exitosa");
            sesionActiva = true;
            registrarIntento(usuario, true);
            return true;
        } else {
            System.out.println("❌ Código incorrecto - Intentos restantes: " + intentos);
            System.out.println("❌ Autenticación OTP Fallida");
            registrarIntento(usuario, false);
            return false;
        }
    }

    @Override
    public int intentosRestantes() {
        return intentos;
    }

}
