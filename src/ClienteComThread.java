import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteComThread {
    public static void main(String[] args) throws IOException {
        Socket cliente = new Socket("localhost", 12344);

        // Envia uma mensagem para o servidor
        PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);
        saida.println("Olá, servidor!");

        // Lê a resposta do servidor
        BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        String resposta = entrada.readLine();
        System.out.println("Resposta do servidor: " + resposta);

        // Fecha a conexão com o servidor
        cliente.close();
    }
}


