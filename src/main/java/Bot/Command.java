package Bot;

import Bot.Keyboard.InlineKeyboardMarkupBuilder;
import Util.WeatherUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


public class Command {

    public static final String WEATHER_NAME = "weather";
    public static final String NEWS_NAME = "news";


    public static SendMessage processStartCommand(long chatID) {
        System.err.println("process StartCommand");
        return InlineKeyboardMarkupBuilder.create(chatID)
                .setText("Выберете что-нибудь")
                .row()
                .button("Погода " + WeatherUtil.weatherIconsCodes.get("rainbow"), WEATHER_NAME)
                .button("Новости " +WeatherUtil.weatherIconsCodes.get("newspaper"), NEWS_NAME)
                .endRow()
                .row()
                .button("Что-нибудь ещё " + WeatherUtil.weatherIconsCodes.get("underage"), "ещё ни чего")
                .endRow()
                .build();


//         InlineKeyboardMarkupBuilder.create(chatID)
//                .setText("Выберете что-нибудь :)")
//                .row()
//                .button("Погода работают на 90%", WEATHER_NAME)
//                .button("Новости, вообще ещё не работает.", NEWS_NAME)
//                .endRow()
//                .build()
//                .setChatId(String.valueOf(chatID));


//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//
//        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
//        inlineKeyboardButton1.setText("Новости ( не работает)");
//        inlineKeyboardButton1.setCallbackData(NEWS_NAME);
//
//        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
//        inlineKeyboardButton2.setText("Погода ( работает на 90%)");
//        inlineKeyboardButton2.setCallbackData(WEATHER_NAME);
//
//        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//        keyboardButtonsRow1.add(inlineKeyboardButton1);
//        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
//        keyboardButtonsRow2.add(inlineKeyboardButton2);
//
//        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//        rowList.add(keyboardButtonsRow1);
//        rowList.add(keyboardButtonsRow2);
//
//        inlineKeyboardMarkup.setKeyboard(rowList);
//
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(String.valueOf(chatID));
//        sendMessage.setText("Добрый день! я Бот райончика. Выберете что Вам нужно? Если будут вопросы, спрашивайте через '/help'.");
//        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//
//        return sendMessage;
    }

    public static SendMessage processHelpCommand(long chatID) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatID));
        sendMessage.setText("Если возникли какие-либо проблемы писать фидбэк @br0dos.");
        return sendMessage;
    }

    public static SendMessage processStopCommand(long chatID) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatID));
        sendMessage.setText("Не, так не работает, зай.\nПопробуй ещё раз '/start'");
        return sendMessage;
    }
}
