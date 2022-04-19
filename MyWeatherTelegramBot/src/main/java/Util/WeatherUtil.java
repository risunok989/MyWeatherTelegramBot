package Util;

import com.vdurmont.emoji.EmojiParser;

import java.util.HashMap;
import java.util.Map;

public class WeatherUtil {
    public final static Map<String, String> weatherIconsCodes = new HashMap<>();

    static {
        weatherIconsCodes.put("01d", EmojiParser.parseToUnicode(":sunny:"));
        weatherIconsCodes.put("01n", EmojiParser.parseToUnicode(":sunny:"));
        weatherIconsCodes.put("02d", EmojiParser.parseToUnicode(":partly_sunny:"));
        weatherIconsCodes.put("02n", EmojiParser.parseToUnicode(":partly_sunny:"));
        weatherIconsCodes.put("03d", EmojiParser.parseToUnicode(":cloud:"));
        weatherIconsCodes.put("03n", EmojiParser.parseToUnicode(":cloud:"));
        weatherIconsCodes.put("04d", EmojiParser.parseToUnicode(":cloud:"));
        weatherIconsCodes.put("04n", EmojiParser.parseToUnicode(":cloud:"));
        weatherIconsCodes.put("09d", EmojiParser.parseToUnicode(":cloud_rain:"));
        weatherIconsCodes.put("09n", EmojiParser.parseToUnicode(":cloud_rain:"));
        weatherIconsCodes.put("10d", EmojiParser.parseToUnicode(":white_sun_rain:"));
        weatherIconsCodes.put("10n", EmojiParser.parseToUnicode(":white_sun_rain:"));
        weatherIconsCodes.put("11d", EmojiParser.parseToUnicode("⛈"));
        weatherIconsCodes.put("11n", EmojiParser.parseToUnicode("⛈"));
        weatherIconsCodes.put("13d", EmojiParser.parseToUnicode(":snowflake:"));
        weatherIconsCodes.put("13n", EmojiParser.parseToUnicode(":snowflake:"));
        weatherIconsCodes.put("50d", EmojiParser.parseToUnicode(":fog:"));
        weatherIconsCodes.put("50n", EmojiParser.parseToUnicode(":fog:"));

    }


}
