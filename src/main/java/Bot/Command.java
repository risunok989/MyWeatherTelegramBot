package Bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.awt.image.ImagingOpException;
import java.util.ArrayList;
import java.util.List;


public class Command {

    public static final String WEATHER_NAME = "weather";
    public static final String NEWS_NAME = "news";
    private static final long TIME_SPAM = 10;
    private static long timeRunCount;
    private static long callbackCounter = 0;
    private static long before = 0;
    private static long after = 0;

    public static void setTimeRunCount(long timeRunCount) {
        Command.timeRunCount = timeRunCount;
    }

    public static void setCallbackCounter(long callbackCounter) {
        Command.callbackCounter = callbackCounter;
    }

    public static void setBefore(long before) {
        Command.before = before;
    }

    public static void setAfter(long after) {
        Command.after = after;
    }

    public static long getCallbackCounter() {
        return callbackCounter;
    }

    public static SendMessage processStartCommand(long chatID) {
        System.err.println("procces StartCommand");



        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Новости ( не работает)");
        inlineKeyboardButton1.setCallbackData(NEWS_NAME);

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Погода ( работает на 90%)");
        inlineKeyboardButton2.setCallbackData(WEATHER_NAME);

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatID));
        sendMessage.setText("Добрый день! я Бот райончика. Выберете что Вам нужно? Если будут вопросы, спрашивайте через '/help'.");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        return sendMessage;
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
