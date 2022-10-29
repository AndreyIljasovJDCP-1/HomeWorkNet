package cities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server2 {
    private final int port;

    Handler handler;

    public Server2(int port, Handler handler) {
        this.port = port;
        this.handler = handler;
    }

    public static void main(String[] args) {
        Handler handler = new Handler();
        Server2 server = new Server2(8085, handler);
        server.start();
    }

    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(
                             clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(
                             clientSocket.getInputStream(), StandardCharsets.UTF_8))) {

                    String input = in.readLine();
                    System.out.println(input);

                    if (input.contains("favicon")) continue;

                    if (input.contains(" / ")) {
                        getResponse(out);
                        continue;
                    }

                    if (handler.getCity().isEmpty()) {
                        handler.setCity(input, clientSocket.getPort());
                        getResponseCity(out, handler, "OK");
                        continue;
                    }

                    String newCity = handler.getCityFrom(input);

                    if (handler.getLastLetter() == handler.getFirstLetter(newCity)) {
                        if (handler.isRepeat(newCity)) {
                            String msg = "Not OK. "
                                    + newCity.substring(0, 1).toUpperCase()
                                    + newCity.substring(1).toLowerCase()
                                    + " - такой город уже называли.<br>";
                            getResponseCity(out, handler, msg);
                            continue;
                        }
                        handler.setCity(input, clientSocket.getPort());
                        getResponseCity(out, handler, "OK");
                    } else {
                        getResponseCity(out, handler, "Not OK");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getResponseCity(PrintWriter out, Handler handler, String ok) {
        String msg = "<h3>" + ok
                + " Порт " + handler.getClientPort()
                + " назвал город: " + handler.getCity() + ".<br>"
                + "Придумайте город на : "
                + Character.toUpperCase(handler.getLastLetter()) + "</h3>";
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html; charset=utf-8");
        out.println();
        out.println(msg);
    }

    private static void getResponse(PrintWriter out) {

        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html; charset=utf-8");
        out.println();
        out.println("<h3>Поиграем в города? Введите город:</h3>");
    }

}
