import Interfaces.Auditable;
import Interfaces.Autenticable;
import Interfaces.MultiFactor;

import java.util.ArrayList;
import java.util.List;

public class AutenticacionBiometrica implements Autenticable, Auditable {
    private String usuario;
    private String reconocimientoFacial;
    private String huellaDactilar;
    private boolean sesionActiva;
    private List<String> historial;

    public AutenticacionBiometrica(String usuario, String huellaDactilar, String reconocimientoFacial) {
        this.usuario = usuario;
        this.huellaDactilar = huellaDactilar;
        this.reconocimientoFacial = reconocimientoFacial;
        this.historial = new ArrayList<>();
    }

    @Override
    public void registrarIntento(String usuario, boolean exitoso) {
        String estado = exitoso ? "Biométrica/Alta" : "Biométrica/Fallida";
        String registro = "["+java.time.LocalDateTime.now() + "]"+estado;
        historial.add(registro);
        System.out.println("[AUDIT] Intento "+(exitoso ? "exitoso" : "fallido")+": "+usuario+" ("+estado+") - "+java.time.LocalDateTime.now());
    }
    @Override
    public List<String> obtenerHistorial(String usuario) {
        return historial;
    }

    @Override
    public boolean autenticar(String usuario, String credencial) {
        System.out.println("Escandeando huella dactilar...");

        boolean huellaOK = this.huellaDactilar.equals(credencial);
        String confianza = huellaOK ? "ALTA" : "BAJA";

        registrarIntento(usuario, huellaOK);

        if (huellaOK){
            System.out.println("✅ Huella Verificada - Confianza : "+confianza);
            System.out.println("✅ Autenticación biométrica exitosa");
            sesionActiva = true;
            return true;
        }
        System.out.println("❌ Huella no coincide");
        return false;
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
