package WatherParser;


import Bot.Bot;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class JsonDownloadForOpenWeather {


    private static final String API_CALL_TEMPLATE = "https://api.openweathermap.org/data/2.5/weather?id=";
    private final static String API_KEY_TEMPLATE = Bot.getTokenWeather();
    public static final String UNITS = "&units=metric";
    public static final String LANG = "&lang=ru";
    private final static DateTimeFormatter INPUT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static DateTimeFormatter OUTPUT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MMM-dd HH:mm", Locale.US);


    public static String downloadJsonData(String city) throws IOException {
        System.out.println("PARSER");
        String urlString = API_CALL_TEMPLATE + city + LANG + UNITS + API_KEY_TEMPLATE;

        URL urlObject = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = connection.getResponseCode();
        if (responseCode == 404) {
            throw new IllegalArgumentException("ERROR 404");
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
        return response.toString();
    }

    private static final String filePath = "D:\\1/response-weather.json";


}
