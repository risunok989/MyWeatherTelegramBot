package Bot;

import Bot.Enum.CheckCommandType;
import Bot.Enum.CheckMessageType;
import Bot.Keyboard.InlineKeyboardMarkupBuilder;
import Bot.Keyboard.ReplyKeyboardMarkupBuilder;
import WatherParser.JsonDownloadForOpenWeather;
import WatherParser.JsonParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;


public class Bot extends TelegramLongPollingBot {


    private static long callbackCounter = 0;

    private final static BotSetting botSettings = BotSetting.getInstance();

    public String getBotUsername() {
        return botSettings.getUserName();
    }

    public String getBotToken() {
        return botSettings.getToken();
    }

    // отправка сообщений
    public void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendImage(Long chatID, String patch) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(new File(patch)));
        sendPhoto.setChatId(chatID.toString());
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    //

    public void onUpdateReceived(Update update) {
        System.out.println("ПРИШЛО СООБЩЕНИЕ");
        if (update.hasMessage()) {
            //проверяем на ТИП
            CheckMessageType checkMessageType = getMassageType(update);
            switch (checkMessageType) {
                case COMMAND:
                    beginCommandsSymbol(update);
                    break;
                case IMAGE:
                    sendMessage(update.getMessage().getChatId(), "Ещё не работаю с фотографиями, скоро.");
                    break;
                case TEXT:
                    break;
                case POPIT:
                    sendMessage(update.getMessage().getChatId(), "ПОПИТЬ");
                    sendMessage(update.getMessage().getChatId(), "ПАПИТЬ");
                    sendMessage(update.getMessage().getChatId(), "POP'IT");
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
    // проверка собщения на ТИП
    private CheckMessageType getMassageType(Update update) {
        CheckMessageType checkMessageType = null;
        if (update.getMessage().getPhoto() != null) {
            checkMessageType = CheckMessageType.IMAGE;
        } else if (update.getMessage().getText() != null) {
            if ((update.getMessage().getText().contains("/"))) {
                checkMessageType = CheckMessageType.COMMAND;
            } else if ((update.getMessage().getText().contains("попить"))) {
                checkMessageType = CheckMessageType.POPIT;
            } else {
                callbackCounter = 0; // присвоение счётчику CallbackCounter = 0
                System.out.println("CallbackCounter " + callbackCounter);
                checkMessageType = CheckMessageType.TEXT;
            }
        }

        if (checkMessageType == null)
            throw new IllegalArgumentException(update.toString());

        return checkMessageType;
    }

    public void processCallbackQuerry(long chatID, Update update) throws IOException, TelegramApiException {
        System.err.println("processCallbackQuerry");

        callbackCounter++;

        if (callbackCounter <= 5) {   // счётчик спама для Callback

            switch (update.getCallbackQuery().getData()) {
                case Command.WEATHER_NAME:
                    execute(Command.selectCountry(update.getCallbackQuery().getMessage().getChatId()));
                    break;
                case Command.NEWS_NAME:
                    sendMessage(update.getCallbackQuery().getMessage().getChatId(), "Ешё не работает");
                    break;
                //-----------------------------------------------------//
                case Command.LIDA:
                    sendMessage(update.getCallbackQuery().getMessage().getChatId(),
                            JsonParser.parseWeatherJson(JsonDownloadForOpenWeather.downloadJsonData(Command.LIDA)));
                    break;
                case Command.VAWKAVYSK:
                    sendMessage(update.getCallbackQuery().getMessage().getChatId(),
                            JsonParser.parseWeatherJson(JsonDownloadForOpenWeather.downloadJsonData(Command.VAWKAVYSK)));
                    break;
                case Command.BREST:
                    sendMessage(update.getCallbackQuery().getMessage().getChatId(),
                            JsonParser.parseWeatherJson(JsonDownloadForOpenWeather.downloadJsonData(Command.BREST)));
                    break;
                case Command.WARSHAW:
                    sendMessage(update.getCallbackQuery().getMessage().getChatId(),
                            JsonParser.parseWeatherJson(JsonDownloadForOpenWeather.downloadJsonData(Command.WARSHAW)));
                    break;
            }
        } else {
            sendMessage(update.getMessage().getChatId(), "");
            System.out.println("callbackCounter " + callbackCounter);
        }
//
    }



    public void beginCommandsSymbol(Update update) {
        callbackCounter++;

        if (callbackCounter < 2) {
            switch (update.getMessage().getText()) {
                case CheckCommandType.HELP:
                    sendMessage(Command.processHelpCommand(update.getMessage().getChatId()));
                    break;
                case CheckCommandType.START:
                    sendMessage(Command.processStartCommand(update.getMessage().getChatId()));
                    break;
                case "/stop":
                    sendMessage(Command.processStopCommand(update.getMessage().getChatId()));
                    break;
            }
        } else {
            sendMessage(update.getMessage().getChatId(), "");
        }


    }


    public SendMessage test(Long chatID) {
        return InlineKeyboardMarkupBuilder.create(chatID)
                .setText("ffff")
                .row()
                .button("test1", "test1")
                .endRow()
                .row()
                .button("test2", "test2")
                .endRow()
                .build();
    }

    public SendMessage test2(Long chatID) {
        return ReplyKeyboardMarkupBuilder.create(chatID)
                .setText("test")
                .row()
                .button("fdfdf")
                .endRow()
                .build();
    }
}






