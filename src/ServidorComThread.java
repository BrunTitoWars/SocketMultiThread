import java.io.*;
import java.net.*;

public class ServidorComThread {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(12344);
        System.out.println("Servidor iniciado na porta 12344. Aguardando conexões...");

        while (true) {
            // Aceita uma conexão de cliente
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

            // Cria uma nova thread para o cliente conectado
            Thread thread = new Thread(new ClienteHandler(cliente));
            thread.start();
        }
    }
}

// Classe ClienteHandler que implementa Runnable e gerencia a conexão com o cliente
class ClienteHandler implements Runnable {
    private Socket cliente;

    public ClienteHandler(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            // Lê a mensagem enviada pelo cliente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            String mensagemCliente = entrada.readLine();
            System.out.println("Mensagem do cliente: " + mensagemCliente);

            // Envia uma resposta ao cliente
            PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);
            saida.println("Mensagem recebida: " + mensagemCliente);

            // Fecha a conexão com o cliente
            cliente.close();
            System.out.println("Conexão com o cliente encerrada.");
        } catch (IOException e) {
            System.out.println("Erro ao manipular o cliente: " + e.getMessage());
        }
    }
}
