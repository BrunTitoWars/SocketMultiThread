import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Servidor iniciado na porta 12345. Aguardando conexão...");

        while (true) {
            // Aceita uma conexão de cliente
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

            // Lê a mensagem do cliente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            String mensagemCliente = entrada.readLine();
            System.out.println("Mensagem do cliente: " + mensagemCliente);

            // Envia uma resposta ao cliente
            PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);
            saida.println("Mensagem recebida: " + mensagemCliente);

            // Fecha a conexão com o cliente
            cliente.close();
            System.out.println("Conexão com o cliente encerrada.");
        }
    }
}
