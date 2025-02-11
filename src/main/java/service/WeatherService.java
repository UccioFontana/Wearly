package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WeatherService {


    private static final String API_KEY;
    private static final String IP_API_URL;
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?";

    // Caricamento delle proprietà all'inizio dell'esecuzione
    static {
        Properties properties = new Properties();
        try (InputStream input = WeatherService.class.getClassLoader().getResourceAsStream("api.properties")) {
            if (input == null) {
                throw new IOException("File api.properties non trovato!");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        API_KEY = properties.getProperty("API_KEY");
        IP_API_URL = properties.getProperty("IP_API_URL");
    }

    public WeatherData getWeather() throws IOException {
        // 1. Ottieni la città automatica tramite l'indirizzo IP
        String city = getCityFromIP();

        // 2. Usa la città ottenuta per fare la richiesta meteo
        String url = BASE_URL + "q=" + city + "&appid=" + API_KEY + "&units=metric";  // unità metriche (°C)

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        HttpResponse response = httpClient.execute(request);
        String jsonResponse = EntityUtils.toString(response.getEntity());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        // Estrai temperatura e condizioni meteo
        double temperature = rootNode.path("main").path("temp").asDouble();
        String weatherCondition = rootNode.path("weather").get(0).path("description").asText();

        return new WeatherData(temperature, weatherCondition);
    }

    // Metodo per ottenere la città in base all'indirizzo IP tramite ipinfo.io
    private String getCityFromIP() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(IP_API_URL);  // URL con il token di ipinfo.io

        HttpResponse response = httpClient.execute(request);
        String jsonResponse = EntityUtils.toString(response.getEntity());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        // Estrai la città dalla risposta
        return rootNode.path("city").asText();  // Restituisce la città
    }

    // Classe per memorizzare i dati meteo
    public static class WeatherData {
        private double temperature;
        private String condition;

        public WeatherData(double temperature, String condition) {
            this.temperature = temperature;
            this.condition = condition;
        }

        public double getTemperature() {
            return temperature;
        }

        public String getCondition() {
            return condition;
        }
    }
}