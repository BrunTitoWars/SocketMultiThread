import java.io.*;
import java.net.*;

public class MultiThreadServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("Servidor com threads iniciado na porta 6000.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conexão aceita no servidor com threads: " + clientSocket.getInetAddress());
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Resposta do servidor com threads");
            in.readLine();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
