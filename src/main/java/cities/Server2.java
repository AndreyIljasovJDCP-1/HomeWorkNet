package cities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server2 {
    private static final int PORT = 8089;

    public static void main(String[] args) {


        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server started...");
            Set<String> citySet = new HashSet<>();
            boolean firstRound = true;
            char lastLetter = ' ';
            String city = "";
            int port = 0;
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(
                             clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(
                             clientSocket.getInputStream()))) {

                    if (firstRound) {
                        out.println("??????????????");
                        city = in.readLine();
                        citySet.add(city.toLowerCase());
                        lastLetter = getLastLetter(city.toLowerCase());
                        port = clientSocket.getPort();
                        firstRound = false;
                    } else {
                        out.println(port + " назвал город: "
                                + city.substring(0, 1).toUpperCase()
                                + city.substring(1).toLowerCase()
                                + ". Придумайте город на -> "
                                + lastLetter
                        );
                        String newCity = in.readLine();
                        if (citySet.contains(newCity.toLowerCase())) {
                            out.println("Not OK. "
                                    + newCity.substring(0, 1).toUpperCase()
                                    + newCity.substring(1).toLowerCase()
                                    + " - такой город уже называли.");
                            continue;
                        }
                        char firstLetter = newCity.toLowerCase().charAt(0);

                        if (lastLetter == firstLetter) {
                            lastLetter = getLastLetter(newCity.toLowerCase());
                            citySet.add(newCity.toLowerCase());
                            port = clientSocket.getPort();
                            city = newCity;
                            out.println("OK");
                        } else {
                            out.println("Not OK");
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

    /**
     * Возвращает последнюю букву, исключая "плохие" буквы (ыйьъё).
     * Например: Мирный, Грозный, Долгопрудный последняя буква 'н'
     *
     * @param newCity город
     * @return char - последняя буква
     */
    private static char getLastLetter(String newCity) {
        String badLetters = "ыйьъё";
        int i = 0;
        while (badLetters.contains(Character.toString(
                newCity.charAt(newCity.length() - ++i)))) ;

        return newCity.charAt(newCity.length() - i);
    }
}
