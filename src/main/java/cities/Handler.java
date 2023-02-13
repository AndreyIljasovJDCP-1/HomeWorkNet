package cities;

import cities.coordinates.Language;
import cities.coordinates.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Handler {
    public static final ObjectMapper mapper = new ObjectMapper();
    public static final String REMOTE_SERVICE_SEARCH = "https://geocoding-api.open-meteo.com/v1/search";
    private final Set<String> citySet = new HashSet<>();
    private String city = "";
    private Float latitude;
    private Float longitude;
    private int clientPort = 0;
    private char lastLetter = ' ';
    private Language language;
    private final Pattern pattern = Pattern.compile(
            "[" +                   //начало списка допустимых символов
                    "а-яА-ЯёЁ" +    //буквы русского алфавита
                    "\\d" +         //цифры
                    "\\s" +         //знаки-разделители (пробел, табуляция и т.д.)
                    "\\p{Punct}" +  //знаки пунктуации
                    "]" +                   //конец списка допустимых символов
                    "*");                   //допускается наличие указанных символов в любом количестве


    public void setCity(String input, int port) {
        String temp = input.split(" ")[1].substring(1);
        //todo если русские буквы проверка то раскодировка
        if (language == Language.RU) {
            temp = URLDecoder.decode(temp, StandardCharsets.UTF_8);
        }

        city = temp.substring(0, 1).toUpperCase()
                + temp.substring(1).toLowerCase();
        lastLetter = city.toLowerCase().charAt(city.length() - 1);
        clientPort = port;
        citySet.add(city.toLowerCase());
    }

    public void setLanguage(String input) {
        String temp = input.split(" ")[1].substring(1);
        //todo если русские буквы проверка то раскодировка
        if (temp.charAt(0) == '%') {
            language = Language.RU;
        } else {
            language = Language.EN;
        }
    }

    public void setCoordinates() {
        try {
            String url = REMOTE_SERVICE_SEARCH
                    + "?name=" + city
                    + "&language=" + this.getLanguage();

            HttpResponse response = Request.Get(url)
                    .execute()
                    .returnResponse();

            Response results = mapper.readValue(response.getEntity().getContent(), Response.class);
            if (results.getResults() != null) {
                latitude = results.getResults().get(0).getLatitude();
                longitude = results.getResults().get(0).getLongitude();
            } else {
                latitude = null;
                longitude = null;
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public boolean isRepeat(String city) {
        return citySet.contains(city.toLowerCase());
    }

    public String getCityFrom(String input) {
        String temp = input.split(" ")[1].substring(1);
        //todo если русские буквы проверка то раскодировка
        if (language == Language.RU) {
            temp = URLDecoder.decode(temp, StandardCharsets.UTF_8);
        }
        return temp;
    }

    public String getCity() {
        return city;
    }

    public char getLastLetter() {
        return lastLetter;
    }

    public int getClientPort() {
        return clientPort;
    }

    public char getFirstLetter(String name) {
        return name.toLowerCase().charAt(0);
    }

    public String getLanguage() {
        return language.name().toLowerCase();
    }

}
