package cities;

import java.util.HashSet;
import java.util.Set;

public class Handler {

    private final Set<String> citySet;

    private int clientPort = 0;
    private char lastLetter = ' ';

    public Handler() {
        this.citySet = new HashSet<>();
    }

    public void addToCitySet(String city) {
        citySet.add(city.toLowerCase());
    }

    public String getCityName(String input) {
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

    public void setLastLetter(String name) {
        lastLetter = name.toLowerCase().charAt(name.length() - 1);

    }

    public char getFirstLetter(String name) {
        return name.toLowerCase().charAt(0);
    }

}
