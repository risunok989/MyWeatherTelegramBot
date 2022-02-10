package Bot;

import WatherParser.OpenWeatherMapJsonParser;
import WatherParser.Parser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {

    private final static BotSetting botSettings = BotSetting.getInstance();

    public String getBotUsername() {
        return botSettings.getUserName();
    }

    public String getBotToken() {
        return botSettings.getToken();
    }

    public void sendMsg(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId.toString())
                .text(message)
                .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendImage(Long chatID, String patch){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(new File(patch)));
        sendPhoto.setChatId(chatID.toString());
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

    private static final String beginCommandSymbol = "/";
    private static final String getStartCommandName = "start";
    private static final String getStopCommandName = "stop";
    private static final String getHelpCommandName = "help";
    private static final String getWeatherName = "weather";
    private static final String getNewsName = "news";


    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                System.out.println();
                System.err.println("ПРИШЛО СООБЩЕНИЕ");
                //проверям на наличие символа
                if (update.getMessage().getText().contains("/")) {
                    beginCommandsSymbol(update);
                } else if (update.getMessage().getText().equals("погода")) {
                    try {
                        execute(OpenWeatherMapJsonParser.selectCountry(update.getMessage().getChatId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                }
            }
        } else if (update.hasCallbackQuery()) {
            try {
                try {
                    processCallbackQuerry(update.getCallbackQuery().getMessage().getChatId(), update);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void beginCommandsSymbol(Update update) {
        switch (update.getMessage().getText()) {
            case "/help":
                sendMsg(proccesHelpCommand(update.getMessage().getChatId()));
                break;
            case "/start":
                sendMsg(setStartCommand(update.getMessage().getChatId()));
                break;
            case "/stop":
                sendMsg(processStopCommand(update.getMessage().getChatId()));
                break;
        }

    }

    public SendMessage setStartCommand(long chatID) {
        System.err.println("procces StartCommand");

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Новости ( не работает)");
        inlineKeyboardButton1.setCallbackData(getNewsName);

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Погода ( работает на 90%)");
        inlineKeyboardButton2.setCallbackData(getWeatherName);

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

    public SendMessage proccesHelpCommand(Long chatID) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID.toString());
        sendMessage.setText("Если возникли какие-либо проблемы писать фидбэк @br0dos.");
        return sendMessage;
    }

    public SendMessage processStopCommand(Long chatID) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID.toString());
        sendMessage.setText("Не, так не работает, зай.\nПопробуй ещё раз '/start'");
        return sendMessage;
    }

    public void processCallbackQuerry(long chatID, Update update) throws IOException, TelegramApiException {
        System.err.println("processCallbackQuerry");


        switch (update.getCallbackQuery().getData()) {
            case getWeatherName:
                execute(OpenWeatherMapJsonParser.selectCountry(update.getCallbackQuery().getMessage().getChatId()));
                break;
            case getNewsName:
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                execute(sendMessage);
                break;
            case "626081":
                SendMessage sendMessage1 = new SendMessage();
                sendMessage1.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                sendMessage1.setText(Parser.parseWeather(OpenWeatherMapJsonParser.downloadJsonRawData("626081")));
                sendMsg(sendMessage1);

                break;
            case "620391":
                SendMessage sendMessage2 = new SendMessage();
                sendMessage2.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                sendMessage2.setText(Parser.parseWeather(OpenWeatherMapJsonParser.downloadJsonRawData("620391")));
                sendMsg(sendMessage2);

                break;
        }
//
    }

    public void popit(long chatID, String message) {
        System.err.println("grimacingUser (ПОПИТЬ)");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatID));
        sendMessage.setText("ПОПИТЬ");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        sendMessage.setText("POP'IT");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}






