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
    private boolean firstRound = true;
    int clientPort = 0;
    char lastLetter = ' ';
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
        String city = "";
        String newCity;

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

                    if (firstRound) {
                        city = handler.getCityName(input);
                        handler.addToCitySet(city);
                        clientPort = clientSocket.getPort();
                        lastLetter = handler.getLastLetter(city);
                        firstRound = false;
                        getResponse(out, "OK");
                        getResponseCity(out, clientPort, city, lastLetter);
                    } else {
                        newCity = handler.getCityName(input);
                        if (lastLetter == handler.getFirstLetter(newCity)) {
                            if (handler.isRepeat(newCity)) {
                                getResponseAlreadyBeen(out, newCity);
                                getResponseCity(out, clientPort, city, lastLetter);
                                continue;
                            }

                            handler.addToCitySet(newCity);
                            lastLetter = handler.getLastLetter(newCity);
                            clientPort = clientSocket.getPort();
                            getResponse(out, "OK");
                            getResponseCity(out, clientPort, newCity, lastLetter);
                            city = newCity;
                        } else {
                            getResponse(out, "Not OK");
                            getResponseCity(out, clientPort, city, lastLetter);
                        }
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void getResponseCity(PrintWriter out, int port, String city, char lastLetter) {
        String msg = "<h3>Порт " + port + " назвал город: "
                + city.substring(0, 1).toUpperCase()
                + city.substring(1).toLowerCase() + ".<br>"
                + "Придумайте город на : "
                + Character.toUpperCase(lastLetter) + "</h3>";
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html; charset=utf-8");
        out.println();
        out.println(msg);
    }

    private static void getResponseAlreadyBeen(PrintWriter out, String newCity) {
        String msg = "<h3>Not OK.<br>"
                + newCity.substring(0, 1).toUpperCase()
                + newCity.substring(1).toLowerCase()
                + " - такой город уже называли.</h3>";
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html; charset=utf-8");
        out.println();
        out.println(msg);
    }

    private static void getResponse(PrintWriter out, String msg) {

        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html; charset=utf-8");
        out.println();
        out.println("<h3>" + msg + "</h3>");
    }

}
