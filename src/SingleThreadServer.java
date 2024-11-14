import java.io.*;
import java.net.*;

public class SingleThreadServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor sem threads iniciado na porta 5000.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conexão aceita no servidor sem threads: " + clientSocket.getInetAddress());
                handleClient(clientSocket);
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Resposta do servidor sem threads");
            in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
