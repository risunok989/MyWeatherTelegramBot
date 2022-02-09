package WatherParser;

import Util.WeatherUtil;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.IOException;

public class Parser {
                        // object//
    private static final String NAME_OBJECT = "name";  // город
                        // weather[0] //
    private static final String WEATHER = "weather";
    private static final String MAIN_O = "main"; // облачность
    private static final String DESCRIPTION_O = "description"; // оописание
    private static final String ICON_O = "icon"; // номер икноки
                        // main //
    private static final String TEMP_MAIN = "temp"; // температура
    private static final String FELLS_LICE = "feels_like"; // ощущается
    private static final String TEMP_MIN = "temp_min";
    private static final String TEMP_MAX = "temp_max";
    private static final String PRESSURE = "pressure"; // давление
    private static final String HUMIDITY = "pressure"; // владность
                        // wind //
    private static final String WIND = "wind"; // object
    private static final String SPEED = "speed"; // скорость ветра
    private static final String GUST = "gust"; // порывы ветра


    public static String parseWeather(String json) {
        System.out.println("parseWeather()");

        JSONParser jsonParser = new JSONParser();
        //обьект самого json {}
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONObject mainObject = (JSONObject) jsonObject.get(MAIN_O);
        JSONObject windObject = (JSONObject) jsonObject.get(WIND);
        JSONArray weatherArrey = (JSONArray) jsonObject.get(WEATHER);
        JSONObject weatherData = (JSONObject) weatherArrey.get(0);


        System.out.println(jsonObject.get(NAME_OBJECT));
        System.out.println(mainObject.get(TEMP_MAIN));
        System.out.println(windObject.get(SPEED));
        System.out.println(weatherData.get(DESCRIPTION_O));





        return (String) ("Текущая температура в городе " + jsonObject.get(NAME_OBJECT) + ": " + mainObject.get(TEMP_MAIN) + "\u00B0" + "."
                + "\nОщущается как : " + mainObject.get(FELLS_LICE) + "\u00B0" + "." + " " +weatherData.get(DESCRIPTION_O) + " "
                + WeatherUtil.weatherIconsCodes.get(weatherData.get(ICON_O))
                + "\nВетер: " + windObject.get(SPEED) + " м/c, с порывами до " + windObject.get(GUST) + " м/c.");
    }

}
