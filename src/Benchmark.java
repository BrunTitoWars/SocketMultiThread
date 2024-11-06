import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Benchmark {
    private static final int NUM_CLIENTES = 100; // Número de clientes simultâneos
    private static final String HOST = "localhost";
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        // Executor para gerenciar múltiplos clientes
        ExecutorService executor = Executors.newFixedThreadPool(NUM_CLIENTES);
        long tempoInicio = System.currentTimeMillis();

        // Envia as requisições simultâneas
        for (int i = 0; i < NUM_CLIENTES; i++) {
            executor.submit(() -> {
                try {
                    Socket cliente = new Socket(HOST, PORTA);

                    // Envia uma mensagem ao servidor
                    PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);
                    saida.println("Mensagem do cliente");

                    // Lê a resposta do servidor
                    BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    entrada.readLine(); // Recebe a resposta (não armazenada para simplificação)

                    cliente.close();
                } catch (IOException e) {
                    System.out.println("Erro na conexão do cliente: " + e.getMessage());
                }
            });
        }

        // Aguarda todos os clientes terminarem e calcula o tempo final
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES); // Aguarda no máximo 1 minuto
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long tempoFim = System.currentTimeMillis();
        long tempoTotal = tempoFim - tempoInicio;
        System.out.println("Tempo total para " + NUM_CLIENTES + " clientes: " + tempoTotal + " ms");
        System.out.println("Tempo médio por cliente: " + (tempoTotal / (double) NUM_CLIENTES) + " ms");
    }
}