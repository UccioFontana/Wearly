package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.CapoAbbigliamento;
//import model.Outfit;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OutfitService {
    /*
    private static final String FLASK_SERVER_URL = "http://localhost:5000/generate_outfit";
    private final WeatherService weatherService;

    public OutfitService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public List<Outfit> getOutfits(List<CapoAbbigliamento> userItems) throws IOException {
        WeatherService.WeatherData weatherData = weatherService.getWeather();
        double temperature = weatherData.getTemperature();
        String weatherCondition = weatherData.getCondition();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(new OutfitRequest(userItems, temperature, weatherCondition));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(FLASK_SERVER_URL);
        request.setHeader("Content-Type", "application/json");
        request.setEntity(new StringEntity(jsonBody));

        HttpResponse response = httpClient.execute(request);
        String jsonResponse = EntityUtils.toString(response.getEntity());

        // Parsiamo la risposta JSON
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        List<Outfit> outfits = new ArrayList<>();

        if (rootNode.isArray()) {
            for (JsonNode node : rootNode) {
                outfits.add(parseOutfit(node, objectMapper));
            }
        } else {
            outfits.add(parseOutfit(rootNode, objectMapper));
        }

        return outfits;
    }

    private Outfit parseOutfit(JsonNode node, ObjectMapper objectMapper) {
        JsonNode outfitNode = node.get("outfit");

        String name = "Outfit Generato";
        String description = "Un outfit basato sul meteo attuale";

        List<CapoAbbigliamento> items = new ArrayList<>();
        items.add(new CapoAbbigliamento(outfitNode.get("top_category").asText(), outfitNode.get("top_material").asText()));
        items.add(new CapoAbbigliamento(outfitNode.get("bottom_category").asText(), outfitNode.get("bottom_material").asText()));
        items.add(new CapoAbbigliamento(outfitNode.get("shoes_category").asText(), outfitNode.get("shoes_material").asText()));

        return new Outfit(name, description, items);
    }

    public static class OutfitRequest {
        private List<CapoAbbigliamento> userItems;
        private double temperature;
        private String weatherCondition;

        public OutfitRequest(List<CapoAbbigliamento> userItems, double temperature, String weatherCondition) {
            this.userItems = userItems;
            this.temperature = temperature;
            this.weatherCondition = weatherCondition;
        }

        public List<CapoAbbigliamento> getUserItems() {
            return userItems;
        }

        public void setUserItems(List<CapoAbbigliamento> userItems) {
            this.userItems = userItems;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public String getWeatherCondition() {
            return weatherCondition;
        }

        public void setWeatherCondition(String weatherCondition) {
            this.weatherCondition = weatherCondition;
        }
    }*/
}