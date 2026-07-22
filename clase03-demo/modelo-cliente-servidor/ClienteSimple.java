import java.io.*;
import java.net.*;

public class ClienteSimple {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8080)) {
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

            salida.println("Hola servidor");

            String respuesta = entrada.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);
        }
    }
}