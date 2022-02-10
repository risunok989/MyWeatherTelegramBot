package Bot;

import WatherParser.OpenWeatherMapJsonParser;
import WatherParser.Parser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;


public class Bot extends TelegramLongPollingBot {

    private final static BotSetting botSettings = BotSetting.getInstance();

    public String getBotUsername() {
        return botSettings.getUserName();
    }

    public String getBotToken() {
        return botSettings.getToken();
    }


    public void sendMessage(SendMessage sendMessage) {
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


    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            CheckMessageType checkMessageType = getMassageType(update);
            switch (checkMessageType) {
                case COMMAND:
                    beginCommandsSymbol(update);
                    break;
                case IMAGE:
                    sendMessage(update.getMessage().getChatId(), "Ещё не работает");
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
//        if (update.hasMessage()) {
//            if (update.getMessage().hasText()) {
//                System.out.println();
//                System.err.println("ПРИШЛО СООБЩЕНИЕ");
//                //проверям на наличие символа
//                if (update.getMessage().getText().contains("/")) {
//                    beginCommandsSymbol(update);
//                } else if (update.getMessage().getText().equals("погода")) {
//                    try {
//                        execute(OpenWeatherMapJsonParser.selectCountry(update.getMessage().getChatId()));
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        } else if (update.hasCallbackQuery()) {
//            try {
//                try {
//                    processCallbackQuerry(update.getCallbackQuery().getMessage().getChatId(), update);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }

    // проверка собщения на ТИП
    private CheckMessageType getMassageType(Update update) {
        CheckMessageType checkMessageType = null;
        if (update.getMessage().getPhoto() != null) {
            checkMessageType = CheckMessageType.IMAGE;
        } else if (update.getMessage().getText() != null) {
            checkMessageType = (update.getMessage().getText().contains("/")) ?
                    CheckMessageType.COMMAND :
                    CheckMessageType.TEXT;
            checkMessageType = (update.getMessage().getText().contains("Попить")) ?
                    CheckMessageType.POPIT :
                    CheckMessageType.TEXT;
        }

        if (checkMessageType == null)
            throw new IllegalArgumentException(update.toString());

        return checkMessageType;
    }

    public void beginCommandsSymbol(Update update) {
        switch (update.getMessage().getText()) {
            case "/help":
                sendMessage(Command.processHelpCommand(update.getMessage().getChatId()));
                break;
            case "/start":
                sendMessage(Command.processStartCommand(update.getMessage().getChatId()));
                break;
            case "/stop":
                sendMessage(Command.processStopCommand(update.getMessage().getChatId()));
                break;
        }

    }


    public void processCallbackQuerry(long chatID, Update update) throws IOException, TelegramApiException {
        System.err.println("processCallbackQuerry");


        switch (update.getCallbackQuery().getData()) {
            case Command.WEATHER_NAME:
                execute(OpenWeatherMapJsonParser.selectCountry(update.getCallbackQuery().getMessage().getChatId()));
                break;
            case Command.NEWS_NAME:
                sendMessage(update.getCallbackQuery().getMessage().getChatId(), "Ешё не работает");
                break;
            case "626081":
                SendMessage sendMessage1 = new SendMessage();
                sendMessage1.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                sendMessage1.setText(Parser.parseWeather(OpenWeatherMapJsonParser.downloadJsonRawData("626081")));
                sendMessage(sendMessage1);

                break;
            case "620391":
                SendMessage sendMessage2 = new SendMessage();
                sendMessage2.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                sendMessage2.setText(Parser.parseWeather(OpenWeatherMapJsonParser.downloadJsonRawData("620391")));
                sendMessage(sendMessage2);

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






