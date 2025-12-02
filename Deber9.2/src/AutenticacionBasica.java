import Interfaces.Auditable;
import Interfaces.Autenticable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AutenticacionBasica implements Autenticable, Auditable {
    private String usuario;
    private String password;
    private boolean sesionActiva;
    private int intentos;
    private List<String> historial;

    public AutenticacionBasica(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
        this.sesionActiva = false;
        this.intentos = 0;
        this.historial = new ArrayList<>();
    }

    @Override
    public void registrarIntento(String usuario, boolean exitoso) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaFormateada = LocalDateTime.now().format(formato);

        String estado = exitoso ? "LOGIN EXITOSO" : "LOGIN FALLIDO";
        String registro = "["+java.time.LocalDateTime.now() + "]"+estado;
        historial.add(registro);
        System.out.println("[AUDIT] Intento "+(exitoso ? "exitoso" : "fallido")+": "+usuario+" - "+fechaFormateada);
    }

    @Override
    public List<String> obtenerHistorial(String usuario) {
        return historial;
    }

    @Override
    public boolean autenticar(String usuario, String credencial) {
        if (intentos >= 3){
            System.out.println("CUENTA BLOQUEADA - Contacte al administrador");
            return false;
        }

        boolean valido = this.usuario.equals(usuario) && this.password.equals(credencial);

        registrarIntento(usuario, valido);

        if (valido){
            System.out.println("✅ Autentificación Exitosa");
            sesionActiva = true;
            intentos = 0;
            return true;
        } else {
            System.out.println("❌ Autentificación Fallida");
            intentos++;
            return false;
        }

    }

    @Override
    public void cerrarSesion() {
        sesionActiva = false;
    }

    @Override
    public boolean sesionActiva() {
        return sesionActiva;
    }
}
