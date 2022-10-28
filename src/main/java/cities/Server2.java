package cities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class Server2 {
    private final int port;
    private boolean firstRound = true;
    private final Set<String> citySet;
    private String city=null;
    char lastLetter = ' ';
    int clientPort = 0;
    public Server2(int port) {
        this.port = port;
        this.citySet = new HashSet<>();
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


                    if (firstRound) {
                        String input = in.readLine();
                        System.out.println(input);
                        city = getCity(input);
                        citySet.add(city.toLowerCase());
                        lastLetter = getLastLetter(city.toLowerCase());
                        clientPort = clientSocket.getPort();
                        firstRound = false;
                    } else {
                        String input = in.readLine();
                        System.out.println(input);
                        if (input.contains("favicon")) continue;
                        String newCity = getCity(input);

                        if (lastLetter == getFirstLetter(newCity)) {
                            if (citySet.contains(newCity.toLowerCase())) {
                                getResponseAlreadyBeen(out, newCity);
                                getResponseCity(out, clientPort, city, lastLetter);
                                continue;
                            }
                            lastLetter = getLastLetter(newCity.toLowerCase());
                            citySet.add(newCity.toLowerCase());
                            clientPort = clientSocket.getPort();
                            city = newCity;
                            //out.println("OK");
                            getResponse(out, "OK");
                            getResponseCity(out, clientPort, city, lastLetter);
                        } else {
                            //out.println("Not OK");
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

    public static void main(String[] args) {
        Server2 server = new Server2(8085);
        server.start();
    }


    private static char getLastLetter(String city) {

        return city.charAt(city.length() - 1);
    }
    private static char getFirstLetter(String newCity) {
        return newCity.toLowerCase().charAt(0);
    }

    private static void firstRound(PrintWriter out) {
        String msg = "<p>????????????</p>";
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html; charset=utf-8");
        out.println();
        out.println(msg);
    }

    private static void getResponseCity(PrintWriter out, int port, String city, char lastLetter) {
        String msg = "<h2>Порт " + port + " назвал город: "
                + city.substring(0, 1).toUpperCase()
                + city.substring(1).toLowerCase() + ".<br>"
                + "Придумайте город на : "
                + Character.toUpperCase(lastLetter) + "</h2>";
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html; charset=utf-8");
        out.println();
        out.println(msg);
    }

    private static void getResponseAlreadyBeen(PrintWriter out, String newCity) {
        String msg = "<h2>Not OK.<br>"
                + newCity.substring(0, 1).toUpperCase()
                + newCity.substring(1).toLowerCase()
                + " - такой город уже называли.</h2>";
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html; charset=utf-8");
        out.println();
        out.println(msg);
    }

    private static void getResponse(PrintWriter out, String msg) {

        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html; charset=utf-8");
        out.println();
        out.println("<h2>" + msg + "</h2>");
    }

    private static String getCity(String input) throws IOException {
        return input.split(" ")[1].substring(1);

    }
}
