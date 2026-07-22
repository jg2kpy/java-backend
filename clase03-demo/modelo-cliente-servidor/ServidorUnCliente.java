import java.io.*;
import java.net.*;

public class ServidorUnCliente {
    public static void main(String[] args) throws IOException {
        int puerto = 8080;

        ServerSocket serverSocket = new ServerSocket(puerto);
        System.out.println("Servidor escuchando en puerto " + puerto + "...");

        Socket cliente = serverSocket.accept();
        System.out.println("Cliente conectado: " + cliente.getInetAddress());

        BufferedReader entrada = new BufferedReader(
            new InputStreamReader(cliente.getInputStream()));
        PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

        String mensaje = entrada.readLine();
        System.out.println("Recibido: " + mensaje);

        salida.println("Servidor recibió: " + mensaje);

        cliente.close();
        serverSocket.close();
        System.out.println("Conexión cerrada.");
    }
}