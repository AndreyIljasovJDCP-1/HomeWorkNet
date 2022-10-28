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

                    response(out, clientSocket);

                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void response(PrintWriter out, Socket clientSocket) {
        String host = clientSocket.getInetAddress().getHostName() + " : "
                + clientSocket.getLocalPort() + " : " + clientSocket.getPort();
        String msg = "<h1>Network connected to " + host + "</h1>"
                + "<p>Knock, knock, Neo.<br>"
                + "Follow the white rabbit...</p>";
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html; charset=utf-8");
        out.println();
        out.println(msg);

    }
}
