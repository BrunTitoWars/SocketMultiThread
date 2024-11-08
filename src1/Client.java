import java.io.*;
import java.net.*;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORTA)) {
            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Envia uma mensagem para o servidor
            saida.println("Olá, servidor!");

            // Recebe e exibe a resposta do servidor
            String resposta = entrada.readLine();
            System.out.println("Resposta do servidor: " + resposta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
