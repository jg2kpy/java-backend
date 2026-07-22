import java.io.*;
import java.net.*;

public class ServidorMultihilo {
    public static void main(String[] args) throws IOException {
        int puerto = 8080;

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor multihilo escuchando en puerto " + puerto + "...");

            while (true) {
                Socket cliente = serverSocket.accept(); // sigue bloqueando, uno por vez
                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                // Delegamos la atención de ESTE cliente a un hilo nuevo,
                // para que el servidor pueda volver a hacer accept() de inmediato.
                Thread hiloCliente = new Thread(() -> atenderCliente(cliente));
                hiloCliente.start();
            }
        }
    }

    private static void atenderCliente(Socket cliente) {
        try (cliente;
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)) {

            String mensaje = entrada.readLine();
            System.out.println("[" + Thread.currentThread().getName() + "] Recibido: " + mensaje);

            // Simula algo de trabajo, para que se note que otros clientes
            // no quedan bloqueados esperando a este.
            Thread.sleep(2000);

            salida.println("Servidor recibió: " + mensaje);
        } catch (IOException | InterruptedException e) {
            System.out.println("Error atendiendo cliente: " + e.getMessage());
        }
    }
}