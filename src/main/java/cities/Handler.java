package cities;

import java.util.HashSet;
import java.util.Set;

public class Handler {

    private final Set<String> citySet= new HashSet<>();
    private String city="";
    private int clientPort = 0;
    private char lastLetter = ' ';

    public void addToCitySet() {
        citySet.add(city.toLowerCase());
    }

    public String getCity() {
        return city;
    }

    public void setCity(String input) {
        String temp = input.split(" ")[1].substring(1);
        city = temp.substring(0, 1).toUpperCase()
                + temp.substring(1).toLowerCase();
    }

    public String getCityFrom(String input) {
        return input.split(" ")[1].substring(1);
    }

    public boolean isRepeat(String city) {
        return citySet.contains(city.toLowerCase());
    }

    public char getLastLetter() {
        return lastLetter;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }

    public void setLastLetter() {
        lastLetter = city.toLowerCase().charAt(city.length() - 1);

    }

    public char getFirstLetter(String name) {
        return name.toLowerCase().charAt(0);
    }

}
