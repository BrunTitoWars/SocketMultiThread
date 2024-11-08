import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Servidor {
    private static final int PORTA = 12345;
    private static final int NUM_CLIENTES = 10;

    public static void main(String[] args) throws IOException, InterruptedException {
        // Executar servidor sem threads
        long inicioSemThreads = System.currentTimeMillis();
        servidorSemThreads();
        long fimSemThreads = System.currentTimeMillis();
        System.out.println("Tempo com servidor sem threads: " + (fimSemThreads - inicioSemThreads) + " ms");

        // Espera para liberar a porta e evitar o conflito
        Thread.sleep(2000);

        // Executar servidor com threads
        long inicioComThreads = System.currentTimeMillis();
        servidorComThreads();
        long fimComThreads = System.currentTimeMillis();
        System.out.println("Tempo com servidor com threads: " + (fimComThreads - inicioComThreads) + " ms");
    }

    // Servidor sem threads: processa os clientes sequencialmente
    public static void servidorSemThreads() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORTA);
        System.out.println("Servidor sem threads iniciado.");

        for (int i = 0; i < NUM_CLIENTES; i++) {
            try (Socket clienteSocket = serverSocket.accept()) {
                System.out.println("Cliente " + (i + 1) + " conectado.");
                processarCliente(clienteSocket);
            }
        }

        serverSocket.close();
    }

    // Servidor com threads: processa os clientes simultaneamente usando threads
    public static void servidorComThreads() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORTA);
        System.out.println("Servidor com threads iniciado.");
        ExecutorService pool = Executors.newFixedThreadPool(NUM_CLIENTES);

        for (int i = 0; i < NUM_CLIENTES; i++) {
            Socket clienteSocket = serverSocket.accept();
            System.out.println("Cliente " + (i + 1) + " conectado.");
            pool.execute(() -> {
                try {
                    processarCliente(clienteSocket);
                    clienteSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        pool.shutdown();
        serverSocket.close();
    }

    // Função que simula o processamento de uma requisição de cliente
    private static void processarCliente(Socket clienteSocket) {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(), true);

            String mensagem = entrada.readLine();
            System.out.println("Mensagem recebida do cliente: " + mensagem);

            // Simulação de processamento (espera de 1 segundo)
            Thread.sleep(1000);

            saida.println("Processamento concluído");

            entrada.close();
            saida.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Função para simular clientes automaticamente
    private static void simularClientes(int numeroClientes) {
        for (int i = 0; i < numeroClientes; i++) {
            new Thread(() -> {
                try (Socket socket = new Socket("localhost", PORTA)) {
                    PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    saida.println("Olá, servidor!");
                    String resposta = entrada.readLine();
                    System.out.println("Resposta do servidor: " + resposta);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}