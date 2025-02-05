package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.CapoAbbigliamento;
import model.CapoAbbigliamentoDAO;
import model.Outfit;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class OutfitService {

    private static final String FLASK_SERVER_URL = "http://localhost:5000/generate_outfit";
    private final WeatherService weatherService;

    public OutfitService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public List<Outfit> getOutfits(List<CapoAbbigliamento> userItems) throws IOException {
        WeatherService.WeatherData weatherData = weatherService.getWeather();
        int temperature = (int) weatherData.getTemperature();
        String weatherCondition = mapWeatherCondition(weatherData.getCondition());

        System.out.println("WEATHER: " + weatherCondition);

        // Creazione della richiesta JSON
        OutfitRequest outfitRequest = new OutfitRequest(userItems, temperature, weatherCondition);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(outfitRequest);


        // Invio della richiesta HTTP
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(FLASK_SERVER_URL);
            request.setHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(jsonBody));

            HttpResponse response = httpClient.execute(request);
            String jsonResponse = EntityUtils.toString(response.getEntity());
            System.out.println("RISPOSTA JSON: " + jsonResponse);
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            return parseOutfits(rootNode);
        }
    }

    private String mapWeatherCondition(String condition) {
        return switch (condition.toLowerCase()) {
            case "clear sky", "few clouds", "scattered clouds", "broken clouds", "overcast clouds" -> "Cloudy";
            case "shower rain", "rain", "thunderstorm" -> "Rainy";
            case "sunny" -> "Sunny";
            case "windy", "squall", "dust", "smoke", "haze", "fog" -> "Windy";
            case "snow", "snowy" -> "Snowy";
            default -> "Unknown";
        };
    }

    private List<Outfit> parseOutfits(JsonNode rootNode) {
        List<Outfit> outfits = new ArrayList<>();
        if (rootNode.isArray()) {
            for (JsonNode node : rootNode) {
                outfits.add(parseOutfit(node));
            }
        } else {
            outfits.add(parseOutfit(rootNode));
        }
        return outfits;
    }

    private Outfit parseOutfit(JsonNode node) {
        JsonNode outfitNode = node.get("outfit");
        if (outfitNode == null) return null;

        String name = "Outfit Generato";
        String description = "Un outfit basato sul meteo attuale";
        CapoAbbigliamentoDAO capoAbbigliamentoDAO = new CapoAbbigliamentoDAO();
        List<CapoAbbigliamento> items = Arrays.asList(
                getCapoSafe(capoAbbigliamentoDAO, outfitNode, "top"),
                getCapoSafe(capoAbbigliamentoDAO, outfitNode, "bottom"),
                getCapoSafe(capoAbbigliamentoDAO, outfitNode, "shoes")
        );

        Outfit o = new Outfit(name, description);
        o.setListaCapi(items);
        return o;
    }

    private CapoAbbigliamento getCapoSafe(CapoAbbigliamentoDAO dao, JsonNode node, String prefix) {
        if (node.has(prefix + "_category") && node.has(prefix + "_material") && node.has(prefix + "_color")) {
            String categoria = node.get(prefix + "_category").asText();
            String materiale = node.get(prefix + "_material").asText();

            // Converti il colore in stringa e rimuovi gli spazi per matchare il database
            JsonNode colorNode = node.get(prefix + "_color");
            String colore = colorNode.isArray()
                    ? Arrays.toString(new int[]{colorNode.get(0).asInt(), colorNode.get(1).asInt(), colorNode.get(2).asInt()}).replace(" ", "")
                    : colorNode.asText().replace(" ", ""); // Rimuove eventuali spazi extra


            return dao.getCapoByCategoriaMaterialeColore(categoria, materiale, colore);
        }
        return null;
    }

    public static class OutfitRequest {
        private final Map<String, List<Map<String, Object>>> user_items;
        private final int temperature;
        private final String weather_condition;

        public OutfitRequest(List<CapoAbbigliamento> items, int temperature, String weatherCondition) {
            this.user_items = categorizeUserItems(items);
            this.temperature = temperature;
            this.weather_condition = weatherCondition;
        }

        private Map<String, List<Map<String, Object>>> categorizeUserItems(List<CapoAbbigliamento> items) {
            Map<String, List<Map<String, Object>>> categorizedItems = new HashMap<>();
            categorizedItems.put("tops", new ArrayList<>());
            categorizedItems.put("bottoms", new ArrayList<>());
            categorizedItems.put("shoes", new ArrayList<>());

            for (CapoAbbigliamento item : items) {
                String key = switch (item.getParteDelCorpo().toLowerCase()) {
                    case "top" -> "tops";
                    case "bottom" -> "bottoms";
                    case "shoes" -> "shoes";
                    default -> null;
                };

                if (key != null) {
                    categorizedItems.get(key).add(mapItem(item, key));
                }
            }
            return categorizedItems;
        }

        private Map<String, Object> mapItem(CapoAbbigliamento item, String categoryKey) {
            Map<String, Object> mappedItem = new HashMap<>();

            // Aggiungi la condizione per escludere "shoes" dall'eliminare l'ultima lettera
            String modifiedCategoryKey = categoryKey;
            if (!categoryKey.equals("shoes")) {
                modifiedCategoryKey = categoryKey.substring(0, categoryKey.length() - 1);
            }

            mappedItem.put(modifiedCategoryKey + "_category", item.getCategoria());
            mappedItem.put(modifiedCategoryKey + "_material", item.getMateriale());

            // Conversione del colore in formato array [R, G, B]
            int[] rgbColor = parseColor(item.getColore());
            mappedItem.put(modifiedCategoryKey + "_color", rgbColor);

            // Simulazione di un valore per il color_code (può essere aggiornato con un calcolo specifico)
            mappedItem.put(modifiedCategoryKey + "_color_code", calculateColorCode(rgbColor));

            return mappedItem;
        }

        private int[] parseColor(String colorString) {
            if (colorString.startsWith("[") && colorString.endsWith("]")) {
                colorString = colorString.substring(1, colorString.length() - 1);
            }
            String[] parts = colorString.split(",");
            int[] rgb = new int[3];
            for (int i = 0; i < parts.length && i < 3; i++) {
                rgb[i] = Integer.parseInt(parts[i].trim());
            }
            return rgb;
        }

        private double calculateColorCode(int[] rgb) {
            return (0.3 * rgb[0] + 0.59 * rgb[1] + 0.11 * rgb[2]); // Formula semplificata per luminosità
        }

        public Map<String, List<Map<String, Object>>> getUserItems() {
            return user_items;
        }

        public int getTemperature() {
            return temperature;
        }

        public String getWeatherCondition() {
            return weather_condition;
        }
    }
}