package cities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {

    private static final int PORT = 8089;
    private static final String HOST = "localhost";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {

            try (Socket clientSocket = new Socket(HOST, PORT);
                 PrintWriter out = new PrintWriter(
                         clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(
                         clientSocket.getInputStream()))) {

                String serverResponse = in.readLine();

                if (serverResponse.contains("?")) {
                    System.out.println("Вы первый! Введите город:");
                    out.println(scanner.nextLine());

                } else {
                    System.out.println("-".repeat(serverResponse.length()));
                    System.out.println(serverResponse);
                    System.out.println("Введите город:");
                    out.println(scanner.nextLine());
                    System.out.println(in.readLine());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
