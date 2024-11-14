import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Benchmark {
    private static final int CLIENT_COUNT = 10;

    public static void main(String[] args) {
        System.out.println("Iniciando benchmark...");

        long singleThreadTime = benchmarkServer("localhost", 5000);
        long multiThreadTime = benchmarkServer("localhost", 6000);

        System.out.println("Tempo do servidor sem threads: " + singleThreadTime + " ms");
        System.out.println("Tempo do servidor com threads: " + multiThreadTime + " ms");
    }

    private static long benchmarkServer(String host, int port) {
        ExecutorService executor = Executors.newFixedThreadPool(CLIENT_COUNT);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < CLIENT_COUNT; i++) {
            executor.execute(() -> {
                try (Socket socket = new Socket(host, port);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    in.readLine(); // Lendo a resposta do servidor
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
