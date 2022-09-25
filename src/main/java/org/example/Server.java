package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8085;

    public static void main(String[] args) {


        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server started...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(
                             clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(
                             clientSocket.getInputStream()))) {

                    System.out.println(in.readLine() + " from port: "
                            + clientSocket.getPort());

                    out.println("Network connected to -> "
                            + clientSocket.getLocalSocketAddress());
                    out.println("Knock, knock, Neo.");
                    out.println("Follow the white rabbit...");
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
