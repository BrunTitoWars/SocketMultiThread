import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Benchmark {
    private static final String HOST = "localhost";
    private static final int PORTA = 12345;
    private static final int NUM_CLIENTES = 10;

    public static void main(String[] args) throws InterruptedException {
        // Testar o servidor sem threads
        long tempoTotalSemThreads = testarServidor(false);
        double mediaSemThreads = (double) tempoTotalSemThreads / NUM_CLIENTES;
        System.out.println("Tempo total sem threads: " + tempoTotalSemThreads + " ms");
        System.out.println("Tempo médio por cliente sem threads: " + mediaSemThreads + " ms");

        // Espera para liberar a porta
        Thread.sleep(2000);

        // Testar o servidor com threads
        long tempoTotalComThreads = testarServidor(true);
        double mediaComThreads = (double) tempoTotalComThreads / NUM_CLIENTES;
        System.out.println("Tempo total com threads: " + tempoTotalComThreads + " ms");
        System.out.println("Tempo médio por cliente com threads: " + mediaComThreads + " ms");
    }

    private static long testarServidor(boolean comThreads) {
        long tempoInicial = System.currentTimeMillis();
        List<Thread> clientes = new ArrayList<>();

        for (int i = 0; i < NUM_CLIENTES; i++) {
            Thread cliente = new Thread(() -> simularCliente(comThreads));
            clientes.add(cliente);
            cliente.start();
        }

        // Aguardar todos os clientes finalizarem
        for (Thread cliente : clientes) {
            try {
                cliente.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return System.currentTimeMillis() - tempoInicial;
    }

    private static void simularCliente(boolean comThreads) {
        try (Socket socket = new Socket(HOST, PORTA);
             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String tipoServidor = comThreads ? "com threads" : "sem threads";
            saida.println("Olá, servidor " + tipoServidor + "!");
            String resposta = entrada.readLine();
            System.out.println("Resposta do servidor (" + tipoServidor + "): " + resposta);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
