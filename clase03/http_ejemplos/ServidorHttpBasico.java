import java.io.*;
import java.net.*;

public class ServidorHttpBasico {

    public static void main(String[] args) throws IOException {
        int puerto = 8080;

        // ServerSocket escucha conexiones entrantes en un puerto TCP
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor escuchando en el puerto " + puerto + "...");

            while (true) {
                // accept() bloquea hasta que un cliente se conecta
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                manejarCliente(cliente);
            }
        }
    }

    private static void manejarCliente(Socket cliente) throws IOException {
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(cliente.getInputStream()));
            OutputStream out = cliente.getOutputStream()
        ) {
            // Leemos la request línea por línea hasta la línea vacía
            // (esa línea vacía marca el fin de los headers en HTTP)
            String linea;
            String primeraLinea = null;

            while ((linea = in.readLine()) != null && !linea.isEmpty()) {
                if (primeraLinea == null) {
                    primeraLinea = linea; // ej: "GET /saludo HTTP/1.1"
                }
                System.out.println("  " + linea);
            }

            System.out.println("Request line: " + primeraLinea);

            // Armamos el cuerpo de la respuesta
            String cuerpo = "<html><body><h1>Hola desde Java puro</h1></body></html>";

            // Armamos la respuesta HTTP a mano: status line + headers + línea vacía + body
            String respuesta =
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: " + cuerpo.getBytes().length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                cuerpo;

            out.write(respuesta.getBytes());
            out.flush();
        } finally {
            cliente.close();
        }
    }
}