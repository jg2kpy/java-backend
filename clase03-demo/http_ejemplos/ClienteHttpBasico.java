import java.io.*;
import java.net.*;

public class ClienteHttpBasico {

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int puerto = 8080;

        // Socket abre la conexión TCP con el servidor
        try (Socket socket = new Socket(host, puerto)) {

            OutputStream out = socket.getOutputStream();
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

            // Armamos la request HTTP a mano, siguiendo el protocolo
            String request =
                "GET /saludo HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n" +
                "\r\n"; // línea vacía obligatoria = fin de headers

            out.write(request.getBytes());
            out.flush();

            // Leemos la respuesta línea por línea
            String linea;
            System.out.println("--- Respuesta del servidor ---");
            while ((linea = in.readLine()) != null) {
                System.out.println(linea);
            }
        }
    }
}