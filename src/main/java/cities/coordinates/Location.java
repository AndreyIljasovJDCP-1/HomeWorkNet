package cities.coordinates;

import java.util.Arrays;

public class Location {
    private Integer id;
    private String name;
    private Float latitude;
    private Float longitude;
    private Float elevation;
    private String timezone;
    private String feature_code;
    private String country_code;
    private String country;
    private Integer country_id;
    private Integer population;
    private String[] postcodes;
    private String admin1;
    private String admin2;
    private String admin3;
    private String admin4;
    private Integer admin1_id;
    private Integer admin2_id;
    private Integer admin3_id;
    private Integer admin4_id;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Float getElevation() {
        return elevation;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getFeature_code() {
        return feature_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getCountry() {
        return country;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public Integer getPopulation() {
        return population;
    }

    public String[] getPostcodes() {
        return postcodes;
    }

    public String getAdmin1() {
        return admin1;
    }

    public String getAdmin2() {
        return admin2;
    }

    public String getAdmin3() {
        return admin3;
    }

    public String getAdmin4() {
        return admin4;
    }

    public Integer getAdmin1_id() {
        return admin1_id;
    }

    public Integer getAdmin2_id() {
        return admin2_id;
    }

    public Integer getAdmin3_id() {
        return admin3_id;
    }

    public Integer getAdmin4_id() {
        return admin4_id;
    }

    @Override
    public String toString() {
        return "Location {" +
                "\nid = " + id +
                ", \nname = '" + name + '\'' +
                ", \nlatitude = " + latitude +
                ", \nlongitude = " + longitude +
                ", \nelevation = " + elevation +
                ", \ntimezone ='" + timezone + '\'' +
                ", \nfeature_code = '" + feature_code + '\'' +
                ", \ncountry_code = '" + country_code + '\'' +
                ", \ncountry = '" + country + '\'' +
                ", country_id = " + country_id +
                ", \npopulation = " + population +
                ", \npostcodes = " + Arrays.toString(postcodes) +
                ", \nadmin1 = '" + admin1 + '\'' +
                ", admin2='" + admin2 + '\'' +
                ", admin3='" + admin3 + '\'' +
                ", admin4='" + admin4 + '\'' +
                ", \nadmin1_id=" + admin1_id +
                ", admin2_id=" + admin2_id +
                ", admin3_id=" + admin3_id +
                ", admin4_id=" + admin4_id +
                '}';
    }
}
