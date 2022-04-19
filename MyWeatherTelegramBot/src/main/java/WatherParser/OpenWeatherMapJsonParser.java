package WatherParser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.glassfish.jersey.server.JSONP;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class OpenWeatherMapJsonParser implements WatherParser {



    private static final String API_CALL_TEMPLATE = "https://api.openweathermap.org/data/2.5/weather?id=";
    private final static String API_KEY_TEMPLATE = "&appid=7f9c8827806a797d89d37d20a9152291";
    public static final String UNITS = "&units=metric";
    public static final String LANG = "&lang=ru";
    private final static DateTimeFormatter INPUT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static DateTimeFormatter OUTPUT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MMM-dd HH:mm", Locale.US);

    @Override
    public String getReadyForecast(String city) {
        return null;
    }

    public static String downloadJsonRawData(String city) throws IOException {
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

        try {
            FileWriter file = new FileWriter("D:\\1/response-weather.json");
            file.write(response.toString());
            file.close();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        System.out.println(response.toString());
        return response.toString();
    }

    public static SendMessage selectCountry(long chatID){
        InlineKeyboardMarkup inlineKeyboardMarkup1 = new InlineKeyboardMarkup();

        System.err.println("procces selectCountry");

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Лида");
        inlineKeyboardButton1.setCallbackData("626081");

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Волковыск");
        inlineKeyboardButton2.setCallbackData("620391");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup1.setKeyboard(rowList);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatID));
        sendMessage.setText("Выберите один из городов. Пока только так.");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup1);

        return sendMessage;
    }


    private static final String filePath = "D:\\1/response-weather.json";




//    public static void parseWeatherJson (String resultJson) throws InterruptedException {
//        Thread.sleep(3500);
//        System.out.println("parseWeatherJson спит 3500 mils");
//
//        try {
//            JSONObject weatherJsonObject = (JSONObject) JSONValue.parseWithException(resultJson);
//
//            System.out.println("Погода в городе : " + weatherJsonObject.get("name"));
//        }catch (org.json.simple.parser.ParseException e){
//            e.printStackTrace();
//        }
//    }

}
