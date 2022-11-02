package cities.coordinates;

import java.util.List;

public class Response {
    List<Location> results;
    Float generationtime_ms;

    public List<Location> getResults() {
        return results;
    }

    public Float getGenerationtime_ms() {
        return generationtime_ms;
    }

    @Override
    public String toString() {
        return "Response{" +
                "results=" + results +
                ", generationtime_ms=" + generationtime_ms +
                '}';
    }
}
