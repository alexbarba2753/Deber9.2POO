public class Main {
    public static void main(String[] args) {
        System.out.println("==== SISTEMA DE AUTENTIFICACIÓN ====");
        System.out.println("--- Autenticación Básica ---");

        AutenticacionBasica autBasica = new AutenticacionBasica("admin", "admin123");
        autBasica.autenticar("admin", "admin123");
        autBasica.autenticar("admin","wrongpass");

        System.out.println("\n--- Autenticación OTP ---");

        AutenticacionOTP otp = new AutenticacionOTP("alex.barba","secure456");

        if (otp.autenticar("alex.barba", "secure456")) {

            String codigoGenerado = otp.generarCodigoVerificacion();

            System.out.println("Ingresando código: " + codigoGenerado);
            otp.verificarCodigo(codigoGenerado);
        }

        System.out.println("\n--- Autenticación Biométrica ---");

        AutenticacionBiometrica autBio = new AutenticacionBiometrica("maria.lopez", "HUELLA123", "ROSTRO123");

        autBio.autenticar("maria.lopez", "HUELLA123");

        System.out.println("\n--- Historial de Usuario: admin ---");
        for (String log : autBasica.obtenerHistorial("admin")) {
            System.out.println(log);
        }

        System.out.println("\n--- Prueba de Bloqueo ---");

        AutenticacionBasica bloqueo = new AutenticacionBasica("alex", "1234");

        for (int i = 1; i <= 4; i++) {
            System.out.print("Intento " + i + ": ");
            bloqueo.autenticar("alex", "fail");
        }

    }
}